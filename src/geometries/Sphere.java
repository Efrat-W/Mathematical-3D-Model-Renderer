package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {

	final Point center;

	/**
	 * Sphere constructor based on a point and radius.
	 * 
	 * @param p center point
	 * @param rad radius
	 */
	public Sphere(Point p, double rad) {
		super(rad);
		center = p;
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
	 * @return center point
	 */
	public Point getPoint() {
		return center;
	}

	@Override
	public String toString() {
		return "" + center + ", " + radius;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
