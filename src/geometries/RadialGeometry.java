package geometries;

public abstract class RadialGeometry implements Geometry {

	final protected double radius;

	/**
	 * Radial Geometry constructor that sets the radius
	 * 
	 * @param r radius
	 */
	public RadialGeometry(double r) {
		radius = r;
	}
}
