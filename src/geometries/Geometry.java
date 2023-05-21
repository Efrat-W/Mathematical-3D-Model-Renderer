package geometries;

import primitives.Color;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is an interface that represents any geometry in general.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public abstract class Geometry extends Intersectable {
	/**
	 * emission light of the shapes
	 */
	protected Color emission = Color.BLACK;

	/**
	 * get normal vector, perpendicular at the point
	 * 
	 * @param p point on the geometric surface
	 * @return normal vector
	 */
	public abstract Vector getNormal(Point p);

	/**
	 * getter for geometries emission light
	 * 
	 * @return emission
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * a setter for geometries emission light
	 * 
	 * @param em color for the setting
	 * @return this
	 */
	public Geometry setEmission(Color em) {
		emission = em;
		return this;
	}
}
