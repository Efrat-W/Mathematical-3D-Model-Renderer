package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Ray;

/**
 * Class Geometries is a class representing a collection of geometries Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Geometries extends BoundingBox {
	private final List<BoundingBox> geometries = new LinkedList<>();

	/**
	 * a default constructor
	 */
	public Geometries() {
	}

	/**
	 * constructor that gets several intersectables and add them to the geometries
	 * list
	 * 
	 * @param geometries geometries to add to list
	 */
	public Geometries(BoundingBox... geometries) {
		add(geometries);
	}

	/**
	 * adds geometries to the list
	 * 
	 * @param geometries the geomtries to add
	 */
	public void add(BoundingBox... geometries) {
		this.geometries.addAll(List.of(geometries));
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsSpecific(Ray ray, double dis) {
		LinkedList<GeoPoint> toReturn = null;
		for (Intersectable g : this.geometries) {
			var lPoints = g.findGeoIntersections(ray, dis);
			if (lPoints != null) {
				if (toReturn == null)
					toReturn = new LinkedList<>();
				toReturn.addAll(lPoints);
			}
		}
		return toReturn;
	}

	@Override
	protected void findMinMax() {
		minX = Double.POSITIVE_INFINITY;
		maxX = Double.NEGATIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		minZ = Double.POSITIVE_INFINITY;
		maxZ = Double.NEGATIVE_INFINITY;
		/**
		 * find the minimum and the maximum of the geometry border
		 */
		for (BoundingBox g : geometries) {
			g.findMinMax();
			if (g.minX < minX)
				minX = g.minX;
			if (g.minY < minY)
				minY = g.minY;
			if (g.minZ < minZ)
				minZ = g.minZ;
			if (g.maxX > maxX)
				maxX = g.maxX;
			if (g.maxY > maxY)
				maxY = g.maxY;
			if (g.maxZ > maxZ)
				maxZ = g.maxZ;
		}
	}

}
