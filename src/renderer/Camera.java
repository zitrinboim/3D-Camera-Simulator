//package renderer;
//import primitives.*;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.MissingResourceException;
//import static primitives.Util.*;
//
//
///**
// * Camera producing rays through a view plane
// */
//public class Camera {
//    private  Point p0;          // camera eye
//    private  Vector vUp;        // vector pointing upwards : Y axis
//    private  Vector vTo;        // vector pointing towards the scene
//    private  Vector vRight;     // vector pointing towards the right : X axis
//    private double distance;    // cameras distance from ViewPlane
//    private double width;       // ViewPlane width
//    private double height;      // ViewPlane height
//    private ImageWriter _imageWriter;
//    private RayTracer _rayTracer;
//    private int amountOfSampledRays = 0;
//
//    private int _threads = 1;
//    private final int SPARE_THREADS = 2;
//    private boolean _print = false;
//
//    /**
//     * @param amountOfSampledRays the amountOfSampledRays to set
//     */
//    public Camera setAmountOfSampledRays(int amountOfSampledRays) {
//        this.amountOfSampledRays = amountOfSampledRays;
//        return this;
//    }
//
//    public void setP0(Point p0) {
//        this.p0 = p0;
//    }
//
//    public void setvUp(Vector vUp) {
//        this.vUp = vUp;
//    }
//
//    public void setvTo(Vector vTo) {
//        this.vTo = vTo;
//    }
//
//    public void setvRight(Vector vRight) {
//        this.vRight = vRight;
//    }
//
//    public Point getP0() {
//        return this.p0;
//    }
//
//    /**
//     * constructor for camera ensuring the 3 vectors are orthogonal
//     * @param p0 origin  point in 3D space
//     * @param vUp vechu
//     * @param vTo vechulei
//     */
//    public Camera(Point p0, Vector vTo, Vector vUp) {
//        if(!isZero(vUp.dotProduct(vTo))){
//            throw  new IllegalArgumentException("vTo and vUp should be orthogonal");
//        }
//        this.p0 = p0;
//        //normalizing the positional vectors
//        this.vTo = vTo.normalize();
//        this.vUp = vUp.normalize();
//        this.vRight = this.vTo.crossProduct(this.vUp);
//    }
//    // chaining methods
//    /**
//     * set distance between the camera and it's view plane
//     * @param distance the  distance for the view plane
//     * @return instance of Camera for chaining
//     */
//    public Camera setVPDistance(double distance) {
//        this.distance = distance;
//        return this;
//    }
//    /**
//     * setting View Plane size
//     * @param width     "physical" width
//     * @param height    "physical" height
//     * @return instance of Camera for chaining
//     */
//    public Camera setVPSize(double width, double height) {
//        this.width = width;
//        this.height = height;
//        return this;
//    }
//    /**
//     * Set multithreading <br>
//     * - if the parameter is 0 - number of coress less 2 is taken
//     *
//     * @param threads number of threads
//     * @return the Render object itself
//     */
//    public Camera setMultithreading(int threads) {
//        if (threads < 0)
//            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
//        if (threads != 0)
//            _threads = threads;
//        else {
//            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
//            if (cores <= 2)
//                _threads = 1;
//            else
//                _threads = cores;
//        }
//        return this;
//    }
//
//    /**
//     * Set debug printing on
//     *
//     * @return the Render object itself
//     */
//    public Camera setDebugPrint() {
//        _print = true;
//        return this;
//    }
//    /**
//     * calls the original write to image to create an image from rendered scene
//     */
//    public void writeToImage() {
//        if(_imageWriter == null)
//            throw new MissingResourceException("missing imagewriter", "Camera", "in writeTorImage");
//        _imageWriter.writeToImage();
//    }
//    /**
//     * Method for creating rays and for every ray gets the color for the pixel
//     */
//    public Camera renderImage() {
//        // In case that not all of the fields are filled
//        if (_imageWriter == null)
//            throw new MissingResourceException("Missing", "imageWriter resource", "exception");
//        else if (_rayTracer == null)
//            throw new MissingResourceException("Missing", "rayTracerBasic resource", "exception");
//
//        // The nested loop finds and creates a ray for each pixels, finds its color and
//        // writes it to the image pixles
//        int nY = this._imageWriter.getNy();
//        int nX = this._imageWriter.getNx();
//        Ray basicRay;
//
////        //In case the amount of rays go through pixel are only one ray (the basic ray)
////        if (amountOfSampledRays == 0) {
////            for (int j = 0; j < nY; j++) {
////                for (int i = 0; i < nX; i++) {
////                    basicRay = constructRay(nX, nY, j, i); // For each pixel calls
////                    // "constructRayThroughPixel" function
////                    _imageWriter.writePixel(j, i, _rayTracer.traceRay(basicRay)); // Traces the color of the ray and
////                    // writes itto the image
////                }
////            }
////        }
//        //In case the amount of rays go through pixel are more than one ray (sampled rays)
////        else {
//            int threadsCount = 3;
////            List<Ray> rays = new LinkedList<>();
//            Pixel.initialize(nY, nX, 1);
//            while (threadsCount-- > 0) {
//                new Thread(() -> {
//                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
//                        //        castRay(nY, nX, pixel.col, pixel.row);
//                        List<Ray> rays  = constructRaysThroughPixel(nX, nY, pixel.col, pixel.row);
//                        _imageWriter.writePixel(pixel.col, pixel.row, _rayTracer.calcAverageColor(rays));
//                    }
//                }).start();
//            }
//            Pixel.waitToFinish();
//
//        return this;
//    }
//
//    /**
//     * paints the image as a grid according to the wanted interval and color of grid lines
//     * @param interval length of wanted interval
//     * @param color wanted color for grid lines
//     */
//    public void printGrid(int interval, Color color) {
//        if(_imageWriter == null)
//            throw new MissingResourceException("missing imageawriter", "Camera", "in print Grid");
//        for (int j = 0; j< _imageWriter.getNx();j++){
//            for (int i = 0; i< _imageWriter.getNy();i++){
//                //grid 16 X 10
//                if(j% interval == 0 || i% interval ==0){
//                    _imageWriter.writePixel(j, i, color);
//                }
//            }
//        }
//    }
//    public Camera setImageWriter(ImageWriter imageWriter) {
//        this._imageWriter = imageWriter;
//        return this;
//    }
//    public Camera setRayTracer(RayTracer rayTracer) {
//        this._rayTracer = rayTracer;
//        return this;
//    }
//    /**
//     * Compute beam rays that intersect the view plane at pixel include the pixel's
//     * central points Compute the ray that start in camera and intersect the view
//     * plane at the center of the pixel and after compute list of rays that
//     *
//     * @param nX : Number of columns in the view plane
//     * @param nY : Number of rows in the view plane
//     * @param j  : The row's index of the pixel
//     * @param i  : The column's index of the pixel
//     * @return : beam rays as a list.
//     */
//    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {
//        Point pCenter = p0.add(vTo.scale(distance));
//        Point pCenterOfPixel = constructSquareCentralPoint(height / nY, width / nX, nX, nY, j, i, pCenter);
//        List<Ray> rays = new LinkedList<Ray>();
//        rays.add(new Ray(p0, pCenterOfPixel.subtract(p0)));
//
//        if (amountOfSampledRays != 0) {
//            double squareHeight = height / nY / amountOfSampledRays;
//            double squareWidth = width / nX / amountOfSampledRays;
//            for (int row = 0; row < amountOfSampledRays; row++)
//                for (int colmun = 0; colmun < amountOfSampledRays; colmun++) {
//                    Point result = constructSquareCentralPoint(squareHeight, squareWidth, amountOfSampledRays, amountOfSampledRays, colmun, row,
//                            pCenterOfPixel);
//                    rays.add(new Ray(p0, result.subtract(p0)));
//                }
//        }
//
//
//        return rays;
//    }
//    /**
//     * calculate central point of square in target plane
//     *
//     * @param squareHeight - The height of the square
//     * @param squareWidth  - The height of the square
//     * @param nX           - Number of columns in the target plane
//     * @param nY           - Number of rows in the target plane
//     * @param j            - The row's index of the square
//     * @param i            - The column's index of the square
//     * @param pCenter      - Central point of the square
//     * @return point of square in target plane
//     */
//    private Point constructSquareCentralPoint(double squareHeight, double squareWidth, int nX, int nY, int j, int i,
//                                              Point pCenter) {
//
//        double heighFromPc = -((i - (nY - 1) / 2d) * squareHeight);
//        double widthFromPc = (j - (nX - 1) / 2d) * squareWidth;
//        Point pIJ = pCenter;
//        if (heighFromPc != 0) {
//            pIJ = pIJ.add(vUp.scale(heighFromPc));
//        }
//        if (widthFromPc != 0) {
//            pIJ = pIJ.add(vRight.scale(widthFromPc));
//        }
//        return pIJ;
//    }
//    /**
//     * Constructing a ray through a pixel
//     *
//     * @param Nx number of pixels widthwise
//     * @param Ny number of pixels heightwise
//     * @param j Y value of pixel wanted
//     * @param i x value of pixel wanted
//     * @return ray form the camera to Pixel[i,j]
//     */
//    public Ray constructRay(int Nx, int Ny, int j, int i) {
//        //Image center
//        Point Pc = p0.add(vTo.scale(distance));
//
//        //Ratio (pixel width & height)
//        double Ry =height/ Ny;
//        double Rx = width/Nx;
//
//        //Pixel[i,j] center
//        Point Pij = Pc;
//
//        //delta values for going to Pixel[i,j]  from Pc
//
//        double yI =  -(i - (Ny -1)/2d)* Ry;
//        double xJ =  (j - (Nx -1)/2d)* Rx;
//
//        if (! isZero(xJ) ){
//            Pij = Pij.add(vRight.scale(xJ));
//        }
//        if (! isZero(yI)) {
//            Pij = Pij.add(vUp.scale(yI));
//        }
//        return new Ray(p0, Pij.subtract(p0));
//    }
//}
//
//
package renderer;
import primitives.*;
import java.util.LinkedList;
import java.util.List;
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
    int threadsCount =1;
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

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Camera setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            threadsCount = threads;

        return this;
    }
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


        /////////////////////////////////
        Pixel.initialize(nY, nX, 1);

        while (threadsCount-- > 0) {

            new Thread(() -> {

                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                    List<Ray> rays = new LinkedList<>();
                    rays = constructRaysThroughPixel(nX, nY, pixel.col, pixel.row);
                    _imageWriter.writePixel( pixel.col, pixel.row, _rayTracer.calcAverageColor(rays));
                }
            }).start();
        }
            Pixel.waitToFinish();

            ///////////////////////////////////



//        //In case the amount of rays go through pixel are only one ray (the basic ray)
//        if (amountOfSampledRays == 0) {
//            for (int j = 0; j < nY; j++) {
//                for (int i = 0; i < nX; i++) {
//                    basicRay = constructRay(nX, nY, j, i); // For each pixel calls
//                    // "constructRayThroughPixel" function
//                    _imageWriter.writePixel(j, i, _rayTracer.traceRay(basicRay)); // Traces the color of the ray and
//                    // writes itto the image
//                }
//            }
//        }
//        //In case the amount of rays go through pixel are more than one ray (sampled rays)
//        else {
//            List<Ray> rays = new LinkedList<>();
//            for (int j = 0; j < nY; j++) {
//                for (int i = 0; i < nX; i++) {
//                    rays = constructRaysThroughPixel(nX, nY, j, i);
//                    _imageWriter.writePixel(j, i, _rayTracer.calcAverageColor(rays)); // Traces the color of the
//                    //rays and writes it to the image
//                }
//            }
//        }
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
     * Compute beam rays that intersect the view plane at pixel include the pixel's
     * central points Compute the ray that start in camera and intersect the view
     * plane at the center of the pixel and after compute list of rays that
     *
     * @param nX : Number of columns in the view plane
     * @param nY : Number of rows in the view plane
     * @param j  : The row's index of the pixel
     * @param i  : The column's index of the pixel
     * @return : beam rays as a list.
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {
        Point pCenter = p0.add(vTo.scale(distance));
        Point pCenterOfPixel = constructSquareCentralPoint(height / nY, width / nX, nX, nY, j, i, pCenter);
        List<Ray> rays = new LinkedList<Ray>();
        rays.add(new Ray(p0, pCenterOfPixel.subtract(p0)));

        if (amountOfSampledRays != 0) {
            double squareHeight = height / nY / amountOfSampledRays;
            double squareWidth = width / nX / amountOfSampledRays;
            for (int row = 0; row < amountOfSampledRays; row++)
                for (int colmun = 0; colmun < amountOfSampledRays; colmun++) {
                    Point result = constructSquareCentralPoint(squareHeight, squareWidth, amountOfSampledRays, amountOfSampledRays, colmun, row,
                            pCenterOfPixel);
                    rays.add(new Ray(p0, result.subtract(p0)));
                }
        }
        return rays;
    }
    /**
     * calculate central point of square in target plane
     *
     * @param squareHeight - The height of the square
     * @param squareWidth  - The height of the square
     * @param nX           - Number of columns in the target plane
     * @param nY           - Number of rows in the target plane
     * @param j            - The row's index of the square
     * @param i            - The column's index of the square
     * @param pCenter      - Central point of the square
     * @return point of square in target plane
     */
    private Point constructSquareCentralPoint(double squareHeight, double squareWidth, int nX, int nY, int j, int i,
                                              Point pCenter) {

        double heighFromPc = -((i - (nY - 1) / 2d) * squareHeight);
        double widthFromPc = (j - (nX - 1) / 2d) * squareWidth;
        Point pIJ = pCenter;
        if (heighFromPc != 0) {
            pIJ = pIJ.add(vUp.scale(heighFromPc));
        }
        if (widthFromPc != 0) {
            pIJ = pIJ.add(vRight.scale(widthFromPc));
        }
        return pIJ;
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