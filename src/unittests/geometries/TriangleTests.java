/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for geometries.Triangle class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		Point p0 = new Point(0, 0, 1);
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(0, 1, 0);

		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point[] pts = { p0, p1, p2 };
		Triangle tri = new Triangle(p0, p1, p2);
		// ensure there are no exceptions
		assertDoesNotThrow(() -> tri.getNormal(p0), "");
		// generate the test result
		Vector result = tri.getNormal(p0);
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 2; ++i)
			assertEquals(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), 0, 0.00000001,
					"Triangle's normal is not orthogonal to one of the edges");
	}

	/**
	 * Test method for
	 * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
	}
}
