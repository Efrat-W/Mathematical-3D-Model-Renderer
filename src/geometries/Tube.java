package geometries;

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
	 * 
	 * @return radius
	 */
	public Double getRadius() {
		return radius;
	}

	/**
	 * 
	 * @return axis ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * (not relevant in current stage)
	 */
	public Vector getNormal(Point p) {
		return null;
	}

	@Override
	public String toString() {
		return "" + axisRay + ", " + radius;
	}

}
