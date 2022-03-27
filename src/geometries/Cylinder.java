package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Cylinder class heir from the Tube class
 */
public class Cylinder extends Tube implements Geometry {

    /**
     * the height of the cylinder
     */
    private double height;

    /**
     * constructor
     *
     * @param axisRay the Ray
     * @param radius  the radius
     * @param height  the height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * getter
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder [height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "]";
    }

    /**
     * The normal of the cylinder
     *
     * @param point
     * @return The normal of the cylinder
     */
    public Vector getNormal(Point point) {
        Point v = axisRay.getPoint(height);

        if (point.equals(v)
                || point.equals(axisRay.getP0())
                || point.subtract(v).length() < radius
                || point.subtract(axisRay.getP0()).length() < radius)
            return axisRay.getDir().normalize();

        return super.getNormal(point);
    }
}