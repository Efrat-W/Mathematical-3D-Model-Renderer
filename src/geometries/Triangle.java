package geometries;

import java.util.List;
import static primitives.Util.isZero;
import primitives.*;

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
	public List<Point> findIntersections(Ray ray) {
		if(ray.getPoint().equals(this.vertices.get(0)) || ray.getPoint().equals(this.vertices.get(1)) || ray.getPoint().equals(this.vertices.get(2)))
            return null;
        Vector v1 = this.vertices.get(0).subtract(ray.getPoint());
        Vector v2 = this.vertices.get(1).subtract(ray.getPoint());
        Vector v3 = this.vertices.get(2).subtract(ray.getPoint());
        Vector n1, n2, n3;
        try {
        	 n1 = v1.crossProduct(v2).normalize();
             n2 = v2.crossProduct(v3).normalize();
             n3 = v3.crossProduct(v1).normalize();
        }
		catch (IllegalArgumentException e) {
			return null;
		}
        double vn1 = ray.getDir().dotProduct(n1);
        double vn2 = ray.getDir().dotProduct(n2);
        double vn3 = ray.getDir().dotProduct(n3);
        if(isZero(vn1) || isZero(vn2) || isZero(vn3))
            return null;
        
        if((vn1 > 0 && vn2 > 0 && vn3 > 0) || (vn1 < 0 && vn2 < 0 && vn3 < 0)) {
            return this.plane.findIntersections(ray);
        }
        return null;
    }
}
