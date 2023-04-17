package geometries;

import primitives.Point;

/*
* Class Cylinder is the basic class representing a cylinder
* in Cartesian 3-Dimensional coordinate system.
* @author Efrat Wexler and Sari Zilberlicht
*/

import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static primitives.Util.isZero;

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
	if (p == p0) { return v; }
	double t = v.dotProduct(p.subtract(p0));
	if (isZero(t) || isZero(t - height)) // on bases
	{ return v; }
	Point o = p0.add(v.scale(t));
	return p.subtract(o).normalize();
    }

    @Override
    public String toString() {
	return super.toString() + ", " + height;
    }

}
