package geometries;
import primitives.*;

/**
 * Sphere class represents sphere in 3D
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public class Sphere implements Geometry {

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
}