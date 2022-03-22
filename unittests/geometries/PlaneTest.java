package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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
    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)} (primitives.Ray)}.
     */
    @Test
    public void findIntersections() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray into plane
        assertEquals(List.of(new Point(1, 0, 0)),
                pl.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");

        // TC02: Ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================
        // TC11: Ray parallel to plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC12: Ray in plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, .5), new Vector(0, 1, -1))),
                "Must not be plane intersection");


        // TC13: Orthogonal ray into plane
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
                pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1))),
                "Bad plane intersection");

        // TC14: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC15: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC16: Orthogonal ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC17: Ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Must not be plane intersection");

    }
}