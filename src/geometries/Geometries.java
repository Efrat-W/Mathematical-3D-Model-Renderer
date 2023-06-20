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

public class Geometries extends Intersectable {
	private final List<Intersectable> geometries = new LinkedList<>();

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
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * adds geometries to the list
	 * 
	 * @param geometries the geomtries to add
	 */
	public void add(Intersectable... geometries) {
		this.geometries.addAll(List.of(geometries));
		findMinMax();
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double dis) {
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
		double minX = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double minZ = Double.POSITIVE_INFINITY;
		double maxZ = Double.NEGATIVE_INFINITY;

		/**
		 * find the minimum and the maximum of the geometry border
		 */
		for (Intersectable g : geometries) {

			double gminX = g.getBox().getMinX();
			double gmaxX = g.getBox().getMaxX();
			double gminY = g.getBox().getMinY();
			double gmaxY = g.getBox().getMaxY();
			double gminZ = g.getBox().getMinZ();
			double gmaxZ = g.getBox().getMaxZ();

			if (gminX < minX)
				minX = gminX;
			if (gminY < minY)
				minY = gminY;
			if (gminZ < minZ)
				minZ = gminZ;
			if (gmaxX > maxX)
				maxX = gmaxX;
			if (gmaxY > maxY)
				maxY = gmaxY;
			if (gmaxZ > maxZ)
				maxZ = gmaxZ;
		}
		this.box = new Border(minX, minY, minZ, maxX, maxY, maxZ);
	}

	/**
	 * create the hierarchy and put into the right boxes
	 */
	public void BVH() {
		// min amount of geometries in a box is 2
		if (geometries.size() <= 2) {
			return;
		}
		Geometries l = new Geometries();
		Geometries r = new Geometries();

		//which axis we are reffering to
		char axis = 'x';
		
		double x = box.getMaxX() - box.getMinX();
		double y = box.getMaxY() - box.getMinY();
		double z = box.getMaxZ() - box.getMinZ();

		if (y > x && y > z) {
			axis = 'y';
		}
		if (z > x && z > y) {
			axis = 'z';
		}

		Sort(geometries, axis);

		// add geometries to the splitted boxes
		for (int i = 0; i < geometries.size() / 2; i++) {
			l.add(geometries.get(i));
		}
		for (int i = geometries.size() / 2; i < geometries.size(); i++) {
			r.add(geometries.get(i));
		}
		l.BVH();
		r.BVH();

		geometries.clear();
		geometries.add(l);
		geometries.add(r);
	}

	/**
	 * bubble sort for the geometries
	 * 
	 * @param lst the list of the geometries
	 * @param ch the axis that we are sorting the geometries by
	 */
	private void Sort(List<Intersectable> lst, char ch) {
		int n = lst.size();
		
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++) {
				
				Intersectable iJ = lst.get(j);
				Intersectable iJ1 = lst.get(j+1);

				if (average(iJ, ch) > average(iJ1, ch)) {
					// swap arr[j+1] and arr[j]
					Intersectable b = iJ;
					lst.set(j, iJ1);
					lst.set(j + 1, b);
				}
			}
	}

	/**
	 * find the average of the box borders relative to the axis 
	 * 
	 * @param g  the geometry/ies (intersectable)
	 * @param ch the longest axis
	 * @return the average of the box borders
	 */
	private double average(Intersectable g, char ch) {
		Border b = g.getBox();

		if (ch == 'x')
			return (b.getMaxX() + b.getMinX()) / 2;

		else if (ch == 'y')
			return (b.getMaxY() + b.getMinY()) / 2;

		return (b.getMaxZ() + b.getMinZ()) / 2;
	}

}
