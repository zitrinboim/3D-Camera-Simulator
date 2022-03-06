package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void getNormal() {
        Sphere s=new Sphere(new Point(0,0,0),2);
        assertEquals(s.getNormal(new Point(2,0,0)).normalize(),new Vector(1,0,0),"");
    }
}