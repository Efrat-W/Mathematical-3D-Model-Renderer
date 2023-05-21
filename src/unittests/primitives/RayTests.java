package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * Unit tests for primitives.Ray class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class RayTests {

	/**
	 * Test method for
	 * {@link primitives.Ray#findClosestPoint(List<primitives.Point>)}.
	 */

	@Test
	void findClosestPointTest() {
		Ray ray = new Ray(new Point(0, 0, 1), new Vector(0, 1, 0));
		Point p1 = new Point(0, 1, 1);
		Point p2 = new Point(0, -2, -2);
		Point p3 = new Point(0, 2, 3);
		List<Point> list = List.of(p2, p1, p3);

		// ============ Equivalence Partitions Tests ==============
		// TC01: random point from given list is the closest to ray head
		assertEquals(p1, ray.findClosestPoint(list), "wrong closest point");

		// =============== Boundary Values Tests ==================
		// TC11: empty list
		assertNull(ray.findClosestPoint(List.of()), "empty list of points");

		// TC12: first point
		assertEquals(p1, ray.findClosestPoint(List.of(p1, p2, p3)), "wrong closest point");

		// TC13: last point
		assertEquals(p1, ray.findClosestPoint(List.of(p2, p3, p1)), "wrong closest point");
	}

	/**
	 * Test method for {@link primitives.Ray#getPoint(double t)}.
	 */

	@Test
	void getPointTest() {
		Point p0 = new Point(0, 0, 1);
		Ray ray = new Ray(p0, new Vector(0, 1, 0));
		// ============ Equivalence Partitions Tests ==============
		// TC01: t>0
		assertEquals(new Point(0, 2, 1), ray.getPoint(2), "wrong point");

		// TC02: t<0
		assertEquals(new Point(0, -2, 1), ray.getPoint(-2), "wrong point");

		// =============== Boundary Values Tests ==================
		// TC11: t=0
		assertEquals(p0, ray.getPoint(0), "wrong point");

	}

}
