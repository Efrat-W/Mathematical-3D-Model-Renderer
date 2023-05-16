package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * Class Geometries is a class representing a collection of geometries Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Geometries implements Intersectable {
	private final List<Intersectable> geometries = new LinkedList<Intersectable>();

	public Geometries() {
	}

	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	public void add(Intersectable... geometries) {
		this.geometries.addAll(List.of(geometries));
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> toReturn = null;
		for (Intersectable g : this.geometries) {
			var lPoints = g.findIntersections(ray);
			if (lPoints != null) {
				if (toReturn == null)
					toReturn = new LinkedList<>();
				toReturn.addAll(lPoints);
			}
		}
		return toReturn;
	}

}
