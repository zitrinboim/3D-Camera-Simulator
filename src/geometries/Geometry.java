package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * interface for all the  shapes
 * with Func to return the normal to this shape
 *
 */
public interface Geometry extends Intersectable {
    /**
     * calculates and returns the normal vector from the shape
     * @param point {@link Point} external to the shape
     * @return normal vector {@link Vector}
     */
    Vector getNormal(Point point);
}