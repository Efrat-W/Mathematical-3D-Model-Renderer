/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
		Ray ray = new Ray(p0, new Vector(1, 0, 0));
		Cylinder cyl = new Cylinder(5, ray, 2);
		// ensure there are no exceptions
		assertDoesNotThrow(() -> cyl.getNormal(new Point(1, 2, 0)), "");
		// generate the test result
		Vector result = cyl.getNormal(new Point(1, 2, 0));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
		// ensure the result is orthogonal to the ray
		assertTrue(isZero(ray.getDir().dotProduct(result)), "getNormal() isn't orthogonal to the cylinder");
		// TC02: the normal is on the 1st base
		Point p = new Point(0, 0, 1);
		result = cyl.getNormal(p);
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
		// ensure the result is orthogonal to the ray
		Vector vec = p.subtract(p0);
		assertTrue(isZero(result.dotProduct(vec)), "getNormal() isn't orthogonal to the cylinder");
		// TC03: the normal is on the 2nd base
		p = new Point(5, 0, 1);
		p0 = new Point(5, 0, 0);
		result = cyl.getNormal(p);
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
		// ensure the result is orthogonal to the ray
		vec = p.subtract(p0);
		assertTrue(isZero(result.dotProduct(vec)), "getNormal() isn't orthogonal to the cylinder");
		// =============== Boundary Values Tests ==================
		// TC11: the normal is at the center of the 1st base
		p0 = new Point(0, 0, 0);
		result = cyl.getNormal(p0);
		// check that the normal is parallel to the ray
		assertThrows(IllegalArgumentException.class, () -> result.crossProduct(new Vector(1, 0, 0)), //
				"getNormal() for 0 vector does not throw an exception");
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");

		// TC12: the normal is at the center of the 2nd base
		p0 = new Point(5, 0, 0);
		result = cyl.getNormal(p0);
		// check that the normal is parallel to the ray
		assertThrows(IllegalArgumentException.class, () -> result.crossProduct(new Vector(1, 0, 0)), //
				"getNormal() for 0 vector does not throw an exception");
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");

	}

}
