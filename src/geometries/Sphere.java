package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Sphere class represents sphere in 3D
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
    public List<Point> findIntersections(Ray ray) {
        Point p0 = new Point(ray.getP0());
        //if the ray starts at the center add epsilon
        if(center.equals(ray.getP0()))
            p0 = new Point(ray.getP0().get_x() + 0.1111111115,ray.getP0().get_y(), ray.getP0().get_z());
        //now we need new ray because of we add epsilon to _POO
        Ray myRay = new Ray(p0,ray.getDir());
        //u = o - p0
        Vector u = center.subtract(p0);
        //t_m = v * u
        double t_m = myRay.getDir().dotProduct(u);
        //d = sqrt(|u|^2 - t_m^2)
        double d = Math.sqrt(u.lengthSquared() - t_m*t_m);
        //there are no intersections
        if(d>radius)
            return null;
        //t_h = sqrt(r^2 - d^2)
        double t_h = Math.sqrt(radius*radius - d*d);
        //t1,2 = t_m +- t_h
        double t1 = t_m + t_h;
        double t2 = t_m - t_h;
        Point p1 = null;
        Point p2 = null;
        //if the ray tangent to the sphere - t_h=0
        if(t1 == t2)
            t2 = -1; //that`s for that it will not return the same point twice
        //only if t1>0
        if(!isZero(t1) && t1>0)
            //p1 = p0 + t1*v
            p1 = myRay.getP0().add(myRay.getDir().scale(t1));
        //only if t2>0
        if(!isZero(t2) && t2>0)
            //p2 = p0 + t2*v
            p2 = myRay.getP0().add(myRay.getDir().scale(t2));
        //if it is no intersections points
        if(p1 == null && p2 == null)
            return null;
        ArrayList<Point> intsersection = new ArrayList<Point>();
        if(p1 != null)
            intsersection.add(p1);
        if(p2 != null)
            intsersection.add(p2);
        return intsersection;
    }
}