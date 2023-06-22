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
	protected static boolean cbr = false;
	
	/**
	 * the box for the bvh
	 */
	protected Border box = null;

	/**
	 * class Border is a class that represents the box of the bvh
	 * 
	 * @author Efrat Wexler and Sari Zilberlicht
	 *
	 */
	public static class Border {
		/**
		 * this values represent the minimum point of the geometry
		 */
		protected double minX;
		protected double minY;
		protected double minZ;

		/**
		 * this values represent the maximum point of the geometry
		 */
		protected double maxX;
		protected double maxY;
		protected double maxZ;

		/**
		 * constructor of a border by values
		 * 
		 * @param x1 minimum x
		 * @param y1 minimum y
		 * @param z1 minimum z
		 * @param x2 maximum x
		 * @param y2 maximum y
		 * @param z2 maximum z
		 */
		public Border(double x1, double y1, double z1, double x2, double y2, double z2) {
			minX = x1;
			minY = y1;
			minZ = z1;
			maxX = x2;
			maxY = y2;
			maxZ = z2;
		}

		public Border() {
			minX = Double.POSITIVE_INFINITY;
			maxX = Double.NEGATIVE_INFINITY;
			minY = Double.POSITIVE_INFINITY;
			maxY = Double.NEGATIVE_INFINITY;
			minZ = Double.POSITIVE_INFINITY;
			maxZ = Double.NEGATIVE_INFINITY;
		}
		
		/**
		 * this function calculate if the ray trace the border of the geometry
		 * 
		 * @param ray the crosses ray
		 * @return true for intersection, false for not intersection
		 */
		protected boolean intersect(Ray ray) {
			Point origin = ray.getPoint();
			double originX = origin.getX();
			double originY = origin.getY();
			double originZ = origin.getZ();
			Vector dir = ray.getDir();
			double dirX = dir.getX();
			double dirY = dir.getY();
			double dirZ = dir.getZ();

			// Initially will receive the values of tMinX and tMaxX
			double tMin;
			double tMax;

			// the values are depend on the direction of the ray
			if (dirX >= 0) {
				tMin = (minX - originX) / dirX; // b=D*t+O => y=mx+b =>dirx*tmin+originx=minx
				tMax = (maxX - originX) / dirX;
			} else {
				tMin = (maxX - originX) / dirX;
				tMax = (minX - originX) / dirX;
			}

			double tMinY;
			double tMaxY;
			if (dirY >= 0) {
				tMinY = (minY - originY) / dirY;
				tMaxY = (maxY - originY) / dirY;
			} else {
				tMinY = (maxY - originY) / dirY;
				tMaxY = (minY - originY) / dirY;
			}

			// If either the max value of Y is smaller than overall min value, or min value
			// of Y is bigger than the overall
			// max, we can already return false.
			// Otherwise we'll update the overall min and max values and perform the same
			// check on the Z values.
			if ((tMin > tMaxY) || (tMinY > tMax))
				return false;

			if (tMinY > tMin)
				tMin = tMinY;
			if (tMaxY < tMax)
				tMax = tMaxY;

			double tMinZ;
			double tMaxZ;
			if (dirZ >= 0) {
				tMinZ = (minZ - originZ) / dirZ;
				tMaxZ = (maxZ - originZ) / dirZ;
			} else {
				tMinZ = (maxZ - originZ) / dirZ;
				tMaxZ = (minZ - originZ) / dirZ;
			}

			// If either the max value of Z is smaller than overall min value, or min value
			// of Z is bigger than the overall
			// max, we can already return false. Otherwise we can return true since no other
			// coordinate checks are needed.
			return tMin <= tMaxZ && tMinZ <= tMax;
		}

	}

	public static void setCbr() {
		Intersectable.cbr = true;
	}


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
	int c = 0;

	public List<GeoPoint> findGeoIntersections(Ray ray) {
		return box != null && !box.intersect(ray) ? null : findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
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
}
