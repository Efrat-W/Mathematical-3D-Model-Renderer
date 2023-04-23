/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
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
	 * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
	 */
	 @Test
	 public void testFindIntersections() {
	 }
	 }

}
