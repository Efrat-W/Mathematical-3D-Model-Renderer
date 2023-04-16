package geometries;

/*
* Class Tube is the basic class representing a tube
* in Cartesian 3-Dimensional coordinate system.
* @author Efrat Wexler and Sari Zilberlicht
*/

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {

	/**
	 * axis ray
	 */
	protected final Ray axisRay;

	/**
	 * Tube constructor based on axis ray and radius.
	 * 
	 * @param ar  axis ray
	 * @param rad radius
	 */
	public Tube(Ray ar, double rad) {
		super(rad);
		axisRay = ar;
	}

	/**
	 * Get radius of tube
	 * 
	 * @return radius
	 */
	public Double getRadius() {
		return radius;
	}

	/**
	 * Get axis ray
	 * 
	 * @return axis ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	@Override
	public Vector getNormal(Point p) {
		return null;
	}

	@Override
	public String toString() {
		return "" + axisRay + ", " + radius;
	}

}
