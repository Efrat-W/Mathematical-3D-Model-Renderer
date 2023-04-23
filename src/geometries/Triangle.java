package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Class Triangle is the basic class representing a triangle in Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class Triangle extends Polygon {

	/**
	 * Plane constructor based on 3 vertices of the triangle.
	 * 
	 * @param p1 vertex of the triangle
	 * @param p2 vertex of the triangle
	 * @param p3 vertex of the triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<Point> findIntsersections(Ray ray) {
		return null;
	}

}
