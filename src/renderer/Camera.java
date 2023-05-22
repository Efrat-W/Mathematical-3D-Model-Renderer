package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static primitives.Util.isZero;

import java.awt.image.renderable.RenderableImage;
import java.util.MissingResourceException;

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
	 * @return
	 */
	private Color castRay(int col, int row, int nx, int ny) {
		return rayTracerBase.traceRay(constructRay(nx, ny, col, row));
	}

	/**
	 * build for each pixel a ray and get it's color
	 */
	public Camera renderImage() {
		if (cameraPoint == null || vRight == null || vUp == null || vTo == null || imgWriter == null
				|| rayTracerBase == null)
			throw new MissingResourceException("missing filed in camera", "", "");
		int nx = imgWriter.getNx();
		int ny = imgWriter.getNy();
		for (int x = 0; x < nx; x++) {
			for (int y = 0; y < ny; y++) {
				imgWriter.writePixel(x, y, castRay(x, y, nx, ny));
			}
		}
		return this;
	}

	/**
	 * print a grid on the picture
	 * 
	 * @param interval the size of each square of the grid
	 * @param color    the grid color
	 */
	public void printGrid(int interval, Color color) {
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
	}

	/**
	 * creating the picture
	 */
	public void writeToImage() {
		if (imgWriter == null)
			throw new MissingResourceException("missing filed in camera", "", "");
		imgWriter.writeToImage();
	}
}
