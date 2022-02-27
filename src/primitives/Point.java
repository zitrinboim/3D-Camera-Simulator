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
     * final static for the origin point
     */
    final  Double3 xyz;


    public  Point(Double3 double3){
        xyz=double3;
    }

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

    public Point add(Vector v) { return new Point(this.xyz.add(v.xyz));}

    public Vector subtract(Point p2){
        return new Vector(this.xyz.subtract(p2.xyz));
    }

}


