package primitives;

/**
 * Class Point is the basic class representing a Point of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class Point {
	final Double3 xyz;

	/**
	 * a constructor of a point based on three coordinates
	 * 
	 * @param d1 first coordinate of the point
	 * @param d2 second coordinate of the point
	 * @param d3 third coordinate of the point
	 */
	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1, d2, d3);
	}

	/**
	 * a constructor of a point based on a double3 value
	 * 
	 * @param value a double3 to initiate the point's coordinates
	 */
	Point(Double3 value) {
		xyz = new Double3(value.d1, value.d2, value.d3);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Point other) && xyz.equals(other.xyz);
	}

	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public String toString() {
		return "" + xyz;
	}

	/**
	 * Vector subtraction
	 * 
	 * @param other a point to subtract
	 * @return a new vector from the other point to current point
	 */
	public Vector subtract(Point other) {
		return new Vector(xyz.subtract(other.xyz));
	}

	/**
	 * Adding a vector to a Point
	 * 
	 * @param vec the vector to add
	 * @return a new point after adding
	 */
	public Point add(Vector vec) {
		return new Point(xyz.add(vec.xyz));
	}

	/**
	 * Calculates the squared distance between two points
	 * 
	 * @param p the other pint we calculate the distance of
	 * @return the squared distance
	 */
	public double distanceSquared(Point p) {
		double dx = xyz.d1 - p.xyz.d1;
		double dy = xyz.d2 - p.xyz.d2;
		double dz = xyz.d3 - p.xyz.d3;
		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * Calculates the distance between two points
	 * 
	 * @param p the other pint we calculate the distance of
	 * @return the distance
	 */
	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}

	/**
	 * get x coordinate of point
	 * 
	 * @return x coordinate
	 */
	public double getX() {
		return xyz.d1;
	}

	/**
	 * get y coordinate of point
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return xyz.d2;
	}

	/**
	 * get z coordinate of point
	 * 
	 * @return z coordinate
	 */
	public double getZ() {
		return xyz.d3;
	}
}
