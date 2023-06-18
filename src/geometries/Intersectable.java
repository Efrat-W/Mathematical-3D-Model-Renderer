package geometries;

import primitives.*;

import java.util.List;

/**
 * abstract class Intersectable is an abstract class that represents any
 * Intersectable object.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public abstract class Intersectable {
	/**
	 * finds all intersection points with a given ray and the intersectable
	 * 
	 * @param ray the ray that intersect
	 * @return list of points that intersect
	 */
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
		/**
		 * intersected geometry
		 */
		public Geometry geometry;
		/**
		 * intersection point
		 */
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
		return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * find all intersections with the ray (points and geometries) with limited
	 * distance
	 * 
	 * @param ray ray that intersect
	 * @param dis the maximum distance
	 * @return list of geopoints
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray, double dis) {
		return findGeoIntersectionsHelper(ray, dis);
	}

	/**
	 * auxiliary function find all intersections with the ray (points and
	 * geometries)
	 * 
	 * @param ray    ray that intersect
	 * @param maxDis the maximum distance
	 * @return list of geopoints
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis);

	/**
	 * finds all the intersection points with geometry
	 * @param ray
	 * @return list of Geopoints that the ray intersects
	 */
	protected abstract  List<GeoPoint> findGeoIntersectionsSpecific(Ray ray, double dis);
}
}
