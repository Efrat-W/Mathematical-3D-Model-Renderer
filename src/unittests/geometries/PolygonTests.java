/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

/**
 * Unit tests for geometries.Polygon class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class PolygonTests {

    /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
    @Test
    public void testConstructor() {
	// ============ Equivalence Partitions Tests ==============

	// TC01: Correct concave quadrangular with vertices in correct order
	try {
	    new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
	} catch (IllegalArgumentException e) {
	    fail("Failed constructing a correct polygon");
	}

	// TC02: Wrong vertices order
	assertThrows(IllegalArgumentException.class, //
		() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
		"Constructed a polygon with wrong order of vertices");

	// TC03: Not in the same plane
	assertThrows(IllegalArgumentException.class, //
		() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
		"Constructed a polygon with vertices that are not in the same plane");

	// TC04: Concave quadrangular
	assertThrows(IllegalArgumentException.class, //
		() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
			new Point(0.5, 0.25, 0.5)), //
		"Constructed a concave polygon");

	// =============== Boundary Values Tests ==================

	// TC10: Vertex on a side of a quadrangular
	assertThrows(IllegalArgumentException.class, //
		() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
		"Constructed a polygon with vertix on a side");

	// TC11: Last point = first point
	assertThrows(IllegalArgumentException.class, //
		() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
		"Constructed a polygon with vertice on a side");

	// TC12: Co-located points
	assertThrows(IllegalArgumentException.class, //
		() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
		"Constructed a polygon with vertice on a side");

    }

    /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
	// ============ Equivalence Partitions Tests ==============
	// TC01: There is a simple single test here - using a quad
	Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
	Polygon pol = new Polygon(pts);
	// ensure there are no exceptions
	assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
	// generate the test result
	Vector result = pol.getNormal(new Point(0, 0, 1));
	// ensure |result| = 1
	assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
	// ensure the result is orthogonal to all the edges
	for (int i = 0; i < 3; ++i)
	    assertEquals(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), 0, 0.00000001,
		    "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
	// ============ Equivalence Partitions Tests ==============

	// TC01: Ray's line is Inside polygon (1 points)
	Polygon polygon = new Polygon(new Point(-1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
	Ray ray1 = new Ray(new Point(0, 0.5, -1), new Vector(0, 0, 1));
	assertEquals(List.of(new Point(0, 0.5, 0)), polygon.findIntersections(ray1),
		"Bad intersects to polygon - line is Inside polygon");
	// TC02: Ray's line is Outside against edge (0 points)
	Ray ray2 = new Ray(new Point(2, 0.5, -1), new Vector(0, 0, 1));
	assertNull(polygon.findIntersections(ray2), "Bad intersects to polygon - line is Outside against edge");
	// TC03: Ray's line is Outside against vertex (0 points)
	Ray ray3 = new Ray(new Point(0, 2, -1), new Vector(0, 0, 1));
	assertNull(polygon.findIntersections(ray3), "Bad intersects to polygon - line is Outside against vertex");

	// =============== Boundary Values Tests ==================

	// TC11: Ray's line is On edge (0 points)
	Ray ray4 = new Ray(new Point(0.5, 0, -1), new Vector(0, 0, 1));
	assertNull(polygon.findIntersections(ray4), "Bad intersects to polygon - line is On edge");
	// TC12: Ray's line is In vertex (0 points)
	Ray ray5 = new Ray(new Point(0, 1, -1), new Vector(0, 0, 1));
	assertNull(polygon.findIntersections(ray5), "Bad intersects to polygon - line is In vertex");
	// TC13: Ray's line is On edge's continuation (0 points)
	Ray ray6 = new Ray(new Point(2, 0, -1), new Vector(0, 0, 1));
	assertNull(polygon.findIntersections(ray6), "Bad intersects to polygon - line is On edge's continuation");

    }

}
