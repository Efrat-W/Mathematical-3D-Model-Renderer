package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {

	final Point center;
	// radius??

	public Sphere(Point p, double rad) {
		super(rad);
		center = p;
	}

	public Double getRadius() {
		return radius;
	}

	public Point getPoint() {
		return center;
	}

	@Override
	public String toString() {
		return "" + center + ", " + radius;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
