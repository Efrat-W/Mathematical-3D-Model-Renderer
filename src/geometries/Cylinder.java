package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;
import static primitives.Util.alignZero;

import java.util.List;

/**
 * Class Cylinder is the basic class representing a cylinder in Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Cylinder extends Tube {

	private final double height;

	/**
	 * Cylinder constructor based on radius, axis ray and height.
	 * 
	 * @param h        height
	 * @param axis_ray axis ray
	 * @param rad      radius
	 */
	public Cylinder(double h, Ray axis_ray, double rad) {
		super(axis_ray, rad);
		height = h;
	}

	/**
	 * Get height of cylinder
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point p) {
		Vector v = axisRay.getDir();
		Point p0 = axisRay.getPoint();
		if (p.equals(p0))
			return v;
		double t = v.dotProduct(p.subtract(p0));
		if (isZero(t) || alignZero(t - height) == 0) // on bases
			return v;
		return super.getNormal(p);
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double dis) {
		Point rayP = axisRay.getPoint();
		Vector rayV = axisRay.getDir();
		GeoPoint pOne = null;
		GeoPoint pTwo = null;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flagUp = false;
		boolean flagUnder = false;
		// get tube intersections
		List<GeoPoint> list = super.findGeoIntersections(ray, dis);
		if (list != null)
			if (list.size() == 1) {
				Point p = list.get(0).point;
				double d = rayV.dotProduct(p.subtract(rayP));
				d = d < 0 ? -d : d;
				// if point is in the range
				if (alignZero(height - d) > 0)
					flag1 = true;
			} else if (list.size() == 2) {
				pOne = new GeoPoint(this, list.get(0).point);
				pTwo = new GeoPoint(this, list.get(1).point);
				double d1 = rayV.dotProduct(pOne.point.subtract(rayP));
				d1 = d1 < 0 ? -d1 : d1;

				double d2 = rayV.dotProduct(pTwo.point.subtract(rayP));
				d2 = d2 < 0 ? -d2 : d2;

				// if point is in the range
				if (alignZero(height - d1) > 0 && alignZero(pOne.point.distance(rayP)) < dis)
					flag1 = true;
				// if point is in the range
				if (alignZero(height - d2) > 0 && alignZero(pOne.point.distance(rayP)) < dis)
					flag2 = true;
			}

		if (flag1 && flag2)
			return List.of(pOne, pTwo);

		// get upper plane intersections
		Point upperPoint = rayP.add(rayV.scale(height));
		Plane upperPlane = new Plane(upperPoint, rayV);
		// if point is in the range
		List<GeoPoint> listUpper = upperPlane.findGeoIntersections(ray);
		if (listUpper != null)
			if (alignZero(radius - upperPoint.distance(listUpper.get(0).point)) > 0 && //
					alignZero(listUpper.get(0).point.distance(rayP)) < dis)
				flagUp = true;

		// get under plane intersections
		Plane underPlane = new Plane(rayP, rayV);
		List<GeoPoint> listUnder = underPlane.findGeoIntersections(ray);
		if (listUnder != null)

			if (alignZero(radius - rayP.distance(listUnder.get(0).point)) > 0 && //
					alignZero(listUnder.get(0).point.distance(rayP)) < dis)
				flagUnder = true;

		if (flag1)
			if (flagUp)
				return List.of(pOne, listUpper.get(0));
			else if (flagUnder)
				return List.of(pOne, listUnder.get(0));
			else {
				return List.of(pOne);
			}

		if (flag2)
			if (flagUp)
				return List.of(pTwo, listUpper.get(0));
			else if (flagUnder)
				return List.of(pTwo, listUnder.get(0));
			else {
				return List.of(pTwo);
			}

		if (flagUp)
			if (flagUnder)
				return List.of(listUnder.get(0), listUpper.get(0));
			else
				return List.of(listUpper.get(0));

		if (flagUnder)
			return List.of(listUnder.get(0));

		return null;

	}

	@Override
	public String toString() {
		return super.toString() + ", " + height;
	}

}
