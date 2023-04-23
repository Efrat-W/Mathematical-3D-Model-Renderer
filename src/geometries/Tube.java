package geometries;

import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Tube is the basic class representing a tube in Cartesian 3-Dimensional
 * coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Tube extends RadialGeometry {

	/**
	 * axis ray
	 */
	protected final Ray axisRay;

	/**
	 * Tube constructor based on axis ray and radius.
	 * 
	 * @param axis_ray axis ray
	 * @param rad      radius
	 */
	public Tube(Ray axis_ray, double rad) {
		super(rad);
		axisRay = axis_ray;
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
		Vector v = axisRay.getDir();
		Point p1 = axisRay.getPoint();
		double t = v.dotProduct(p.subtract(p1));
		if (isZero(t)) // in case of p being right on top of axis ray point
			return p.subtract(p1);
		Point o = p1.add(v.scale(t));
		return p.subtract(o).normalize();
	}

	@Override
	public List<Point> findIntsersections(Ray ray) {
		return null;
	}

	@Override
	public String toString() {
		return "" + axisRay + ", " + radius;
	}

}
