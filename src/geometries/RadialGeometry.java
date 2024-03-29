package geometries;

/**
 * Class RadialGeometry is an abstract class representing radial geometries in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public abstract class RadialGeometry extends Geometry {

	/**
	 * the radius of the radial geometry
	 */
	protected final double radius;
	/**
	 * the squared radius of the radial geometry
	 */
	protected final double radiusSquared;

	/**
	 * Get radius of sphere
	 * 
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Radial Geometry constructor that sets the radius
	 * 
	 * @param r radius
	 */
	public RadialGeometry(double r) {
		// if (r <= 0)
		// throw new IllegalArgumentException("radius has to be of positive value");
		radius = r;
		radiusSquared = r * r;
	}
}
