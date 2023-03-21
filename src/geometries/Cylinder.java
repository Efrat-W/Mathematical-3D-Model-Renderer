package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

	final double height;

	/**
	 * Cylinder constructor based on radius, axis ray and height.
	 * 
	 * @param h height
	 * @param ar axis ray
	 * @param rad radius
	 */
	public Cylinder(double h, Ray ar, double rad) {
		super(ar, rad);
		height = h;
	}

	/**
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return super.toString() + ", " + height;
	}

}
