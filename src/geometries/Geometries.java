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
		double x = box.maxX - box.minX;
		double y = box.maxY - box.minY;
		double z = box.maxZ - box.minZ;
		// which axis we are reffering to
		setBVH(y > x && y > z ? 1 : z > x && z > y ? 2 : 0, 3);
	}

	/**
	 * create the hierarchy and put into the right boxes
	 */
	private void setBVH(int axis, int count) {
		if (!cbr || count == 0)
			return;
		// min amount of geometries in a box
		if (geometries.size() > 4) {
			if (box == null) {
				var finites = new Geometries(geometries);
				geometries.clear();
				this.add(finites.geometries);
				return;
			}

			var l = new Geometries();
			var m = new Geometries();
			var r = new Geometries();
			double midX = (box.maxX + box.minX) / 2;
			double midY = (box.maxY + box.minY) / 2;
			double midZ = (box.maxZ + box.minZ) / 2;
			switch (axis) {
			case 0:
				for (var g : geometries) {
					if (g.box.minX > midX)
						r.add(g);
					else if (g.box.maxX < midX)
						l.add(g);
					else
						m.add(g);
				}
				break;
			case 1:
				for (var g : geometries) {
					if (g.box.minY > midY)
						r.add(g);
					else if (g.box.maxY < midY)
						l.add(g);
					else
						m.add(g);
				}
				break;
			case 2:
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

			int nextAxis = (axis + 1) % 3;
			int lsize = l.geometries.size();
			int msize = m.geometries.size();
			int rsize = r.geometries.size();
			geometries.clear();
			if (lsize <= 2 || msize + rsize == 0) {
				this.add(l.geometries);
				if (msize + rsize == 0)
					this.setBVH(nextAxis, count - 1);
			} else {
				geometries.add(l);
			}

			if (msize <= 2 || lsize + rsize == 0) {
				this.add(m.geometries);
				if (lsize + rsize == 0)
					this.setBVH(nextAxis, count - 1);
			} else {
				geometries.add(m);
			}

			if (rsize <= 2 || lsize + msize == 0) {
				this.add(r.geometries);
				if (lsize + msize == 0)
					this.setBVH(nextAxis, count - 1);
			} else {
				geometries.add(r);
			}
		}

		for (var geo : this.geometries)
			if (geo instanceof Geometries geos)
				geos.setBVH();
		return;
	}

}
