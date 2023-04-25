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
	public List<Point> findIntersections(Ray ray) {
		if (geometries.isEmpty())
			return null;
		boolean flag = false;
		for (Intersectable g : this.geometries)
			if (g.findIntersections(ray) != null) {
				flag = true;
				break;
			}
		if (!flag)
			return null;
		LinkedList<Point> toReturn = new LinkedList<Point>();
		List<Point> lPoints = null;
		for (Intersectable g : this.geometries) {
			lPoints = g.findIntersections(ray);
			if (lPoints != null) {
				for (Point p : lPoints)
					toReturn.add(p);
			}
		}
		return toReturn;
	}

}
