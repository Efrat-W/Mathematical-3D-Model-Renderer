package primitives;

//import javax.management.ValueExp;

/**
* Class Vector is the basic class representing Vector of Euclidean geometry in Cartesian
* 3-Dimensional coordinate system.
*  @author Efrat Wexler and Sari Zilberlicht
*/
public class Vector extends Point {
	
	public Vector(double d1, double d2, double d3) 
	{
		super(d1, d2, d3);
		if(xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector is invalid!");
	}

	Vector(Double3 value) {
		super(value);
		if(xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector is invalid!");
	}
	
	public Vector add(Vector vec) {
		return new Vector(xyz.add(vec.xyz));
	}
	
	public Vector scale(double num) {
		return new Vector(xyz.scale(num));
	}
	
	public double dotProduct(Vector vec) {
		return vec.xyz.d1*xyz.d1+
				vec.xyz.d2*xyz.d2+
				vec.xyz.d3*xyz.d3;
	}
	public Vector crossProduct(Vector vec) {
		return new Vector((xyz.d2*vec.xyz.d3)-(xyz.d3*vec.xyz.d2), 
				(xyz.d3*vec.xyz.d1)-(xyz.d1*vec.xyz.d3),
				(xyz.d1*vec.xyz.d2)-(xyz.d2*vec.xyz.d1));
	}
	
	public double lengthSquared() {
		return dotProduct(this);
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public Vector normalize() {
		return new Vector(xyz.reduce(length()));
	}
	
	@Override
	public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj instanceof Vector other)
		return super.equals(other);
	return false;
	}
	@Override
	public int hashCode() { return xyz.hashCode(); }
	@Override
	public String toString() { return "‐>" + super.toString(); }
}
