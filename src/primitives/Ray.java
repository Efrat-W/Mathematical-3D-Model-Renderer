package primitives;

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
	 * a getter for the vector field
	 * 
	 * @return dir
	 */
	public Vector getDir() {
		return dir;
	}
}
