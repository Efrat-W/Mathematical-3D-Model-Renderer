package geometries;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;

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
    public List<Point> findIntersections(Ray ray) {
	Point d, e;
	double dis, ab, bc, ac;

	// Given ray (A + ta) and this Tube ray (B + tb)
	Point pointA = ray.getPoint(), pointB = axisRay.getPoint();
	Vector vectorA = ray.getDir(), vectorB = axisRay.getDir();

	ab = vectorA.dotProduct(vectorB);

	// if is parallel to tube
	try {
	    vectorA.crossProduct(vectorB);
	} catch (IllegalArgumentException ex) {
	    return null;
	}

	double bb = 1; // it is a unit vector therefore it's squared size is 1
	double aa = 1;
	try {
	    // Vector AB
	    Vector c = pointB.subtract(pointA);
	    // dot-product calc
	    bc = vectorB.dotProduct(c);
	    ac = vectorA.dotProduct(c);

	    // The closest point on (A + t1a)
	    double t1 = (-ab * bc + ac * bb) / (aa * bb - ab * ab);
	    try {
		d = pointA.add(vectorA.scale(t1));
	    } catch (IllegalArgumentException ex) {
		d = pointA;
	    }

	    // The closest point on (B + t2b)
	    double t2 = (ab * ac - bc * aa) / (/* aa * bb */ 1 - ab * ab);

	    try {
		e = pointB.add(vectorB.scale(t2));
	    } catch (IllegalArgumentException ex) {
		e = pointB;
	    }

	    // distance between two rays
	    dis = d.distance(e);

	} catch (IllegalArgumentException ex) {
	    // If A and B are the same
	    d = ray.getPoint();
	    dis = 0;
	}

	double diff = alignZero(dis - radius);
	// The ray doesn't touch the Tube
	if (diff > 0.0)
	    return null;

	// The ray is tangent to the Tube
	if (diff == 0.0) {
	    return null;

	}

	/*
	 * We know that the ray goes through the tube. Lets cut the tube parallel to the
	 * ray. We will get a ellipse where the height is radius. We need to calculate
	 * the width
	 */
	double width;
	// Whether the ray is orthogonal to the tube?
	try {
	    // sin's between (B + tb) and (A + ta) is |VxU|
	    double sinA = vectorA.crossProduct(vectorB).length();
	    // ellipse width
	    width = radius / sinA;
	} catch (IllegalArgumentException ex) { // it is orthogonal
	    width = radius;
	}
	// ellipse equation x^2/k^2 + y^2 = radius^2
	// if the width is w then k is w/r
	double k = width / radius;
	// y is d for our ray x^2/k^2 + k^2 = radius^2 => x^2/k^2 = radius^2 -d^2 =>
	// x^2 = (radius^2 -d^2)*k^2 => x = sqrt(radius^2 -d^2)*k
	double th = Math.sqrt(radius * radius - dis * dis) * k;

	// the two points
	Point p1 = d.subtract(vectorA.scale(th));
	Point p2 = d.add(vectorA.scale(th));

	// Check if the points are in range and return them

	try {
	    // the ray starts before point 1
	    if (!(p1.subtract(pointA).dotProduct(vectorA) < 0.0) && !(p2.subtract(pointA).dotProduct(vectorA) < 0.0)) {
		return List.of(p1, p2);
	    }
	} catch (IllegalArgumentException ex) {
	    // the ray starts at point1

	}

	try {
	    // the ray starts before point 1
	    if (!(p1.subtract(pointA).dotProduct(vectorA) < 0.0)) { return List.of(p1); }
	} catch (IllegalArgumentException ex) {
	    // the ray starts at point1

	}

	try {
	    // the ray starts before point 2
	    if (!(p2.subtract(pointA).dotProduct(vectorA) < 0.0)) { return List.of(p2); }
	} catch (IllegalArgumentException ex) {
	    // the ray starts at point2

	}

	return null;

    }

    @Override
    public String toString() {
	return "" + axisRay + ", " + radius;
    }

}
