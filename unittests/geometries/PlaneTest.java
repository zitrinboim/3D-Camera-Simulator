package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    public void testPlanePoint3DPoint3DPoint3D() {

        try {
            Plane p1=new Plane(new Point(1,2,3),new Point(1,2,3),new Point(2,2,2));
            fail("Constructed a plane with the same point");
        }catch(Exception e) {}

        try {
            Plane p2=new Plane(new Point(1,2,3),new Point(2,4,6),new Point(3,6,9));
            fail("Constructed a plane with points on the same line");
        }catch(Exception e) {}
    }


    @Test
    void testGetNormal() {
        Plane p = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(0, 0, -1);
        Vector normal=p.getNormal(new Point(0,0,0));
        assertTrue(normal.equals(v1) || normal.equals(v2),"the normal has wrong value");

    }
}