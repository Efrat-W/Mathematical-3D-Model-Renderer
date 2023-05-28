package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
import java.util.List;

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
		Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
				"Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
		List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		Point p3 = new Point(2, 0, 0);
		List<Point> result2 = sphere.findIntersections(new Ray(new Point(1.5, -0.5, 0), new Vector(0.5, 0.5, 0)));
		assertEquals(1, result2.size(), "Wrong number of points");
		assertEquals(p3, result2.get(0), "Ray crosses sphere from inside incorrectly.");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(2.5, -1, 0.8), new Vector(3, -1, 1))),
				"Ray's line out of sphere and starts outside of sphere");

		
		// TC05: Ray starts before and crosses the sphere but is too far (0 points)
		assertNull(sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)), 0.5),
			"Ray's line out of sphere");
				
		// =============== Boundary Values Tests ==================
		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		Point p4 = new Point(1, -1, 0);
		List<Point> result3 = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, -1, 0)));
		assertEquals(1, result3.size(), "Wrong number of points");
		assertEquals(p4, result3.get(0), "Ray crosses sphere from inside incorrectly.");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1.59, -58, 0.57), new Vector(-0.19, -0.85, 1))),
				"Ray's line out of sphere and starts on sphere surface");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		Point p6 = new Point(1, 0, -1);
		Point p5 = new Point(1, 0, 1);
		List<Point> result4 = sphere.findIntersections(new Ray(new Point(1, 0, -2), new Vector(0, 0, 1)));
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result4.get(1), result.get(0));
		assertEquals(List.of(p5, p6), result4, "Ray crosses sphere");

		// TC14: Ray starts at sphere and goes inside (1 points)
		Point p7 = new Point(2, 0, 0);
		List<Point> result5 = sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 1, 0)));
		assertEquals(1, result5.size(), "Wrong number of points");
		assertEquals(p7, result5.get(0), "Ray crosses sphere from inside incorrectly.");

		// TC15: Ray starts inside (1 points)
		Point p8 = new Point(1, 1, 0);
		List<Point> result6 = sphere.findIntersections(new Ray(new Point(1, -0.5, 0), new Vector(0, 1.5, 0)));
		assertEquals(1, result6.size(), "Wrong number of points");
		assertEquals(p8, result6.get(0), "Ray crosses sphere from inside incorrectly.");

		// TC16: Ray starts at the center (1 points)
		Point p9 = new Point(1, 1, 0);
		List<Point> result7 = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
		assertEquals(1, result7.size(), "Wrong number of points");
		assertEquals(p9, result7.get(0), "Ray starts at center crosses sphere from inside incorrectly.");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, 1))),
				"Ray's direction through center, line out of sphere and starts on sphere surface");

		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1.5), new Vector(0, 0, 1))),
				"Ray's direction through center, line out of sphere and starts outside of sphere");

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), new Vector(1, 0, 0))),
				"Ray's tangent to sphere, starts before");

		// TC20: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 0, 0))),
				"Ray's tangent to sphere, starts before");

		// TC21: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(2, -1, 0), new Vector(1, 0, 0))),
				"Ray's tangent to sphere, starts before");

		// **** Group: Special cases
		// TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull(sphere.findIntersections(new Ray(new Point(2, -2, 0), new Vector(-1, 0, 0))),
				"Ray's perpendicular to sphere");
	}

}
