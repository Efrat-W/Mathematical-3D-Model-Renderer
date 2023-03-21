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

	public Ray(Point p, Vector vec) {
		p0 = p;
		dir = vec.normalize();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ray other) {
			return p0.equals(other.p0) && dir.equals(other.dir);
		}
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

	public Point getPoint() {
		return p0;
	}

	public Vector getDir() {
		return dir;
	}

}
