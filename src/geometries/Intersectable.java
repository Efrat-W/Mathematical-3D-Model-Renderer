package geometries;

import primitives.*;

import static primitives.Util.isZero;

import java.util.List;

/**
 * abstract class Intersectable is an abstract class that represents any
 * Intersectable object.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public abstract class Intersectable {
	/* list of intersections with the intersectable */
	public List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * class GeoPoint is a class that represents an intersection of a geometry and
	 * the point
	 * 
	 * @author Efrat Wexler and Sari Zilberlicht
	 *
	 */
	public static class GeoPoint {
		/* intersected geometry */
		public Geometry geometry;
		/* intersection point */
		public Point point;

		/**
		 * constructor of a geoPoint by given geometry and a point
		 * 
		 * @param geometry geometry
		 * @param point    point
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return obj instanceof GeoPoint other && //
					this.geometry == other.geometry && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "Point: " + point + ", Geometry: " + geometry;
		}

	}

	/**
	 * find all intersections with the ray (points and geometries)
	 * 
	 * @param ray ray that intersect
	 * @return list of geopoints
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * auxiliary function find all intersections with the ray (points and
	 * geometries)
	 * 
	 * @param ray ray that intersect
	 * @return list of geopoints
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
