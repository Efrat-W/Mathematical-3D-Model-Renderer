package geometries;

import java.util.List;
import static primitives.Util.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Plane is the basic class representing a plane in Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Plane extends Geometry {

	private final Point q0;
	private final Vector normal;

	/**
	 * Plane constructor based on 3 points on the plane.
	 * 
	 * @param p1 point on the plane
	 * @param p2 point on the plane
	 * @param p3 point on the plane
	 */
	public Plane(Point p1, Point p2, Point p3) {
		q0 = p1;
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		normal = v1.crossProduct(v2).normalize();
	}

	/**
	 * Plane constructor based on a point and a normal vector perpendicular to the
	 * plane.
	 * 
	 * @param p   point on the plane
	 * @param vec normal vector to the plane
	 */
	public Plane(Point p, Vector vec) {
		q0 = p;
		normal = vec.normalize();
	}

	@Override
	public Vector getNormal(Point p) {
		return normal;
	}

	/**
	 * gets normal vector
	 * 
	 * @return normal to plane
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		if (ray.getPoint().equals(this.q0))
			return null;
		double Numerator = this.normal.dotProduct(this.q0.subtract(ray.getPoint()));
		double denominator = this.normal.dotProduct(ray.getDir());
		if (isZero(Numerator) || isZero(denominator))
			return null;
		double t = Numerator / denominator;
		return alignZero(t) <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
	}

	@Override
	public String toString() {
		return "" + q0 + ", " + normal;
	}

	/**
	 * get the point of the definition of the plane
	 * 
	 * @return point
	 */
	public Point getPoint() {
		return q0;
	}

}
