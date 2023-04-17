/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;
import static primitives.Util.*;

/**
 * Unit tests for geometries.Tube class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point p0 = new Point(0, 0, 0);
		Ray v = new Ray(p0, new Vector(1, 0, 0));
		double rad = 2;
		Tube tube = new Tube(v, rad);
		Point p = new Point(2, 1, 0);

		Vector n = tube.getNormal(p);
		assertTrue(isZero(v.getDir().dotProduct(n)), "getNormal() isn't orthogonal to the tube at tested point.");
		// ensure |n| = 1
		assertEquals(1, n.length(), 0.00000001, "Tube's normal is not a unit vector");
		// =============== Boundary Values Tests ==================
		// TC11: the point on the tube (of the normal) is in front of the head ray
		rad = 1;
		tube = new Tube(v, rad);
		p = new Point(1, 0, 0);

		n = tube.getNormal(p);
		assertTrue(isZero(v.getDir().dotProduct(n)), "getNormal() isn't orthogonal to the tube at tested point.");
		// ensure |n| = 1
		assertEquals(1, n.length(), 0.00000001, "Tube's normal is not a unit vector");
	}

}
