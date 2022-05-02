package geometries;

import primitives.*;

import java.util.List;

/**
 * Triangle class represents two-dimensional Triangle in 3D Cartesian coordinate
 * system heir from the polygon
` *
 */
public class Triangle extends Polygon {

    /**
     * constructor that get 3 points and use the polygon constructor
     *
     * @param vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "Triangle [vertices=" + vertices + ", plane=" + plane + "]";
    }

    // override function
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        //
        List<GeoPoint> result = plane.findGeoIntersections(ray);

        if(result == null){
            return null;
        }

        //checking that the intersection point is bounded by traingle vertices
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(p0);// p0->p1
        Vector v2 = p2.subtract(p0);// p0->p2
        Vector v3 = p3.subtract(p0);//p0->p3

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double s1 = n1.dotProduct(v);
        double s2 = n2.dotProduct(v);
        double s3 = n3.dotProduct(v);

        if(s1> 0 && s2 > 0 && s3 > 0 ||  s1 < 0 && s2< 0 && s3 < 0)
            return List.of(new GeoPoint(this,result.get(0).point));

        return null;
    }
}