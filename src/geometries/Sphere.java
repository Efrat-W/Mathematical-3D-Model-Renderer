package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

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
		if (cbr) {
			double minX = center.getX() - radius;
			double maxX = center.getX() + radius;
			double minY = center.getY() - radius;
			double maxY = center.getY() + radius;
			double minZ = center.getZ() - radius;
			double maxZ = center.getZ() + radius;
			this.box = new Border(minX, minY, minZ, maxX, maxY, maxZ);
		}
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
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double dis) {
		Point point = ray.getPoint();
		if (center.equals(point))
			return alignZero(radius) > dis ? null : List.of(new GeoPoint(this, ray.getPoint(radius)));

		Vector u = center.subtract(point);
		double tm = ray.getDir().dotProduct(u);
		double dSquared = u.lengthSquared() - tm * tm;
		double thSquared = radiusSquared - dSquared;
		if (alignZero(thSquared) <= 0)
			return null;

		double th = Math.sqrt(thSquared); // always positive!
		double t2 = alignZero(tm + th); // always greater than t1
		double t1 = alignZero(tm - th);
		double t1MinusDis = alignZero(t1 - dis);
		if (t2 <= 0 || t1MinusDis > 0)
			return null;

		if (alignZero(t2 - dis) > 0)
			// 2nd point is after the distance
			return t1 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t1)));

		// 2nd point is inside
		return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) //
				: List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1)));
	}

}
