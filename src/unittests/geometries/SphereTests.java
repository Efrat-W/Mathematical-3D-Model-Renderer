/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

/**
 * Unit tests for geometries.Sphere class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point center = new Point(0, 0, 0);
		double radius = 1;
		// build the sphere
		Sphere s = new Sphere(center, radius);
		Point p = new Point(1, 0, 0);
		Vector n = p.subtract(center).normalize();
		assertEquals(n, s.getNormal(p), "getNormal() is wrong.");
		// ensure |n| = 1
		assertEquals(1, n.length(), 0.00000001, "Sphere's normal is not a unit vector");
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
	}

}
