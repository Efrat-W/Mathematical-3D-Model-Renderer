/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import primitives.*;

/**
 * Unit tests for geometries.Cylinder class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class CylinderTests {

	/** Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}. */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point p0 = new Point(0, 0, 0);
		Vector v = new Vector(1, 0, 0);
		Ray ray = new Ray(p0, v);
		Cylinder cyl = new Cylinder(5, ray, 2);
		// ensure there are no exceptions
		Point p1 = new Point(1, 2, 0);
		assertDoesNotThrow(() -> cyl.getNormal(p1), "");
		// generate the test result
		Vector result = cyl.getNormal(p1);
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
		// ensure the result is orthogonal to the ray
		assertEquals(ray.getDir().dotProduct(result), 0, 0.00000001, "getNormal() isn't orthogonal to the cylinder");

		// TC02: the normal is on the 1st base
		Point p2 = new Point(0, 0, 1);
		result = cyl.getNormal(p2);
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
		// ensure the result is orthogonal to the ray
		Vector vec = p2.subtract(p0);
		assertEquals(result.dotProduct(vec), 0, 0.00000001, "getNormal() isn't orthogonal to the cylinder");

		// TC03: the normal is on the 2nd base
		Point p3 = new Point(5, 0, 1);
		Point pointh = new Point(5, 0, 0);
		result = cyl.getNormal(p3);
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
		// ensure the result is orthogonal to the ray
		vec = p3.subtract(pointh);
		assertEquals(result.dotProduct(vec), 0, 0.00000001, "getNormal() isn't orthogonal to the cylinder");

		// =============== Boundary Values Tests ==================
		// TC11: the normal is at the center of the 1st base
		Vector result2 = cyl.getNormal(p0);
		// check that the normal is parallel to the ray
		assertThrows(IllegalArgumentException.class, () -> result2.crossProduct(v), //
				"getNormal() for 0 vector does not throw an exception");
		// ensure |result| = 1
		assertEquals(1, result2.length(), 0.00000001, "Cylinder's normal is not a unit vector");

		// TC12: the normal is at the center of the 2nd base
		Vector result3 = cyl.getNormal(pointh);
		// check that the normal is parallel to the ray
		assertThrows(IllegalArgumentException.class, () -> result3.crossProduct(v), //
				"getNormal() for 0 vector does not throw an exception");
		// ensure |result| = 1
		assertEquals(1, result3.length(), 0.00000001, "Cylinder's normal is not a unit vector");

	}

	/**
	 * Test method for
	 * {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Cylinder cylinder = new Cylinder(3, new Ray(new Point(0, -1, 1), new Vector(0, 3, 0)), 1);
		Point baseApoint = new Point(0.5, -1, 0.5);
		Point baseBpoint = new Point(0.5, 2, 0.5);
		Point p1 = new Point(0, 2, 1); // point on opposite base from axis ray head
		// Point p2 = new Point(0, -1, 1.5); // point on opposite base from axis ray
		// head
		// ============ Equivalence Partitions Tests ==============
		// TC01: the ray starts on A base, intersects with the cylinder's B base (1
		// point)
		List<Point> result1a = cylinder.findIntersections(new Ray(baseApoint, new Vector(-0.5, 3, 0.5)));
		assertEquals(1, result1a.size(), "Wrong number of points");
		assertEquals(p1, result1a.get(0), "Ray crosses cylinder from inside incorrectly.");

		// TC02: the ray starts from inside, intersects with the cylinder's B base (1
		// point)
		List<Point> result2 = cylinder.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 2, 0.5)));
		assertEquals(1, result2.size(), "Wrong number of points");
		assertEquals(p1, result2.get(0), "Ray crosses cylinder from inside incorrectly.");

		// TC03: the ray starts from inside, intersects with the cylinder's A base (1
		// point)
		// List<Point> result2a = cylinder.findIntersections(new Ray(new Point(0, 1,
		// 0.5), new Vector(0, -2, 1)));
		// assertEquals(1, result2a.size(), "Wrong number of points");
		// assertEquals(p2, result2a.get(0), "Ray crosses cylinder from inside
		// incorrectly.");

		// TC04: the ray starts on base, goes outwards (0 points)
		assertNull(cylinder.findIntersections(new Ray(baseApoint, new Vector(-0.6, -4, -0.5))),
				"Ray's parallel to cylinder and on top of its surface");

		// TC05: the ray starts on opposite base from axis ray head, goes outwards (0
		// points)
		assertNull(cylinder.findIntersections(new Ray(baseBpoint, new Vector(-0.5, 2, -0.5))),
				"Ray's parallel to cylinder and on top of its surface");

		// =============== Boundary Values Tests ==================

	}

}
