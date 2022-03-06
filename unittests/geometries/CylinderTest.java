package geometries;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;



class CylinderTest {

    @org.junit.jupiter.api.Test
    void getNormal() {
        Cylinder c=new Cylinder(new Ray(new Point(0,0,1),new Vector(0,0,1)),2,2);
        assertEquals(new Vector(0,1,0),c.getNormal(new Point(0,2,1.5)),"get normal() wrong value" );
        assertEquals(new Vector(0,0,1),c.getNormal(new Point(0,1,3)),"get normal() wrong value" );
        assertEquals(new Vector(0,0,1),c.getNormal(new Point(1,1,-1)),"get normal() wrong value" );
    }
}