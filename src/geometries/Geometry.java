package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is an interface that represents any geometry in general.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public interface Geometry {

	/**
	 * get normal vector, perpendicular at the point
	 * 
	 * @param p point on the geometric surface
	 * @return normal vector
	 */
	public Vector getNormal(Point p);
}
