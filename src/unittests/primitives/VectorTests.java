/**
 * 
 */
package unittests.primitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Vector;
import static primitives.Util.*;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1, 1, 4);
		// ============ Equivalence Partitions Tests ==============
		// TC01: for a obtuse angle
		Vector v2 = new Vector(-2, 0, 4);
		Vector newVec = v1.add(v2);
		assertEquals(new Vector(-1, 1, 8), newVec, "Add() wrong result");
		// TC02: for a sharp angle
		Vector v3 = new Vector(0, 3, 4);
		newVec = v1.add(v3);
		assertEquals(new Vector(1, 4, 8), newVec, "Add() wrong result");
		//TC03: orthogonal vectors:
		v1=new Vector(1, 2,3);
		v2=new Vector(0, 3, -2);
		newVec = v1.add(v2);
		assertEquals(new Vector(1, 5, 1), newVec, "Add() wrong result");
		//TC04: parallel vectors same length:
		newVec=v1.add(v1);
		assertEquals(new Vector(2, 4, 6), newVec, "Add() wrong result");
		//TC05: parallel vectors different length:
		v3= new Vector(2, 4, 6);
		newVec = v1.add(v3);
		assertEquals((new Vector(3, 6, 9)).equals(newVec), "Add() wrong result");
		//TC06: opposite direction different length:
		v3=new Vector(-2, -4, -6);
		newVec = v1.add(v3);
		assertEquals(new Vector(-1, -2, -3), newVec, "Add() wrong result");
		// =============== Boundary Values Tests ==================
		//TC11: opposite direction same length:
		v3=new Vector(-1, -2, -3);
		assertThrows(IllegalArgumentException.class, () -> v1.add(v3),
				"add() for 0 vector does not throw an exception");	
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		//TC01: the scalar is a number
		Vector newVector= v1.scale(5);
		assertEquals(newVector,new Vector(5, 10, 15) ,"Scale() wrong result");
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(newVector),
				"the scaled vector is not in the same direction");
		// =============== Boundary Values Tests ==================
		//TC11: the scalar is 0
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
				"scale() for 0 vector does not throw an exception");	
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v1 = new Vector(1, 1, 4);
		// ============ Equivalence Partitions Tests ==============
		// TC01: for a obtuse angle
		Vector v2 = new Vector(-2, 0, 4);
		double scal = v1.dotProduct(v2);
		assertEquals(14, scal, "DotProduct() wrong result");
		// TC02: for a sharp angle
		Vector v3 = new Vector(0, 3, 4);
		scal = v1.dotProduct(v3);
		assertEquals(19, scal, "DotProduct() wrong result");
		//TC03: parallel vectors same length:
		scal=v1.dotProduct(v1);
		assertEquals(18, scal, "DotProduct() wrong result");
		//TC04: parallel vectors different length:
		v1=new Vector(1, 2,3);
		v3= new Vector(2, 4, 6);
		scal = v1.dotProduct(v3);
		assertEquals(28, scal, "DotProduct() wrong result");
		//TC05: opposite direction different length:
		v3=new Vector(-2, -4, -6);
		scal = v1.dotProduct(v3);
		assertEquals(-28, scal, "DotProduct() wrong result");
		//TC06: opposite direction same length:
		v3=new Vector(-1, -2, -3);
		scal = v1.dotProduct(v3);
		assertEquals(-14, scal, "DotProduct() wrong result");
		// =============== Boundary Values Tests ==================
		//TC11: orthogonal vectors:
		v2=new Vector(0, 3, -2);
		scal = v1.dotProduct(v2);
		assertTrue(isZero(scal), "Add() wrong result");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v2);
		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
		// TC02: Test cross-product result orthogonality to its operands
		assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
		assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from cross-productof co-lined vectors
		Vector v3 = new Vector(-2, -4, -6);
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
				"crossProduct() for parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============
		//test the squared distance
		Vector v = new Vector(1, 2, 3);
		assertEquals(14, v.lengthSquared(), "lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Equivalence Partitions Tests ==============
		//test the distance
		assertEquals(new Vector(0, 3, 4).length(), 5, "length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v = new Vector(0, 3, 4);
		Vector n = v.normalize();
		// ============ Equivalence Partitions Tests ==============
		// TC01: Simple test
		assertEquals(1d, n.lengthSquared(), 0.00001, "wrong normalized vector length");
		assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),
				"normalized vector is not in the same direction");
		assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
	}

}
