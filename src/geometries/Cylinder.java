package geometries;


import primitives.Ray;

public class Cylinder extends Tube {

	final double height;
	
	public Cylinder(double h, Ray ar, double rad) {
		super(ar, rad);
		height=h;
	}
	
	public double getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return super.toString()+ ", " +height;
	}
	
	
}
