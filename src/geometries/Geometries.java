package geometries;

import java.util.LinkedList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

import primitives.Point;
import primitives.Ray;

/**
 * Class Geometries is a class representing a collection of geometries Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Geometries extends Intersectable {
	private final List<Intersectable> geometries;

	public Geometries() {
		geometries = new LinkedList<Intersectable>();
	}

	public Geometries(Intersectable... geometries) {
		this.geometries = new LinkedList<Intersectable>();
		for (Intersectable g : geometries)
			this.geometries.add(g);
	}

	public void add(Intersectable... geometries) {
		for (Intersectable g : geometries)
			this.geometries.add(g);
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		LinkedList<GeoPoint> toReturn = null;
		for (Intersectable g : this.geometries) {
			var lPoints = g.findGeoIntersections(ray);
			if (lPoints != null) {
				if (toReturn == null)
					toReturn = new LinkedList<>();
				toReturn.addAll(lPoints);
			}
		}
		return toReturn;
	}

}
