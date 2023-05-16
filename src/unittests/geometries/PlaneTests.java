/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import geometries.Plane;
import primitives.*;

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
		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is intersects the plane (1 points)
		Plane plane = new Plane(new Point(1, 0, 0), new Vector(1, 0, 0));
		Ray ray1 = new Ray(new Point(0, 0, 1), new Vector(1, 0, -1));
		assertEquals(List.of(new Point(1, 0, 0)), plane.findIntersections(ray1),
				"Bad intersects to plane - line is intersects the plane");
		// TC02: Ray's line does not intersect the plane (0 points)
		Ray ray2 = new Ray(new Point(2, 0, 0), new Vector(1, 0, 1));
		assertEquals(null, plane.findIntersections(ray2),
				"Bad intersects to plane - line does not intersect the plane");

		// =============== Boundary Values Tests ==================

		// **** Group: Ray is parallel to the plane
		// TC11: Ray is parallel to the plane and included in the plane (0 points)
		Ray ray3 = new Ray(new Point(1, 0, 1), new Vector(0, 0, 1));
		assertEquals(null, plane.findIntersections(ray3),
				"Bad intersects to plane - Ray is parallel to the plane and included in the plane");
		// TC12: Ray is parallel to the plane and not included in the plane (0 points)
		Ray ray4 = new Ray(new Point(2, 0, 0), new Vector(0, 0, 1));
		assertEquals(null, plane.findIntersections(ray4),
				"Bad intersects to plane - Ray is parallel to the plane and not included in the plane");

		// **** Group: Ray is orthogonal to the plane
		// TC13: Ray is orthogonal to the plane and P0 before the plane (1 points)
		Ray ray5 = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0));
		assertEquals(List.of(new Point(1, 0, 0)), plane.findIntersections(ray5),
				"Bad intersects to plane - Ray is orthogonal to the plane and p0 before the plane");
		// TC14: Ray is orthogonal to the plane and P0 in the plane (0 points)
		Ray ray6 = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
		assertEquals(null, plane.findIntersections(ray6),
				"Bad intersects to plane - Ray is orthogonal to the plane and p0 in the plane");
		// TC15: Ray is orthogonal to the plane and P0 after the plane (0 points)
		Ray ray7 = new Ray(new Point(2, 0, 0), new Vector(1, 0, 0));
		assertEquals(null, plane.findIntersections(ray7),
				"Bad intersects to plane - Ray is orthogonal to the plane and p0 after the plane");

		// **** Group: Special cases
		// TC16: Ray is neither orthogonal nor parallel to and begins at the plane (0
		// points)
		Ray ray8 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 1));
		assertEquals(null, plane.findIntersections(ray8),
				"Bad intersects to plane - Ray is neither orthogonal nor parallel to and begins at the plane");
		// TC17: Ray is neither orthogonal nor parallel to the plane and begins in the
		// same point which appears as reference point in the plane (0 points)
		Ray ray9 = new Ray(new Point(1, 0, 0), new Vector(1, 0, 1));
		assertEquals(null, plane.findIntersections(ray9),
				"Bad intersects to plane - Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane");

	}

}
