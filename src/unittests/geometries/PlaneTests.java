/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for geometries.Plane class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#Plane(Point p1, Point p2, Point p3)}.
	 */
	@Test
	void testConstructor() {
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(0, 1, 0);
		Point p2 = new Point(1, 0, 0);
		// ============ Equivalence Partitions Tests ==============
		// TC01: a normal plane:
		assertDoesNotThrow(() -> new Plane(p0, p1, p2), //
				"Error: some exception was thrown in the plane constructor");

		// =============== Boundary Values Tests ==================
		// TC11: the 1st and 2nd points coalescing
		assertThrows(IllegalArgumentException.class, () -> new Plane(p0, p0, p2), //
				"Error: two points are coalescing");

		// TC12: the 3 points are on the same straight
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)), //
				"Error: the points are on the same straight");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(0, 1, 0);
		Point p2 = new Point(1, 0, 0);
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Plane plane = new Plane(p0, p1, p2);
		// ensure there are no exceptions
		assertDoesNotThrow(() -> plane.getNormal(p0), "");
		// generate the test result
		Vector result = plane.getNormal(p0);
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		Point[] pts = { p0, p1, p2 };
		for (int i = 0; i < 2; ++i)
			assertEquals(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), 0, 0.00000001,
					"plane's normal is not orthogonal to one of the edges");
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
	}

}
