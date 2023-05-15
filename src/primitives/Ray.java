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
    final Point p0;
    final Vector dir;

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
     * returns the intersection point by a given distance
     * 
     * @param t - the distance between p0 and the intersection point
     * @return the intersection point
     */

    public Point getPoint(double t) {
	if (isZero(t))
	    return p0;
	return p0.add(dir.scale(t));
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
     * @return point closest to p0
     */
    public Point findClosestPoint(List<Point> ls) {
	if (ls.isEmpty())
	    return null;
	double min = 100000;
	Point minPoint = new Point(0, 0, 0);
	for (Point p : ls)
	    if (p0.distance(p) < min) { min = p0.distance(p); minPoint = p; }
	return minPoint;
    }

}
