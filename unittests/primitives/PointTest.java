package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    Point p1 = new Point(1,2,3);
    Point p2 = new Point(1,1,1);

    @Test
    /**
     * Test method for {@link Point#add(Vector)}
     */
    void testAdd() {
        Vector v = new Vector(1,2,3);
        Point point = new Point(1,1,1);

        assertEquals(new Point(2,3,4), point.add(v),"add doesn't work properly");
    }

    /**
     * Test method for {@link Point#subtract(Point)} )}
     */
    @Test
    void testSubtract() {
        assertEquals(new Vector(0,1,2), p1.subtract(p2),"add doesn't work properly");
    }
}