package renderer;



import primitives.*;
import lighting.*;

import java.util.LinkedList;
import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * Camera producing rays through a view plane
 */
public class Camera {

    private  Point p0;          // camera eye
    private  Vector vUp;        // vector pointing upwards : Y axis
    private  Vector vTo;        // vector pointing towards the scene
    private  Vector vRight;     // vector pointing towards the right : X axis

    private double distance;    // cameras distance from ViewPlane
    private double width;       // ViewPlane width
    private double height;      // ViewPlane height

    private ImageWriter _imageWriter;
    private RayTracer _rayTracer;
    private int amountOfSampledRays = 0;

    /**
     * @param amountOfSampledRays the amountOfSampledRays to set
     */
    public Camera setAmountOfSampledRays(int amountOfSampledRays) {
        this.amountOfSampledRays = amountOfSampledRays;
        return this;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
    }

    public void setvUp(Vector vUp) {
        this.vUp = vUp;
    }

    public void setvTo(Vector vTo) {
        this.vTo = vTo;
    }

    public void setvRight(Vector vRight) {
        this.vRight = vRight;
    }

    public Point getP0() {
        return this.p0;
    }

    /**
     * constructor for camera ensuring the 3 vectors are orthogonal
     * @param p0 origin  point in 3D space
     * @param vUp vechu
     * @param vTo vechulei
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo))){
            throw  new IllegalArgumentException("vTo and vUp should be orthogonal");
        }

        this.p0 = p0;

        //normalizing the positional vectors
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        this.vRight = this.vTo.crossProduct(this.vUp);

    }

    // chaining methods

    /**
     * set distance between the camera and it's view plane
     * @param distance the  distance for the view plane
     * @return instance of Camera for chaining
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * setting View Plane size
     * @param width     "physical" width
     * @param height    "physical" height
     * @return instance of Camera for chaining
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

//

    /**
     * calls the original write to image to create an image from rendered scene
     */
    public void writeToImage() {
        if(_imageWriter == null)
            throw new MissingResourceException("missing imagewriter", "Camera", "in writeTorImage");
        _imageWriter.writeToImage();
    }


    /**
     * Method for creating rays and for every ray gets the color for the pixel
     */
    public Camera renderImage() {
        // In case that not all of the fields are filled
        if (_imageWriter == null)
            throw new MissingResourceException("Missing", "imageWriter resource", "exception");
        else if (_rayTracer == null)
            throw new MissingResourceException("Missing", "rayTracerBasic resource", "exception");

        // The nested loop finds and creates a ray for each pixels, finds its color and
        // writes it to the image pixles
        int nY = this._imageWriter.getNy();
        int nX = this._imageWriter.getNx();
        Ray basicRay;

        //In case the amount of rays go through pixel are only one ray (the basic ray)
        if (amountOfSampledRays == 0) {
            for (int j = 0; j < nY; j++) {
                for (int i = 0; i < nX; i++) {
                    basicRay = constructRay(nX, nY, j, i); // For each pixel calls
                    // "constructRayThroughPixel" function
                    _imageWriter.writePixel(j, i, _rayTracer.traceRay(basicRay)); // Traces the color of the ray and
                    // writes itto the image
                }
            }
        }
        //In case the amount of rays go through pixel are more than one ray (sampled rays)
        else {
            LinkedList<Ray> rays = new LinkedList<>();
            for (int j = 0; j < nY; j++) {
                for (int i = 0; i < nX; i++) {
                    rays = constructSampledRays(nX, nY, j, i);
                    _imageWriter.writePixel(j, i, _rayTracer.calcAverageColor(rays)); // Traces the color of the
                    //rays and writes it to the image
                }
            }
        }
        return this;
    }
    /**
     * paints the image as a grid according to the wanted interval and color of grid lines
     * @param interval length of wanted interval
     * @param color wanted color for grid lines
     */
    public void printGrid(int interval, Color color) {
        if(_imageWriter == null)
            throw new MissingResourceException("missing imageawriter", "Camera", "in print Grid");
        for (int j = 0; j< _imageWriter.getNx();j++){
            for (int i = 0; i< _imageWriter.getNy();i++){
                //grid 16 X 10
                if(j% interval == 0 || i% interval ==0){
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }

    }


    public Camera setImageWriter(ImageWriter imageWriter) {
        this._imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracer rayTracer) {
        this._rayTracer = rayTracer;
        return this;
    }


    /**
     * Finds the middle of the pixle
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    private Point findCenterOfPixel(int nX, int nY, int j, int i) {
        // Image center:
        Point pCenter = this.p0.add(vTo.scale(this.distance));

        // Ratio:
        double Ry = this.height / nY;
        double Rx = this.width / nX;

        if (nX % 2 == 0 || nY % 2 == 0) { // In case the number of columns or rows is even, it moves the Pceneter to the
            // (0,0) pixel
            pCenter = new Point(pCenter.getX() - Rx / 2, pCenter.getY() - Ry / 2, pCenter.getZ());
        }
        // Pixel[i,j] center
        double yi = alignZero(-(i - (nY - 1) / 2) * Ry);
        double xj = alignZero((j - (nX - 1) / 2) * Rx);
        Point pIJ = pCenter;
        // To avoid a zero vector exception
        if (xj != 0)
            pIJ = pIJ.add(vRight.scale(xj));
        if (yi != 0)
            pIJ = pIJ.add(vUp.scale(yi));
        return pIJ;

    }

    /**
     * Calculates the super sampled rays in a pixel
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return Linked List of the sampled rays, the basic ray included
     */
    public LinkedList<Ray> constructSampledRays(int nX, int nY, int j, int i) {
        LinkedList<Ray> result = new LinkedList<Ray>();
        Point pCenter = findCenterOfPixel(nX, nY, j, i);
        double Ry = this.height / nY;
        double Rx = this.width / nX;
        double randX;
        double randY;
        Point sPoint;
        Ray sRay;
        result.add(new Ray(p0, pCenter.subtract(this.p0))); //adding the basic ray
        // The loop finds random rays at the needed amount in the margins of the pixel
        for (int k = 0; k < amountOfSampledRays; k++) {
            randX = random(-Rx / 2, Rx / 2); // Random x value of the new point on the view plane
            randY = random(-Ry / 2, Ry / 2); // Random y value of the new point on the view plane
            sPoint = pCenter;
            if (randX != 0) {
                sPoint.add(vRight.scale(randX));
            }
            if (randY != 0) {
                sPoint.add(vUp.scale(randY));
            }
            sRay = new Ray(this.p0, sPoint.subtract(this.p0)); // Creates the sampled ray
            result.add(sRay); // Add the ray to the list of sampled rays
        }
        return result;

    }

    /**
          * Constructing a ray through a pixel
          *
          * @param Nx number of pixels widthwise
          * @param Ny number of pixels heightwise
          * @param j Y value of pixel wanted
          * @param i x value of pixel wanted
          * @return ray form the camera to Pixel[i,j]
          */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        //Image center
        Point Pc = p0.add(vTo.scale(distance));

        //Ratio (pixel width & height)
        double Ry =height/ Ny;
        double Rx = width/Nx;

        //Pixel[i,j] center
        Point Pij = Pc;

        //delta values for going to Pixel[i,j]  from Pc

        double yI =  -(i - (Ny -1)/2d)* Ry;
        double xJ =  (j - (Nx -1)/2d)* Rx;

        if (! isZero(xJ) ){
            Pij = Pij.add(vRight.scale(xJ));
        }
        if (! isZero(yI)) {
            Pij = Pij.add(vUp.scale(yI));
        }

        return new Ray(p0, Pij.subtract(p0));
    }
}

//    /**
//     * The actual rendering function , according to data received from the ray tracer - colours each pixel appropriately thus
//     * rendering the image
//     */
//    public Camera renderImage() {
//        try {
//            if (_imageWriter == null) {
//                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
//            }
//            if (_rayTracer == null) {
//                throw new MissingResourceException("missing resource", RayTracer.class.getName(), "");
//            }
//
//            //rendering the image
//            int nX = _imageWriter.getNx();
//            int nY = _imageWriter.getNy();
//            for (int i = 0; i < nY; i++) {
//                for (int j = 0; j < nX; j++) {
 //              Ray ray = constructRay(nX, nY, j, i);
//                    Color pixelColor = _rayTracer.traceRay(ray);
//                    _imageWriter.writePixel(j, i, pixelColor);
//                }
//            }
//        } catch (MissingResourceException e) {
//            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
//        }
//        return this;
//    }

