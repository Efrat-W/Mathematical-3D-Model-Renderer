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
    // flag for using the optimization of bvh or not
    static protected boolean isBVH = false;

    /**
     * setter for the functionality of borderable
     */
    public static void setBVH() {
	isBVH = true;
    }

    /**
     * a boolean flag for each geometry if it already has borders or not
     */
    protected boolean isInBox = false;

    /**
     * this values represent the minimum point of the geometry
     */
    public double minX;
    public double minY;
    public double minZ;

    /**
     * this values represent the maximum point of the geometry
     */
    public double maxX;
    public double maxY;
    public double maxZ;

    /**
     * find the minimum and the maximum of the geometry border
     */
    public final void findMinMaxHelper() {

	findMinMax();
	isInBox = true;

    }

    /***
     * the function finds The bounds of the volume of each borderable geometry-
     * defines a set of lines parallel to each axis of the coordinate
     */
    protected abstract void findMinMax();

    /**
     * the function checks if a ray intersects the bounding volume
     * 
     * @param ray
     * @return true if the ray intersects the bounding volume else it returns false
     */
    protected boolean intersectBorderHelper(Ray ray) {
	if (!isInBox)
	    findMinMaxHelper();
	return intersectBorder(ray);
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

	// Initially will receive the values of tMinX and tMaxX
	double tMin;
	double tMax;

	if (alignZero(dirX) >= 0) // the values are depend on the direction of the ray
	{
	    tMin = (minX - originX) / dirX; // b=D*t+O => y=mx+b =>dirx*tmin+originx=minx
	    tMax = (maxX - originX) / dirX;
	} else {
	    tMin = (maxX - originX) / dirX;
	    tMax = (minX - originX) / dirX;
	}

	double tMinY;
	double tMaxY;
	if (alignZero(dirY) >= 0) {
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

	if (alignZero(dirZ) >= 0) {
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
	if (isBVH)
	    if (!intersectBorderHelper(ray))
		return null;
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
