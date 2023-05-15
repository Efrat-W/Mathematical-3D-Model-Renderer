/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.*;

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
	// ============ Equivalence Partitions Tests ==============

	// TC01: Ray's line is Inside triangle (1 points)
	Triangle triangle = new Triangle(new Point(-1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
	Ray ray1 = new Ray(new Point(0, 0.5, -1), new Vector(0, 0, 1));
	assertEquals(List.of(new Point(0, 0.5, 0)), triangle.findIntersections(ray1),
		"Bad intersects to triangle - line is Inside triangle");
	// TC02: Ray's line is Outside against edge (0 points)
	Ray ray2 = new Ray(new Point(2, 0.5, -1), new Vector(0, 0, 1));
	assertNull(triangle.findIntersections(ray2), "Bad intersects to triangle - line is Outside against edge");
	// TC03: Ray's line is Outside against vertex (0 points)
	Ray ray3 = new Ray(new Point(0, 2, -1), new Vector(0, 0, 1));
	assertNull(triangle.findIntersections(ray3), "Bad intersects to triangle - line is Outside against vertex");

	// =============== Boundary Values Tests ==================

	// TC11: Ray's line is On edge (0 points)
	Ray ray4 = new Ray(new Point(0.5, 0, -1), new Vector(0, 0, 1));
	assertNull(triangle.findIntersections(ray4), "Bad intersects to triangle - line is On edge");
	// TC12: Ray's line is In vertex (0 points)
	Ray ray5 = new Ray(new Point(0, 1, -1), new Vector(0, 0, 1));
	assertNull(triangle.findIntersections(ray5), "Bad intersects to triangle - line is In vertex");
	// TC13: Ray's line is On edge's continuation (0 points)
	Ray ray6 = new Ray(new Point(2, 0, -1), new Vector(0, 0, 1));
	assertNull(triangle.findIntersections(ray6), "Bad intersects to triangle - line is On edge's continuation");

    }
}
