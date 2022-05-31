package renderer;
import geometries.Geometries;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.checkSign;

/**
     * derivative class from RayTracer traces ray path in the scene noting intersections
     * with geometries in the scene
     */
    public class RayTracerBasic extends RayTracer {
        private static final double DELTA = 0.1;
        private static final int MAX_CALC_COLOR_LEVEL = 10;
        private static final Double MIN_CALC_COLOR_K = 0.001;
        public static final Double3 INITIAL_K = Double3.ONE;

        public RayTracerBasic(Scene scene) {
            super(scene);
        }

        /**
         * traces the ray and its intersections with geometries to find the closest point and return its colour
         *
         * @param ray the ray being traced
         * @return the calculated color of the closest point- to colour thus themathcing pixel
         */
        @Override
        public Color traceRay(Ray ray) {
            GeoPoint closetPoint = findClosestIntersection(ray);
            if (closetPoint == null) {
                return scene.getBackground();
            }
            return calcColour(closetPoint, ray);
        }
    /**
     * @param rays
     * @return The average color of the rays
     */
    @Override
    public Color calcAverageColor(List<Ray> rays) {
        Color totalColor = Color.BLACK;
        for (Ray ray : rays) {
            totalColor = totalColor.add(traceRay(ray));
        }
        return totalColor.reduce(rays.size()); // Calculates the average color
    }

        /**
         * @param ray
         * @return closest geopoint in intersection list
         */
        private GeoPoint findClosestIntersection(Ray ray) {
            List<GeoPoint> intersectionPoints = scene.getGeometries().findGeoIntersections(ray);
            if (intersectionPoints == null) {
                return null;
            }
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersectionPoints);
            return closestPoint;
        }

        private Color calcColour(GeoPoint gp, Ray ray) {

            return calcColour(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                    .add(scene.getAmbientLight().getIntensity());
        }


        private Color calcColour(GeoPoint gp, Ray ray, int level, Double3 k) {

            Color color = gp.geometry.getEmission().add(calcLocalEffects(gp, ray, k));
            return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));

        }

        /**
         * calculates the concurrent local effects such a specular and diffusion effects
         *
         * @param gp
         * @param ray
         * @return the color of the area add to concurrent local effect
         */
        private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
            Color color = gp.geometry.getEmission();
            Vector v = ray.getDir().normalize();
            Vector n = gp.geometry.getNormal(gp.point);
            double nv = alignZero(n.dotProduct(v));
            if (nv == 0) return color.BLACK;
            Material material = gp.geometry.getMaterial();
            color = color.BLACK;
            for (LightSource lightSource : scene.getLights()) {
                Vector l = lightSource.getL(gp.point);
                double nl = alignZero(n.dotProduct(l));
                if(checkSign(nl, nv)) { // sign(nl) == sign(nv)

                    Double3 ktr = transparency(l, n, lightSource, gp, nv);
                    if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                        Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                        color = color.add(
                                iL.scale(calcDiffusive(material, nl)),
                                iL.scale(calcSpecular(material, n, l, nl, v)));
                    }
                }
            }
            return color;
        }
        /**
         * checks whether a point on  a geometry is shaded
         *
         * @param gp
         * @param lightSource
         * @param n
         * @return
         */
        private Double3 transparency(Vector l, Vector n, LightSource lightSource, GeoPoint gp, double nv) {
            Vector lightDirection = l.scale(-1); // from point to light source//
            Ray lightRay = new Ray(gp.point, lightDirection, n);
            double lightDistance = lightSource.getDistance(gp.point);
            var intersections = scene.getGeometries().findGeoIntersections(lightRay);
            if (intersections == null)
                return Double3.ONE; //no intersection
            for (var geo : intersections) {
                double dist = geo.point.distance(gp.point);
                if (dist >= lightDistance) {
                    intersections.remove(geo);
                }
            }
            if (intersections.isEmpty()) {
                return Double3.ONE;
            }
            Double3 ktr = Double3.ONE;
            for (GeoPoint geopoint : intersections) {
                ktr = ktr.product(geopoint.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
            return ktr;
        }

        private Color calcGlobalEffects(GeoPoint geopoint, Ray inRay, int level, Double3 k) {
            Color color = Color.BLACK;
            Material material = geopoint.geometry.getMaterial();
            // Double3 MIN_CALC=new Double3(MIN_CALC_COLOR_K,MIN_CALC_COLOR_K,MIN_CALC_COLOR_K);
            Double3 kr = material.kR;
            Double3 kkr = k.product(kr);
            Vector n = geopoint.geometry.getNormal(geopoint.point);//
            if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
                Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
                GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
                if (reflectedPoint != null) {
                    color = color.add(calcColour(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
                }
            }
            Double3 kt = material.kT;
            Double3 kkt = k.product(kt);
            if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
                Ray refractedRay = constructRefractedRay(geopoint, inRay, n);
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                if (refractedPoint != null) {
                    color = color.add(calcColour(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
                }
            }
            return color;
        }

        private Double3 calcDiffusive(Material material, double nl) {
            return material.kD.scale(Math.abs(nl));
        }

        private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
            Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)).normalize();
            return material.kS.scale( Math.pow(Math.max(0, r.dotProduct(v.scale(-1d))), material.nShininess));

        }

        private Ray constructRefractedRay(GeoPoint pointGeo, Ray inRay, Vector n) {
            return new Ray(pointGeo.point, inRay.getDir(), n);
        }

        private Ray constructReflectedRay(Vector n, Point pointGeo, Ray inRay) {

            Vector v = inRay.getDir();
            double vn = v.dotProduct(n);

//        if (vn == 0) {
//            return null;
//        }

            //r = v - 2.(v.n).n

            Vector r = v.subtract(n.scale(2 * vn)).normalize();
            return new Ray(pointGeo, r, n);
        }

//    /**
//     * If the ray from the point to the light source intersects with any opaque object, then the point is in shadow
//     *
//     * @param light the light source
//     * @param gp the point on the geometry that we're shading
//     * @param l the vector from the light source to the point on the geometry
//     * @param n the normal vector of the point
//     * @return The color of the pixel.
//     */
//    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Ray lightRay = new Ray(gp.point, lightDirection, n); // refactored ray head move
//        List<GeoPoint> intersections = this.scene.getGeometries().findGeoIntersections(lightRay, light.getDistance(gp.point));
//        if (intersections != null) {
//            for (GeoPoint intersection : intersections) {
//                if (intersection.geometry.getMaterial().kT == Double3.ZERO) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
    }











