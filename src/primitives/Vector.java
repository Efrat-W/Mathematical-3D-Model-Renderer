package primitives;

//import javax.management.ValueExp;

/**
 * Class Vector is the basic class representing Vector of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class Vector extends Point {

	/**
	 * ctor for vector
	 * 
	 * @param d1 first value for the point
	 * @param d2 second value for the point
	 * @param d3 third value for the point
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector is invalid!");
	}

	/**
	 * ctor for vector
	 * 
	 * @param value a double3 to initiate the point's coordinate
	 */
	Vector(Double3 value) {
		super(value);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector is invalid!");
	}

	/**
	 * Addition of two vectors
	 * 
	 * @param vec the other vector to perform the operation on (in addition to the
	 *            current vector)
	 * @return the new vector after the addition
	 */
	public Vector add(Vector vec) {
		return new Vector(xyz.add(vec.xyz));
	}

	/**
	 * multiplication of a vector in a scalar
	 * 
	 * @param num the scalar
	 * @return the new vector after the multiplication
	 */
	public Vector scale(double num) {
		return new Vector(xyz.scale(num));
	}

	/**
	 * Performs dot product between two vectors (due to the algebraic formula)
	 * 
	 * @param vec the other vector to perform the operation on (in addition to the
	 *            current vector)
	 * @return a scalar
	 */
	public double dotProduct(Vector vec) {
		return vec.xyz.d1 * xyz.d1 + vec.xyz.d2 * xyz.d2 + vec.xyz.d3 * xyz.d3;
	}

	/**
	 * Performs cross product between two vectors (due to the algebraic formula)
	 * 
	 * @param vec the other vector to perform the operation on (in addition to the
	 *            current vector)
	 * @return the vector perpendicular to two vectors
	 */
	public Vector crossProduct(Vector vec) {
		return new Vector((xyz.d2 * vec.xyz.d3) - (xyz.d3 * vec.xyz.d2), (xyz.d3 * vec.xyz.d1) - (xyz.d1 * vec.xyz.d3),
				(xyz.d1 * vec.xyz.d2) - (xyz.d2 * vec.xyz.d1));
	}

	/**
	 * Calculates the squared length of a vector
	 * 
	 * @return the squared length
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}

	/**
	 * Calculates the length of a vector
	 * 
	 * @return the length
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * normalizing a vector
	 * 
	 * @return the normalized vector
	 */
	public Vector normalize() {
		return new Vector(xyz.reduce(length()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Vector other)
			return super.equals(other);
		return false;
	}

	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public String toString() {
		return "‐>" + super.toString();
	}
}
