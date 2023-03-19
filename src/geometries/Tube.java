package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {

	final Ray axisRay;
	
	public Tube(Ray ar, double rad) {
		super(rad);
		axisRay=ar;
	}


	public Double getRadius() {
		return radius;
	}
	
	
	public Ray getAxisRay() {
		return axisRay;
	}	
	
	public Vector getNormal(Point p) {
		return null;
	}
	
	@Override
	public String toString() {
		return "" + axisRay + ", " +radius;
	}
	
}

