package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is an interface that represents any geometry in general.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public abstract class Geometry extends BoundingBox {
	/**
	 * emission light of the shapes
	 */
	protected Color emission = Color.BLACK;

	private Material material = new Material();

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
	 * @return this geometry
	 */
	public Geometry setEmission(Color em) {
		emission = em;
		return this;
	}

	/**
	 * getter for geomtry material
	 * 
	 * @return material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * a setter for geomtry material
	 * 
	 * @param mat material of the geometry
	 * @return this geometry
	 */
	public Geometry setMaterial(Material mat) {
		material = mat;
		return this;
	}

}
