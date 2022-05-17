package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.*;

public class SpotLight extends PointLight implements LightSource {

    private double radius;

    /**
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    final Vector direction;

    /**
     * Constructor of Spotlight which receives four params
     *
     * @param intensity light intensity
     * @param position  Light starts location
     * @param direction direction of the light
     * @param radius
     */
    public SpotLight(Color intensity, Point position, Vector direction, double radius) {
        super(intensity, position);
        this.direction = direction.normalize();
        this.radius = radius;
    }

    public Color getIntensity(Point p) {
       double projection = direction.dotProduct(getL(p));

        double factor = Math.max(0, projection);

        Color pointIntensity = super.getIntensity(p);

        return pointIntensity.scale(factor);
    }


}
