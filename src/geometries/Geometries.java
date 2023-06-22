package geometries;

//import java.util.Collections;
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
	private final List<Intersectable> infinites = new LinkedList<>();

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
	 * constructor that gets several intersectables and add them to the geometries
	 * list
	 * 
	 * @param geometries geometries to add to list
	 */
	public Geometries(List<Intersectable> geometries) {
		add(geometries);
	}

	/**
	 * adds geometries to the list
	 * 
	 * @param geometries the geomtries to add
	 */
	public void add(Intersectable... geometries) {
		add(List.of(geometries));
	}

	/**
	 * adds geometries to the list
	 * 
	 * @param geometries the geomtries to add
	 */
	public void add(List<Intersectable> geometries) {
		if (!cbr) {
			this.geometries.addAll(geometries);
			return;
		}

		for (var g : geometries) {
			if (g.box == null)
				infinites.add(g);
			else {
				this.geometries.add(g);
				if (infinites.isEmpty()) {
					if (box == null)
						box = new Border();
					if (g.box.minX < box.minX)
						box.minX = g.box.minX;
					if (g.box.minY < box.minY)
						box.minY = g.box.minY;
					if (g.box.minZ < box.minZ)
						box.minZ = g.box.minZ;
					if (g.box.maxX > box.maxX)
						box.maxX = g.box.maxX;
					if (g.box.maxY > box.maxY)
						box.maxY = g.box.maxY;
					if (g.box.maxZ > box.maxZ)
						box.maxZ = g.box.maxZ;
				}
			}
		}
		// if there are inifinite objects
		if (!infinites.isEmpty())
			box = null;
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double dis) {
		LinkedList<GeoPoint> toReturn = null;
		for (Intersectable g : this.geometries) {
			var lPoints = g.findGeoIntersections(ray, dis);
			if (lPoints != null)
				if (toReturn == null)
					toReturn = new LinkedList<>(lPoints);
				else
					toReturn.addAll(lPoints);
		}
		for (Intersectable g : this.infinites) {
			var lPoints = g.findGeoIntersections(ray, dis);
			if (lPoints != null)
				if (toReturn == null)
					toReturn = new LinkedList<>(lPoints);
				else
					toReturn.addAll(lPoints);
		}
		return toReturn;
	}

	/**
	 * create the hierarchy and put into the right boxes
	 */
	public void setBVH() {
		if (!cbr)
			return;
		// min amount of geometries in a box is 2
		if (geometries.size() <= 4)
			return;

		if (box == null) {
			var finites = new Geometries(geometries);
			geometries.clear();
			geometries.add(finites);
			return;
		}

		double x = box.maxX - box.minX;
		double y = box.maxY - box.minY;
		double z = box.maxZ - box.minZ;
		// which axis we are reffering to
		final char axis = y > x && y > z ? 'y' : z > x && z > y ? 'z' : 'x';
//		Collections.sort(geometries, //
//				(i1, i2) -> Double.compare(average(i1, axis), average(i2, axis)));

		var l = new Geometries();
		var m = new Geometries();
		var r = new Geometries();
		double midX = (box.maxX + box.minX) / 2;
		double midY = (box.maxY + box.minY) / 2;
		double midZ = (box.maxZ + box.minZ) / 2;
		switch (axis) {
		case 'x':
			for (var g : geometries) {
				if (g.box.minX > midX)
					r.add(g);
				else if (g.box.maxX < midX)
					l.add(g);
				else
					m.add(g);
			}
			break;
		case 'y':
			for (var g : geometries) {
				if (g.box.minY > midY)
					r.add(g);
				else if (g.box.maxY < midY)
					l.add(g);
				else
					m.add(g);
			}
			break;
		case 'z':
			for (var g : geometries) {
				if (g.box.minZ > midZ)
					r.add(g);
				else if (g.box.maxZ < midZ)
					l.add(g);
				else
					m.add(g);
			}
			break;
		}

		// add geometries to the splitted boxes
//		int counter = 0;
//		int middle = geometries.size() / 2;
//		for (var g : geometries)
//			if (counter++ <= middle)
//				l.add(g);
//			else
//				r.add(g);

		geometries.clear();
		if (l.geometries.size() <= 2)
			geometries.addAll(l.geometries);
		else {
			l.setBVH();
			geometries.add(l);
		}

		if (m.geometries.size() <= 2)
			geometries.addAll(m.geometries);
		else
			geometries.add(m);
		
		if (r.geometries.size() <= 2)
			geometries.addAll(r.geometries);
		else {
			r.setBVH();
			geometries.add(r);
		}
	}

	/**
	 * find the average of the box borders relative to the axis
	 * 
	 * @param g  the geometry/ies (intersectable)
	 * @param ch the longest axis
	 * @return the average of the box borders
	 */
//	private double average(Intersectable g, char ch) {
//		switch (ch) {
//		case 'x':
//			return (g.box.maxX + g.box.minX) / 2;
//		case 'y':
//			return (g.box.maxY + g.box.minY) / 2;
//		case 'z':
//			return (g.box.maxZ + g.box.minZ) / 2;
//		default:
//			throw new IllegalArgumentException("wrong axis name");
//		}
//	}

}
