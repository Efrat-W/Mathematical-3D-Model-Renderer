package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    final Point q0;
    final Vector normal;

    /**
     * Plane constructor based on 3 points on the plane.
     * 
     * @param p1 point on the plane
     * @param p2 point on the plane
     * @param p3 point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
	q0 = p1;
	normal = null;
    }

    /**
     * Plane constructor based on a point and a normal vector perpendicular to the
     * plane.
     * 
     * @param p   point on the plane
     * @param vec normal vector to the plane
     */
    public Plane(Point p, Vector vec) {
	q0 = p;
	normal = vec.normalize();
    }

    /**
     * @return normal to plane
     */
    public Vector getNormal(Point p) {
	return normal;
    }

    /**
     * gets normal vector
     * @return normal to plane
     */
    public Vector getNormal() {
	return normal;
    }

    @Override
    public String toString() {
	return "" + q0 + ", " + normal;
    }

    /**
     * 
     * @return point
     */
    public Point getPoint() {
	return q0;
    }

}
