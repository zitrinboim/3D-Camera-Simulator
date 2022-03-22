package geometries;
import primitives.*;

import java.util.List;

/**
 * Tube class represents Tube in the 3D
 *
 */
public class Tube implements Geometry {

    /**
     * the Ray
     */
    protected Ray axisRay;
    /**
     * the radius of the tube
     */
    protected double radius;

    /**
     * constructor
     *
     * @param axisRay the Ray
     * @param radius  the radius
     */
    public Tube(Ray axisRay, double radius) {
        super();
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getter
     *
     * @return the ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * getter
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }

    /**
     * The normal of the tube to the point
     *
     * @param point
     * @return The normal of the tube to the point
     */
    @Override
    public Vector getNormal(Point point) {

        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
        Point o = axisRay.getP0().add(axisRay.getDir().scale(t));
        return point.subtract(o).normalize();
    }
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}