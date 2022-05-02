package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    /**
     *
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    final Vector direction;


    public Color getIntensity(Point p)
    {
//        // but kL and Kq are 0
//        double denominator= intensityHelp(p);
//        Vector l= getL(p);
//        return (getIntensity().scale(Math.max(0,direction.normalize().dotProduct(l))).reduce(denominator));
        Color pointIntensity = super.getIntensity(p);
        double factor = Math.max(0, direction.dotProduct(getL(p)));
        return pointIntensity.scale(factor);
    }


}