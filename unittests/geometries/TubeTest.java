package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
        Tube t = new Tube(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 2);
        assertEquals(new Vector(0, 1, 0), t.getNormal(new Point(0, 2, 2)), "get normal() wrong value");
    }
}