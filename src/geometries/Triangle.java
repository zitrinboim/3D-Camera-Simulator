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
    public List<Point> findIntersections(Ray ray) {
        //comment here
        List<Point> result =plane.findIntersections(ray);
        if(result == null)
            return null;

        Point P0=ray.getP0();
        Vector v=ray.getDir();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(P0);//(P0->p1)
        Vector v2 = p2.subtract(P0);//(P0->p2)
        Vector v3 = p3.subtract(P0);//(P0->p3)

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double s1 = v.dotProduct(n1);
        double s2 = v.dotProduct(n2);
        double s3 = v.dotProduct(n3);

        if((s1>0 && s2>0 && s3>0 )|| (s1<0 && s2<0 && s3<0))
        {
            return result;
        }
        return super.findIntersections(ray);
    }
}