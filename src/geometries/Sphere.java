package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Sphere is the basic class representing a sphere in Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Sphere extends RadialGeometry {

	private final Point center;

	/**
	 * Sphere constructor based on a point and radius.
	 * 
	 * @param p   center point
	 * @param rad radius
	 */
	public Sphere(Point p, double rad) {
		super(rad);
		center = p;
	}

	/**
	 * Get radius of sphere
	 * 
	 * @return radius
	 */
	public Double getRadius() {
		return radius;
	}

	/**
	 * Get center point of sphere
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
		return p.subtract(center).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		Vector u = ray.getPoint().subtract(center);
		double tm = ray.getDir().dotProduct(u);
		double d = Math.sqrt(u.lengthSquared() - tm*tm);
		if (d >= radius)
		    return null;
		double th = Math.sqrt(radius*radius - d*d);
		double t1 = tm + th;
		double t2 = tm - th;
		LinkedList<Point> intersectionPoints = new LinkedList<Point>();
		if (t1 > 0)
		    intersectionPoints.add(ray.getPoint(t1));
		if (t2 > 0)
		    intersectionPoints.add(ray.getPoint(t2));
		return intersectionPoints;
	}

}
