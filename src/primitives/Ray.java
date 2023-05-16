package primitives;

import static org.junit.Assert.assertNull;
import static primitives.Util.isZero;

import java.util.List;

/**
 * Class Ray is the basic class representing Ray of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class Ray {
	private final Point p0;
	private final Vector dir;

	/**
	 * a constructor of ray based on a point and a vector
	 * 
	 * @param p   a point for the ray
	 * @param vec a vector for the ray
	 */
	public Ray(Point p, Vector vec) {
		p0 = p;
		dir = vec.normalize();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ray other)
			return p0.equals(other.p0) && dir.equals(other.dir);
		return false;
	}

	@Override
	public int hashCode() {
		return p0.hashCode();
	}

	@Override
	public String toString() {
		return "" + p0 + ", " + dir;
	}

	/**
	 * a getter for the point field
	 * 
	 * @return p0
	 */
	public Point getPoint() {
		return p0;
	}

	/**
	 * returns a point on the line of the ray at a given distance from the ray head
	 * 
	 * @param t - the distance between p0 and the point
	 * @return the point on the ray
	 */

	public Point getPoint(double t) {
		return isZero(t) ? p0 : p0.add(dir.scale(t));
	}

	/**
	 * a getter for the vector field
	 * 
	 * @return dir direction vector
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * finds closest point in points of given list to ray head p0
	 * 
	 * @param ls list of points on the ray
	 * @return point closest to p0
	 */
	public Point findClosestPoint(List<Point> ls) {
		if (ls == null)
			return null;
		
		double min = Double.POSITIVE_INFINITY;
		Point minPoint = null;
		for (Point p : ls) {
			double dist = p0.distance(p);
			if ( dist < min) {
				min = dist;
				minPoint = p;
			}
		}
		return minPoint;
	}

}
