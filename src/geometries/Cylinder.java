package geometries;

import primitives.Point;

/*
* Class Cylinder is the basic class representing a cylinder
* in Cartesian 3-Dimensional coordinate system.
* @author Efrat Wexler and Sari Zilberlicht
*/

import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {

    private final double height;

    /**
     * Cylinder constructor based on radius, axis ray and height.
     * 
     * @param h   height
     * @param ar  axis ray
     * @param rad radius
     */
    public Cylinder(double h, Ray ar, double rad) {
	super(ar, rad);
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
    	return null;
    }

    @Override
    public String toString() {
	return super.toString() + ", " + height;
    }

}
