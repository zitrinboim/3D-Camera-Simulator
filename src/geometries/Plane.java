package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * plane class represents two-dimensional Triangle in 3D Cartesian coordinate
 * by point and vector normal to the plane
 *
 */
public class Plane implements Geometry {
    /**
     * Associated point in which the plane lays
     */
    private Point p0;
    private Vector normal;

    /**
     * constructor
     * @param p0 the point
     * @param normal the vector that normal to the plane
     */
    public Plane(Point p0, Vector normal) {
        super();
        this.p0 = p0;
        this.normal = normal;
    }

    /**
     * constructor that get 3 points and set one of them to witch the plane lays
     * and calculate the normal vector
     * @param p0 point 0
     * @param p1 point 1
     * @param p2 point 2
     */
    public Plane(Point p0, Point p1,Point p2) {
        super();
        this.p0 = p0;/** Associated point in which the plane lays*/

        if (p0.equals(p1) || p0.equals(p2) || p1.equals(p2))
            throw new IllegalArgumentException("2 points cannot be the same");
        Vector n = p1.subtract(p0).crossProduct(p2.subtract(p0));
        this.normal = n.normalize();

    }

    /**
     * getter
     * @return the point 0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter
     * @return the normal vector
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane [p0=" + p0 + ", normal=" + normal + "]";
    }


    public Vector getNormal(Point p) {
        return normal;
    }

}