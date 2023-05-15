package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
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
	if (isZero(t) || isZero(t - height)) // on bases
	    return v;
	Point o = p0.add(v.scale(t));
	return p.subtract(o).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
	Point rayP = axisRay.getPoint();
	Vector rayV = axisRay.getDir();
	Point p1, p2, p, pOne = null, pTwo = null;
	boolean flag1 = false, flag2 = false, flagUp = false, flagUnder = false;
	// get tube intersections
	List<Point> list = super.findIntersections(ray);
	if (list != null)
	    if (list.size() == 1) {
		p = list.get(0);
		double d = Math.abs(rayV.dotProduct(p.subtract(rayP)));
		// if point is in the range
		if (alignZero(height - d) > 0)
		    flag1 = true;
	    } else if (list.size() == 2) {
		pOne = list.get(0);
		pTwo = list.get(1);
		double d1 = Math.abs(rayV.dotProduct(pOne.subtract(rayP)));
		double d2 = Math.abs(rayV.dotProduct(pTwo.subtract(rayP)));
		// if point is in the range
		if (alignZero(height - d1) > 0)
		    flag1 = true;
		// if point is in the range
		if (alignZero(height - d2) > 0)
		    flag2 = true;
	    }

	if (flag1 && flag2)
	    return List.of(pOne, pTwo);

	// get upper plane intersections
	Point upperPoint = rayP.add(rayV.scale(height));
	Plane upperPlane = new Plane(upperPoint, rayV);
	// if point is in the range
	List<Point> listUpper = upperPlane.findIntersections(ray);
	if (listUpper != null)
	    if (alignZero(radius - upperPoint.distance(listUpper.get(0))) > 0)
		flagUp = true;

	// get under plane intersections
	Plane underPlane = new Plane(rayP, rayV);
	List<Point> listUnder = underPlane.findIntersections(ray);
	if (listUnder != null)

	    if (alignZero(radius - rayP.distance(listUnder.get(0))) > 0)
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
