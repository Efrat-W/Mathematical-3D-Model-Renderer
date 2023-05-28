package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.*;

/**
 * Class Triangle is the basic class representing a triangle in Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class Triangle extends Polygon {

	/**
	 * Plane constructor based on 3 vertices of the triangle.
	 * 
	 * @param p1 vertex of the triangle
	 * @param p2 vertex of the triangle
	 * @param p3 vertex of the triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double dis) {
		var intersection = this.plane.findGeoIntersections(ray, dis);
		if (intersection == null)
			return null;

		Point p0 = ray.getPoint();
		Vector v = ray.getDir();

		Vector v1 = this.vertices.get(0).subtract(p0);
		Vector v2 = this.vertices.get(1).subtract(p0);
		Vector n1 = v1.crossProduct(v2).normalize();
		double vn1 = alignZero(v.dotProduct(n1));
		if (vn1 == 0)
			return null;

		Vector v3 = this.vertices.get(2).subtract(p0);
		Vector n2 = v2.crossProduct(v3).normalize();
		double vn2 = alignZero(v.dotProduct(n2));
		if (vn1 * vn2 <= 0)
			return null;

		Vector n3 = v3.crossProduct(v1).normalize();
		double vn3 = alignZero(v.dotProduct(n3));
		if (vn1 * vn3 <= 0)
			return null;

		intersection.get(0).geometry = this;
		return intersection;
	}
}
