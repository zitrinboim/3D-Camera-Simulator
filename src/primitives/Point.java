package primitives;

import java.util.Objects;
/**
 * class Point is base class to represent 1
 * point in 3d with Double3
 *
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public class Point {

    /**
     * final for the origin point
     */
    final Double3 xyz;

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

}


