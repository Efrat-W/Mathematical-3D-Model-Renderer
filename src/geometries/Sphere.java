package geometries;

/*
* Class Sphere is the basic class representing a sphere
*  in Cartesian 3-Dimensional coordinate system.
* @author Efrat Wexler and Sari Zilberlicht
*/

import primitives.Point;
import primitives.Vector;

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

}
