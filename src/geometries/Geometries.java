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
	findMinMax();
    }

    /**
     * adds geometries to the list
     * 
     * @param geometries the geomtries to add
     */
    public void add(Intersectable... geometries) {
	this.geometries.addAll(List.of(geometries));
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

	    g.findMinMax();
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
	this.box=new Border(minX, minY, minZ, maxX, maxY, maxZ);
    }

}
