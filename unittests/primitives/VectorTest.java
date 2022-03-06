package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.System.out;
import static primitives.Util.isZero;

/**
 * Unit test for primitives.Vector class
 */

class VectorTest {

    Vector _vector = new Vector(1, 2, 3);
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    public void testAdd() {
        //========================= Equivalence Partition Tests ===========================
        assertTrue(_vector.add(new Vector(1, 1, 1)).equals(new Vector(2, 3, 4)));
        //========================= Boundary Values Tests ===========================
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    public void testScale() {
        //========================= Equivalence Partition Tests ===========================
        assertTrue(_vector.scale(2.0).equals(new Vector(2, 4, 6)));
        //========================= Boundary Values Tests ===========================
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    public void testCrossProduct() {
        //========================= Equivalence Partition Tests ===========================
        assertTrue(_vector.crossProduct(new Vector(0, 3, -2)).equals(new Vector(-13, 2, 3)));
        //========================= Boundary Values Tests ===========================
        try { // test zero vector
            v1.crossProduct(v2);
            fail("ERROR: crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
        Vector vr = v1.crossProduct(v3);
        if (!isZero(vr.length() - v1.length() * v3.length()))
            fail("ERROR: crossProduct() wrong result length");
        if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
            fail("ERROR: crossProduct() result is not orthogonal to its operands");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    public void testLengthSquared() {
        //========================= Equivalence Partition Tests ===========================
        assertTrue(_vector.lengthSquared() == 14);
        //========================= Boundary Values Tests ===========================
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    public void testLength() {
        //========================= Equivalence Partition Tests ===========================
        assertTrue(_vector.length() == Math.sqrt(14));
        //========================= Boundary Values Tests ===========================
    }


    @Test
   /**
   * Test method for {@link Vector#normalize()}
   */
    public void testNormalize() {
        double sqrtVec = _vector.length();
        //========================= Equivalence Partition Tests ===========================
        assertTrue(_vector.normalize().equals(new Vector(1 / sqrtVec, 2 / sqrtVec, 3 / sqrtVec)));
        //========================= Boundary Values Tests ===========================
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    public void testDotProduct() {
        //========================= Equivalence Partition Tests ===========================
        assertTrue(_vector.dotProduct(new Vector(1, 2, 3)) == 14);
        //========================= Boundary Values Tests ===========================
        if (!isZero(v1.dotProduct(v3)))
            fail("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
    }

}














