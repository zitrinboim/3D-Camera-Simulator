package primitives;

import java.util.Objects;
/**
 * class Point is base class to represent 1
 * point in 3d with Double3
 *
 *
 */
public class Point {

    /**
     * final for the origin point
     */
    final Double3 xyz;
    public static  Point ZERO = new Point(0d, 0d, 0d);


    /**
     * Constructor to initialize Point based object with its three number values
     *
     * @param double3 first number value
     */
    public  Point(Double3 double3){
        xyz=double3;
    }

    /**
     * Constructor to initialize Point based object with its three number values
     *
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public  Point(double x, double y, double z ) { xyz = new Double3(x,y,z); }

    /**
     * Constructor to initialize Point based object with its three number values
     *
     * @param point first number value
     */
    public  Point(Point point ) {this.xyz=point.xyz; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point)obj;
        return this.xyz.equals(other.xyz);
    }

    public  Point getPoint() {
        return this ;
    }


    /**
     * Point x value getter
     *
     * @return x coordinate value
     */
    public double get_x()
    {
        return xyz.d1;
    }
    /**
     * Point y value getter
     *
     * @return y coordinate value
     */
    public double get_y()
    {
        return xyz.d2;
    }
    /**
     * Point z value getter
     *
     * @return z coordinate value
     */
    public double get_z()
    {
        return xyz.d3;
    }
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
    /**
     * Sum two floating point triads into a new triad where each couple of numbers
     * is summarized
     *
     * @param v right handle side operand for addition
     * @return result of add
     */
    public Point add(Vector v) { return new Point(this.xyz.add(v.xyz));}

    /**
     * Subtract two floating point triads into a new triad where each couple of
     * numbers is subtracted
     *
     * @param p2 right handle side operand for addition
     * @return result of add
     */
    public Vector subtract(Point p2){
        return new Vector(this.xyz.subtract(p2.xyz));
    }
    /**
     *
     * @param other
     * @return d = ((x2 = x1) * (x2 = x1) + (y2 - y1) * (y2 - y1)  + (z2 = z1 ) * (z2 = z1 ))
     */
    public double distanceSquared(Point other)
    {
        double x1 = xyz.d1;
        double y1 = xyz.d2;
        double z1 = xyz.d3;

        double x2 = other.xyz.d1;
        double y2 = other.xyz.d2;
        double z2 = other.xyz.d3;

        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)  + (z2 - z1 ) * (z2 - z1 ));
    }
    /**
     *
     * @param other
     * @return d=Sqrt(lengthSquare)
     *
     */
    public double distance(Point other)
    {
        return Math.sqrt(distanceSquared(other));
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }
}


