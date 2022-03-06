package primitives;


public class Vector extends Point {


    public Vector(double x, double y, double z ) {
        super(x,y,z);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("the vector can't be the ZERO vector");
    }

    public Vector(Double3 x) {
        super(x);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("the vector can't be the ZERO vector");
    }


    public Vector add(Vector v) { return new Vector(this.xyz.add(v.xyz)); }

    public Vector scale(double d) { return new Vector(this.xyz.scale(d)); }

    /**
     * Func to do crossProduct between 2 vectors
     * @param v the second vector
     * @return the new vector of the result
     */
    public Vector crossProduct(Vector v) {
        return new Vector((this.xyz.d2 * v.xyz.d3) - (this.xyz.d3 * v.xyz.d2),
                (this.xyz.d3 * v.xyz.d1) - (this.xyz.d1 * v.xyz.d3),
                (this.xyz.d1 * v.xyz.d2) - (this.xyz.d2 * v.xyz.d1));
    }


    /**
     * Func for the length^2 of the vector
     * @return the length^2
     */
    public double lengthSquared() {
        return ((this.xyz.d1 * this.xyz.d1) + (this.xyz.d2 * this.xyz.d2)
                + (this.xyz.d3 * this.xyz.d3));
    }

    /**
     * Func to get the length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Func to normelize our vector
     * @return our vector(after the change)
     */
    public Vector normalize() {

        return new Vector(this.xyz.scale(1/length()));
    }

    /**
     * Func to do dotProduct between 2 vectors
     * @param v the second vector
     * @return the number of the result
     */
    public double dotProduct(Vector v) {
        return ((this.xyz.d1 * v.xyz.d1) +
                (this.xyz.d2 * v.xyz.d2) + (this.xyz.d3 * v.xyz.d3));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector)obj;
        return xyz.equals(other.xyz);
    }



}
