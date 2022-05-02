package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Sphere class represents sphere in 3D
 *
 */
public class Sphere extends Geometry {

    /**
     * the point of the center of the sphere
     */
    private Point center;

    /**
     * the radius of the sphere
     */
    private double radius;

    /**
     * constructor
     * @param center the center point
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }



    /**
     * getter
     * @return the center point
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getter
     * @return the radius of the sphere
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }

    /**
     * Normal of a sphere to a point
     * @param pnt
     * @return Normal of a sphere to a point
     */
    @Override
    public Vector getNormal(Point pnt) {
        if(pnt.equals(center))
            throw new IllegalArgumentException("The points for normal has to be different");
        return pnt.subtract(center).normalize();
    }
    /**
     * calculates number of intersections of ray with a sphere
     * @param ray pointing towards the graphic object
     * @return list of points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {

        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        //if the ray is starting from precisely the center of the sphere
        // than we know the intersection point will be scaling the point by the radius
        if (P0.equals(center))
        {
            return List.of(new GeoPoint(this, center.add(v.scale(radius))) );
        }

        Vector U = center.subtract(P0); // The vector from p0 to the center of the sphere
        double tm =alignZero(v.dotProduct(U)); // the scalar for the projection of v on u
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm)); // the distance of the center to the ray

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d)); // distance from p1 to intersection with d
        double t1 = alignZero(tm - th); // from p0 to p1
        double t2 = alignZero(tm + th);// from p0 to p2

        if (t1 > 0 && t2 > 0) // take only t > 0 (going in the right direction)
        {
//            Point P1 = P0.add(v.scale(t1));
//            Point P2 = P0.add(v.scale(t2));
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this, P1),new GeoPoint(this, P2));
        }
        if (t1 > 0) {
//            Point P1 = P0.add(v.scale(t1));
            Point P1 =ray.getPoint(t1);
            return List.of(new GeoPoint(this, P1));
        }
        if (t2 > 0) {
//            Point P2 = P0.add(v.scale(t2));
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this, P2));
        }
        return null;

    }
}
