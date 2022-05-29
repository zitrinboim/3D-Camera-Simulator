package renderer;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;

/**
 * an abstract class for tracing the rays path through the scene
 */
public abstract class RayTracer {
    protected  final Scene scene;

    public RayTracer(Scene scene) {
        this.scene = scene;
    }
    public abstract Color traceRay(Ray ray);
    public abstract Color calcAverageColor(LinkedList<Ray> rays);

}
