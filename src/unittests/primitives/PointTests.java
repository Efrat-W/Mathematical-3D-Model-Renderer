/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {

		// ============ Equivalence Partitions Tests ==============
		// TC 01:
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(2, 3, 7);
		Vector pr = new Vector(-1, -1, -4);
		assertEquals(pr, p1.subtract(p2), "Subtract() is wrong.");

		// =============== Boundary Values Tests ==================
		// TC11: subtract same value
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(new Point(1, 2, 3)),
				"subtract() for 0 vector does not throw an exception");

	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		// TC 01:
		Point p = new Point(2, 8, 3);
		Vector pr = new Vector(3, 10, 6);
		assertEquals(p.add(v1), pr, "Add() is wrong.");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC 01:
		Point p1 = new Point(2.0, 8.0, 3.0);
		Point p2 = new Point(1, 2, 3);
		assertEquals(37, p1.distanceSquared(p2), 0.0001, "Distance() is wrong.");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// ============ Equivalence Partitions Tests ==============
		// TC 01:
		Point p1 = new Point(2, 8, 3);
		Point p2 = new Point(2, 5, -1);
		assertEquals(5, p1.distance(p2), 0.0001, "Distance() is wrong.");
	}

}
