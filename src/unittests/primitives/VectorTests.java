/**
 * 
 */
package unittests.primitives;

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
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(4, 5, 7);
		Vector newVec = v1.add(v2);
		assertEquals((new Vector(5, 7, 10)).length(), newVec.length(), 0.00001, "Add() wrong result");
		// =============== Boundary Values Tests ==================
		
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		fail("Not yet implemented");
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
