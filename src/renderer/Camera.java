package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

/**
 * Class Camera is the class representing the camera (our point of view)
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Camera {
	private Point cameraPoint;
	private Vector vRight, vUp, vTo;
	private double height, width, dis;

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
		vRight = vt.crossProduct(vu).normalize();
		vUp = vu.normalize();
		vTo = vt.normalize();
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
	 * create a ray through the center of the pixel
	 * 
	 * @param nX       number of columns
	 * @param nY       number of rows
	 * @param j        pixel's column
	 * @param ipixel's row
	 * @return the ray we created
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		// image center
		Point pc = cameraPoint.add(vTo.scale(dis));
		// ratio (pixel&height)
		double rY = height / nY, rX = width / nX;
		// pixel[i,j] center
		double yI = -rY * (i - (double) (nY - 1) / 2);
		double xJ = rX * (j - (double) (nX - 1) / 2);
		Point pIJ = pc;
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));

		Vector vIJ = pIJ.subtract(cameraPoint);
		return new Ray(cameraPoint, vIJ);

	}

}
