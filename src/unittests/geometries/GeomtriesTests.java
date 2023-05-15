package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Geometries class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class GeomtriesTests {

    /**
     * Test method for
     * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
	Ray ray = new Ray(new Point(0, 0, 3), new Vector(1, 0, 0));
	Ray ray2 = new Ray(new Point(-1, 0.5, -1), new Vector(1, -0.4, 1.1));
	// =============== Boundary Values Tests ==================
	Geometries g = new Geometries();
	// TC11: empty collection
	assertNull(g.findIntersections(ray), //
		"There can't be intersections with an empty collection");
	// TC12: no shape intersect
	g.add(new Sphere(new Point(1, 0, 0), 1d),
		new Polygon(new Point(-1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0)));
	assertNull(g.findIntersections(ray), //
		"There sholdn't be intersections with this collection");
	// TC13: only one shape intersects
	g.add(new Tube(new Ray(new Point(1, 0, -3), new Vector(0, 0, 1)), 0.5));
	assertEquals(2, g.findIntersections(ray).size(), "Wrong number of intersections");
	// TC14: all shapes intersect
	assertEquals(5, g.findIntersections(ray2).size(), "Wrong number of intersections");
	// ============ Equivalence Partitions Tests ==============
	// TC01: some shapes intersect (not all of them)
	g.add(new Sphere(new Point(-2, 4, 7), 1d));
	assertEquals(5, g.findIntersections(ray2).size(), "Wrong number of intersections");

    }

}
