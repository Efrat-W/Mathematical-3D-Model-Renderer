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
		Point p1 = new Point(2, 1, 0);
		Vector n1 = tube.getNormal(p1);
		assertEquals(v.getDir().dotProduct(n1), 0, 0.00000001,
				"getNormal() isn't orthogonal to the tube at tested point.");
		// ensure |n| = 1
		assertEquals(1, n1.length(), 0.00000001, "Tube's normal is not a unit vector");

		// =============== Boundary Values Tests ==================
		// TC11: the point on the tube (of the normal) is in front of the head ray
		rad = 1;
		tube = new Tube(v, rad);
		Point p2 = new Point(0, 0, 1);
		Vector n2 = tube.getNormal(p2);
		assertEquals(v.getDir().dotProduct(n2), 0, 0.00000001,
				"getNormal() isn't orthogonal to the tube at tested point.");
		// ensure |n| = 1
		assertEquals(1, n2.length(), 0.00000001, "Tube's normal is not a unit vector");
	}

	/**
	 * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
	 Tube tube = new Tube(new Ray(new Point(0, -1, 0), new Vector(2,2,0)), 1);
	 // ============ Equivalence Partitions Tests ==============
	 // TC01: the ray starts from inside, intersects with the tube (1 point)
	 Point p1 = new Point(1, 0, 1);
	 List<Point> result1 = tube.findIntersections(new Ray(new Point(1,1,0),
		    new Vector(0,-1,1)));
	    assertEquals(1, result1.size(), "Wrong number of points");
	    assertEquals(p1, result1.get(0), "Ray crosses tube from inside incorrectly.");
	    
	 // TC02: the ray starts from outside, intersects with the tube (2 point)
	 List<Point> result2 = tube.findIntersections(new Ray(new Point(1.5, -0.5, 0),
		    new Vector(0.2, 0.61, 0.7)));
	    assertEquals(1, result2.size(), "Wrong number of points");
	    assertEquals(p3, result2.get(0), "Ray crosses tube from outside incorrectly.");
	    
	 // TC03: the ray starts from outside, doesn't intersect with the tube (0 point)
	 assertNull(Tube.findIntersections(new Ray(new Point(-1,1,1),
		    new Vector(2,0,1))),
		    "Ray out of tube");
	    
	    
	 // =============== Boundary Values Tests ==================
	 // TC11: the ray is parallel to the tube
	 assertNull(Tube.findIntersections(new Ray(new Point(1,0,2),
		    new Vector(1,1,0))),
		    "Ray's parallel to tube");
	 
	// TC12: the ray is a skew line to the tube
		 assertNull(Tube.findIntersections(new Ray(new Point(1,0,2),
			    new Vector(1,1,0))),
			    "Ray's parallel to tube, ray point on tube surface");
	 
	 // TC13: the ray is orthogonal to the tube
	 assertNull(Tube.findIntersections(new Ray(new Point(1,0,1),
		    new Vector(0,0,1))),
		    "Ray's orthogonal to tube");
	 
	// TC12: the ray is a skew line to the tube, never intersecting
	    
	}
}
