package primitives;

/*
* Class Point is the basic class representing a Point of Euclidean geometry in Cartesian
* 3-Dimensional coordinate system.
* @author Efrat Wexler and Sari Zilberlicht
*/

public class Point {
	final Double3 xyz;

	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1, d2, d3);
	}

	Point(Double3 value) {
		xyz = new Double3(value.d1, value.d2, value.d3);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Point other) {
			return xyz.equals(other.xyz);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public String toString() {
		return "" + xyz;
	}

	public Vector subtract(Point other) {
		return new Vector(xyz.subtract(other.xyz));
	}

	public Point add(Vector vec) {
		return new Point(xyz.add(vec.xyz));
	}

	public double distanceSquared(Point p) {
		return (xyz.d1 - p.xyz.d1) * (xyz.d1 - p.xyz.d1) + (xyz.d2 - p.xyz.d2) * (xyz.d2 - p.xyz.d2)
				+ (xyz.d3 - p.xyz.d3) * (xyz.d3 - p.xyz.d3);
	}

	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}
}
