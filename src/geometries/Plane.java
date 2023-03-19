package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{

	final Point q0;
	final Vector normal;
	
	public Plane(Point p1, Point p2, Point p3) {
		q0=p1;
		normal=null;
	}
	
	public Plane(Point p, Vector vec) {
		q0=p;
		normal=vec.normalize();
	}
	
	public Vector getNormal(Point p) {
		return normal;
	}
	
	public Vector getNormal() {
		return getNormal(q0);
	}
	
	@Override
	public String toString() {
		return "" + q0 + ", " +normal;
	}
	
	public Point getPoint() {
		return q0;
	}
	
}
