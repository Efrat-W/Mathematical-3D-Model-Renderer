package geometries;

import primitives.*;

import static primitives.Util.alignZero;

import java.util.List;

/**
 * abstract class Intersectable is an abstract class that represents any
 * Intersectable object.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public abstract class Intersectable {

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
		protected final double minX;
		protected final double minY;
		protected final double minZ;

		/**
		 * this values represent the maximum point of the geometry
		 */
		protected final double maxX;
		protected final double maxY;
		protected final double maxZ;

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

		/**
		 * getter for minX
		 * 
		 * @return minX value
		 */
		public double getMinX() {
			return minX;
		}

		/**
		 * getter for minY
		 * 
		 * @return minY value
		 */
		public double getMinY() {
			return minY;
		}

		/**
		 * getter for minZ
		 * 
		 * @return minZ value
		 */
		public double getMinZ() {
			return minZ;
		}

		/**
		 * getter for maxX
		 * 
		 * @return maxX value
		 */
		public double getMaxX() {
			return maxX;
		}

		/**
		 * getter for maxY
		 * 
		 * @return maxY value
		 */
		public double getMaxY() {
			return maxY;
		}

		/**
		 * getter for maxZ
		 * 
		 * @return maxZ value
		 */
		public double getMaxZ() {
			return maxZ;
		}

	}

	/***
	 * the function finds The bounds of the volume of each borderable geometry-
	 * defines a set of lines parallel to each axis of the coordinate
	 */
	protected abstract void findMinMax();

	/**
	 * getter for the bvh box
	 * 
	 * @return the box
	 */
	public Border getBox() {
		return box;
	}

	/**
	 * setter for the bvh box
	 * 
	 * @param box the box we set
	 * @return this intersectable itself
	 */
	public Intersectable setBox(Border box) {
		this.box = box;
		return this;

	}

	/**
	 * this function calculate if the ray trace the border of the geometry
	 * 
	 * @param ray the crosses ray
	 * @return true for intersection, false for not intersection
	 */
	protected boolean intersectBorder(Ray ray) {
		Point origin = ray.getPoint();
		double originX = origin.getX();
		double originY = origin.getY();
		double originZ = origin.getZ();
		Vector dir = ray.getDir();
		double dirX = dir.getX();
		double dirY = dir.getY();
		double dirZ = dir.getZ();
		double minx = box.minX;
		double maxx = box.maxX;
		double miny = box.minY;
		double maxy = box.maxY;
		double minz = box.minZ;
		double maxz = box.maxZ;

		// Initially will receive the values of tMinX and tMaxX
		double tMin;
		double tMax;

		if (alignZero(dirX) >= 0) // the values are depend on the direction of the ray
		{
			tMin = (minx - originX) / dirX; // b=D*t+O => y=mx+b =>dirx*tmin+originx=minx
			tMax = (maxx - originX) / dirX;
		} else {
			tMin = (maxx - originX) / dirX;
			tMax = (minx - originX) / dirX;
		}

		double tMinY;
		double tMaxY;
		if (alignZero(dirY) >= 0) {
			tMinY = (miny - originY) / dirY;
			tMaxY = (maxy - originY) / dirY;
		} else {
			tMinY = (maxy - originY) / dirY;
			tMaxY = (miny - originY) / dirY;
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

		if (alignZero(dirZ) >= 0) {
			tMinZ = (minz - originZ) / dirZ;
			tMaxZ = (maxz - originZ) / dirZ;
		} else {
			tMinZ = (maxz - originZ) / dirZ;
			tMaxZ = (minz - originZ) / dirZ;
		}

		// If either the max value of Z is smaller than overall min value, or min value
		// of Z is bigger than the overall
		// max, we can already return false. Otherwise we can return true since no other
		// coordinate checks are needed.
		if ((tMin > tMaxZ) || (tMinZ > tMax))
			return false;
		
		if (tMinZ > tMin)
			tMin = tMinZ;
		
		if (tMaxZ < tMax)
			tMax = tMaxZ;
		return true;
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
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		if (!intersectBorder(ray))
			return null;
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
}
