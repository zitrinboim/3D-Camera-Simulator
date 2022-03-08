package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * interface for all the  shapes
 * with Func to return the normal to this shape
 *
 */
public interface Geometry {
    public Vector getNormal(Point p);
}