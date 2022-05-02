package geometries;

import primitives.Material;
import primitives.Vector;
import primitives.Point;
import primitives.Color;


/**
 * interface for all the  shapes
 * with Func to return the normal to this shape
 *
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    public Color getEmission() {
        return emission;
    }


    /**
     * setter for material according to builder design pattern
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter for material
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    private Material material=new Material();

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }



    /**
     * calculates and returns the normal vector from the shape
     * @param point {@link Point} external to the shape
     * @return normal vector {@link Vector}
     */
    abstract public Vector getNormal(Point point);
}

