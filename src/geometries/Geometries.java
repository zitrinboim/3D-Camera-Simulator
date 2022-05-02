package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite class to gather other {@link Geometry} based objects
 */

public class Geometries extends Intersectable {
    private List <Intersectable> _intersectables = null;

    /**
     * constructor of Geometries
     * @param intersectables array of {@link Intersectable} objects
     */
    public Geometries(Intersectable... intersectables){
        _intersectables = new LinkedList<>();
        Collections.addAll(_intersectables, intersectables);
    }

    /**
     * default constructor
     */
    public Geometries(){
        _intersectables = new LinkedList<>();
    }

    public void  add(Intersectable... intersectables) {
        Collections.addAll(_intersectables, intersectables);
    }

    @Deprecated
    public void remove(Intersectable... intersectables) {
        _intersectables.removeAll(List.of(intersectables));
    }


    @Override
    /**
     * function to find intersections between different geometries
     * we chose liked list because the cost for adding a value is O(1), and we don't need to
     * have access to the element i in the list, we just pass on the whole list.
     * so it's a better choice
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : _intersectables) {
            var geoIntersections = geometry.findGeoIntersections(ray);
            if (geoIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }

}