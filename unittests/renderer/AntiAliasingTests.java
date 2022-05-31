package renderer;

import static java.awt.Color.WHITE;
import static java.awt.Color.blue;
import static org.junit.jupiter.api.Assertions.*;

import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

//import elements.Camera;
//import elements.PointLight;
//import elements.SpotLight;
import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
//import renderer.Render;
import scene.Scene;

/**
 * @author 97253 Test classs for Anti-aliasing improvment
 */

public class AntiAliasingTests {
    /**
     * Produce a picture of a pyramid lighted using Anti-aliasing improvement
     **/

    @Test
    public void test() {
        Scene scene = new Scene.SceneBuilder("Test scene").setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();
        Camera camera = new Camera(new Point(-140, 20, 35), new Vector(1, -0.15, -0.25), new Vector(1, 0, 4))//
                .setVPSize(200, 200).setVPDistance(1000).setAmountOfSampledRays(17); // Turn on/off the test by

        scene.getGeometries().add( //
                new Geometries(
                        new Polygon(new Point(10, 0, -10), new Point(0, 10, -10), new Point(-10, 0, -10),
                                new Point(0, -10, -10)).setMaterial(
                                new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.2).setKr(0.0)),
                        new Polygon(new Point(10, 0, -10), new Point(0, -10, -10), new Point(0, -10, 0),
                                new Point(10, 0, 0)).setMaterial(
                                new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.0).setKr(1.0)),
                        new Polygon(new Point(10, 0, -10), new Point(0, 10, -10), new Point(0, 10, 0),
                                new Point(10, 0, 0))
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.4)),
                        new Polygon(new Point(-10, 0, -10), new Point(0, 10, -10), new Point(0, 10, 0),
                                new Point(-10, 0, 0))
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.8)),
                        new Polygon(new Point(-10, 0, -10), new Point(0, -10, -10), new Point(0, -10, 0),
                                new Point(-10, 0, 0))
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.4)),
                        new Polygon(new Point(10, 0, 0), new Point(0, -10, 0), new Point(-10, 0, 0),
                                new Point(0, 10, 0)).setMaterial(
                                new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.5))),
                new Geometries(new Triangle(new Point(10, 0, 0), new Point(0, -10, 0), new Point(0, 0, 10)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setKt(0.5).setKr(0.0)), //
                        new Triangle(new Point(10, 0, 0), new Point(0, 10, 0), new Point(0, 0, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setKt(1.0)), //
                        new Triangle(new Point(-10, 0, 0), new Point(0, 10, 0), new Point(0, 0, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setKt(0.8)), //
                        new Triangle(new Point(-10, 0, 0), new Point(0, -10, 0), new Point(0, 0, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setKt(1.0)), //
                        new Sphere(new Point(0, 0, 3), 2) //
                                .setEmission(new Color(java.awt.Color.BLUE)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6))));

        scene.getLights()
                .add(new SpotLight(new Color(700, 400, 400), new Point(30, 30, 100), new Vector(0, 0, -1)) //
                        .setkL(4E-5).setkQ(2E-7));

        scene.getLights().add(new PointLight(new Color(500, 250, 250), new Point(60, 60, 200)));

        scene.getLights()
                .add(new SpotLight(new Color(700, 400, 400), new Point(120, 120, 300), new Vector(0, 0, -1)) //
                        .setkL(4E-5).setkQ(2E-7));


        ImageWriter imageWriter = new ImageWriter("antiAliasingPyramidAfter", 600, 600);
         //
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();


    }

    @Test
    public void testPlata() {
        Scene scene = new Scene.SceneBuilder("Test scene").setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();
        Camera camera = new Camera(new Point(-140, 0, 0), new Vector(1, 05, 0), new Vector(0, 0, 1))//
                .setVPSize(200, 200).setVPDistance(1000).setAmountOfSampledRays(5); // Turn on/off the test by
        // changing the amount
//        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setVPSize(200, 200).setVPDistance(1000);
//        Scene scene = new Scene.SceneBuilder("Test scene").setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getGeometries().add( //
                new Geometries(

               new Plane(new Point(0,0,0),new Point(0,1,0),new Point(0,0,1)).setEmission(new Color(blue))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setKt(1.0)))); //

        scene.getLights()
                .add(new SpotLight(new Color(700, 400, 400), new Point(30, 30, 100), new Vector(0, 0, -1)) //
                        .setkL(4E-5).setkQ(2E-7));

        scene.getLights().add(new PointLight(new Color(500, 250, 250), new Point(60, 60, 200)));

        scene.getLights()
                .add(new SpotLight(new Color(700, 400, 400), new Point(120, 120, 300), new Vector(0, 0, -1)) //
                        .setkL(4E-5).setkQ(2E-7));


        ImageWriter imageWriter = new ImageWriter("Plata", 600, 600);
        //
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();


    }

}