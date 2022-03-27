package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;

class CylinderTest {

    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Cylinder cy = new Cylinder(
                new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 2, 3);
        // test for point on first disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 1, 1)),"get normal() wrong value");

        // test for point on second disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 1, 4)),"get normal() wrong value");

        //test for point on body
        assertEquals(new Vector(0, 1, 0), cy.getNormal(new Point(0, 2, 2)),"get normal() wrong value");

        // ============ Boundary Value Tests ==============
        // test for center point on first disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 0, 1)),"get normal() wrong value");

        // test for center point on second disk
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 0, 4)),"get normal() wrong value");
    }
}