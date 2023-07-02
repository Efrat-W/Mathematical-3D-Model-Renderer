package renderer;

import primitives.*;
import static primitives.Util.*;

import renderer.PixelManager.Pixel;

import java.util.MissingResourceException;
import java.util.LinkedList;

import geometries.Plane;

/**
 * Class Camera is the class representing the camera (our point of view)
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Camera {
	private final Point cameraPoint;
	private final Vector vRight, vUp, vTo;
	private double height, width;
	private double dis;

	private ImageWriter imgWriter;
	private RayTracerBase rayTracerBase;

	/* DoF Ray Tracing Improvement, declarations */
	private boolean dofFlag = false;

	private Plane focalPlane;
	private double focalPlaneDis;

	private Point[] aperturePoints;
	private double apertureSize;

	private int numOfPoints;

	private int threadsCount = 0;
	private double printInterval = 0;
	private PixelManager pixelManager;

	/**
	 * return the camera point (position)
	 * 
	 * @return cameraPoint
	 */
	public Point getPoint() {
		return cameraPoint;
	}

	/**
	 * return the vector vRight
	 * 
	 * @return vRight
	 */
	public Vector getVRight() {
		return vRight;
	}

	/**
	 * return the vector vUp
	 * 
	 * @return vUp
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * return the vector vTo
	 * 
	 * @return vTo
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * return the view plane height
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * return the view plane width
	 * 
	 * @return width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * return the view plane distance from the camera
	 * 
	 * @return dis
	 */
	public double getDis() {
		return dis;
	}

	/**
	 * constructor for camera by point ([osition) and 2 vectors (up+to)
	 * 
	 * @param p  point (position of the camera)
	 * @param vu vector up
	 * @param vt vector to
	 */
	public Camera(Point p, Vector vt, Vector vu) {
		// the vectors aren't orthogonal:
		if (!isZero(vu.dotProduct(vt)))
			throw new IllegalArgumentException();

		cameraPoint = p;
		vUp = vu.normalize();
		vTo = vt.normalize();
		vRight = vTo.crossProduct(vUp);

		this.apertureSize = 0; // initialize DoF parameters.
	}

	/**
	 * set the view plane size
	 * 
	 * @param width  of view plane
	 * @param height of view plane
	 * @return the camera
	 */
	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * set the view plane distance from the camera
	 * 
	 * @param distance between view plane and camera
	 * @return the camera
	 */
	public Camera setVPDistance(double distance) {
		this.dis = distance;
		return this;
	}

	/**
	 * set the image writer from the camera
	 * 
	 * @param imgWriter image writer
	 * @return the camera
	 */
	public Camera setImageWriter(ImageWriter imgWriter) {
		this.imgWriter = imgWriter;
		return this;
	}

	/**
	 * set the ray tracer base from the camera
	 * 
	 * @param rtb ray Tracer Base
	 * @return the camera
	 */
	public Camera setRayTracer(RayTracerBase rtb) {
		this.rayTracerBase = rtb;
		return this;
	}

	/**
	 * amount of threads setter for multi-threading
	 * 
	 * @param threads number of threads to run at the same time
	 * @return camera (builder)
	 */
	public Camera setMultiThreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("number of threads must not be negative");
		threadsCount = threads;
		return this;
	}

	/**
	 * interval setter for debug print
	 * 
	 * @param interval the print interval
	 * @return camera (builder)
	 */
	public Camera setDebugPrint(double interval) {
		if (interval < 0)
			throw new IllegalArgumentException("print interval must not be negative");
		printInterval = interval;
		return this;
	}

	/**
	 * create a ray through the center of the pixel
	 * 
	 * @param nX number of columns
	 * @param nY number of rows
	 * @param j  pixel's column
	 * @param i  pixel's row
	 * @return the ray we created
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		// image center
		Point pc = cameraPoint.add(vTo.scale(dis));
		// ratio (pixel&height)
		double rY = height / nY, rX = width / nX;
		// pixel[i,j] center
		double yI = -rY * (i - (nY - 1.0) / 2.0);
		double xJ = rX * (j - (nX - 1.0) / 2.0);
		Point pIJ = pc;
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));

		Vector vIJ = pIJ.subtract(cameraPoint);
		return new Ray(cameraPoint, vIJ);

	}

	/**
	 * Calculate color of the pixel that in sight
	 * 
	 * @param col column number in View Plane
	 * @param row row number in View Plane
	 */
	private void castRay(int col, int row, int nx, int ny) {
		Ray ray = constructRay(nx, ny, col, row);
		imgWriter.writePixel(col, row, dofFlag ? averagedBeamColor(ray) : rayTracerBase.traceRay(ray));
		pixelManager.pixelDone();
	}

	/**
	 * build for each pixel a ray and get it's color
	 *
	 * @return this camera
	 */
	public Camera renderImage() {
		if (cameraPoint == null || vRight == null || vUp == null || vTo == null || imgWriter == null
				|| rayTracerBase == null)
			throw new MissingResourceException("missing filed in camera", "", "");
		int nx = imgWriter.getNx();
		int ny = imgWriter.getNy();
		pixelManager = new PixelManager(ny, nx, printInterval);
		if (threadsCount == 0)
			for (int x = 0; x < nx; x++)
				for (int y = 0; y < ny; y++)
					castRay(x, y, nx, ny);
		else {
			var threads = new LinkedList<Thread>(); // list of threads
			while (threadsCount-- > 0) // add appropriate number of threads
				threads.add(new Thread(() -> { // add a thread with its code
					Pixel pixel; // current pixel(row,col)
					// allocate pixel(row,col) in loop until there are no more pixels
					while ((pixel = pixelManager.nextPixel()) != null)
						// cast ray through pixel (and color it – inside castRay)
						castRay(pixel.col(), pixel.row(), nx, ny);
				}));
			// start all the threads
			for (var thread : threads)
				thread.start();
			// wait until all the threads have finished
			try {
				for (var thread : threads)
					thread.join();
			} catch (InterruptedException ignore) {
			}
		}
		return this;
	}

	/**
	 * print a grid on the picture
	 * 
	 * @param interval the size of each square of the grid
	 * @param color    the grid color
	 * @return this camera
	 */
	public Camera printGrid(int interval, Color color) {
		if (imgWriter == null)
			throw new MissingResourceException("missing filed in camera", "", "");
		int nx = imgWriter.getNx();
		int ny = imgWriter.getNy();
		for (int x = 0; x < nx; x++) {
			for (int y = 0; y < ny; y++) {
				if (x % interval == 0 || y % interval == 0)
					imgWriter.writePixel(x, y, color);
			}
		}
		return this;
	}

	/**
	 * creating the picture
	 */
	public void writeToImage() {
		if (imgWriter == null)
			throw new MissingResourceException("missing filed in camera", "", "");
		imgWriter.writeToImage();
	}

	/**
	 * setter of the depth of field flag (if using it or not)
	 *
	 * @param depthOfFiled If true, the camera will have a depth of field effect.
	 * @return The camera itself.
	 */
	public Camera setDofFlag(boolean depthOfFiled) {
		dofFlag = depthOfFiled;
		return this;
	}

	/**
	 * setter for the distance of the focal plane from the camera's position
	 *
	 * @param distance The distance from the camera to the focal plane.
	 * @return The camera itself.
	 */
	public Camera setFPDistance(double distance) {
		focalPlaneDis = distance;
		focalPlane = new Plane(cameraPoint.add(this.vTo.scale(focalPlaneDis)), vTo); // the focal plane is parallel to
																						// the view plane
		return this;
	}

	/**
	 * settrer for the aperture size of the camera and initializing the points of
	 * the aperture.
	 *
	 * @param size the size of the aperture.
	 * @return The camera itself.
	 */
	public Camera setApertureSize(double size) {
		apertureSize = size;
		// initializing the points of the aperture.
		if (size != 0)
			initializeAperturePoint();

		return this;
	}

	/**
	 * settrer for the aperture size number of points on the aperture.
	 *
	 * @param num the number of the points on the aperture.
	 * @return The camera itself.
	 */
	public Camera setNumOfPoints(int num) {
		numOfPoints = num;
		return this;
	}

	/**
	 * initializing the aperture points array by calculating the distance between
	 * the points and the initial point, and then initializing the array with the
	 * points
	 */
	private void initializeAperturePoint() {

		int pointsInRow = (int) Math.sqrt(numOfPoints);

		aperturePoints = new Point[pointsInRow * pointsInRow];

		double pointsDistance = (apertureSize * 2) / pointsInRow;
		// calculate the initial point to be the point with coordinates outside the
		// aperture in the down left point,
		// so we won`t have to deal with illegal vectors.
		double s = -(apertureSize + pointsDistance / 2);
		Point initialPoint = cameraPoint.add(this.vUp.scale(s).add(this.vRight.scale(s)));

		// initializing the points array
		for (int i = 0; i < pointsInRow; i++) {
			for (int j = 0; j < pointsInRow; j++) {
				this.aperturePoints[i + j * pointsInRow] = initialPoint
						.add(this.vUp.scale((i + 1) * pointsDistance).add(this.vRight.scale((j + 1) * pointsDistance)));
			}
		}
	}

	/**
	 * averaging the colors of all the rays that were shoot from the aperture points
	 * to the point where the ray intersects the focal plane.
	 *
	 * @param ray The ray that is being traced.
	 * @return The average color of the image.
	 */
	private Color averagedBeamColor(Ray ray) {
		Color averageColor = Color.BLACK;
		Ray apertureRay;
		Color apertureColor;
		Point focalPoint = focalPlane.findGeoIntersections(ray).get(0).point;
		for (Point aperturePoint : aperturePoints) {
			apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
			apertureColor = rayTracerBase.traceRay(apertureRay);
			averageColor = averageColor.add(apertureColor.reduce(numOfPoints));
		}
		return averageColor;
	}

}
