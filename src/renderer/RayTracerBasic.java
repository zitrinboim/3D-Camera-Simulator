package renderer;
import geometries.Geometries;
import primitives.*;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracer {
    public RayTracerBasic(Scene scene){
        super(scene);
        return;
    }

    @Override
    public Color traceRay(Ray ray) {
        Color result = scene.background;
        List<Point> allPoints = scene.geometries.findIntersections(ray);
        if(allPoints != null){
            Point pt = ray.findClosestPoint(allPoints);
            result = calcColour(pt);
        }
        return result;
    }
    private Color calcColour(Point point){
        return scene.ambientLight.getIntensity();
    }
}
