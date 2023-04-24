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
	Tube tube1 = new Tube(new Ray(new Point(0, -1, 0), new Vector(2, 2, 0)), 1);
	Tube tube = new Tube(new Ray(new Point(0, 2, 0), new Vector(1, 0, 0)), 1);
	// ============ Equivalence Partitions Tests ==============
	// TC01: the ray starts from inside, intersects with the tube (1 point)
	Point p1 = new Point(1, 0, 1.5);
	List<Point> result1 = tube1.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 1, -1.5)));
	assertEquals(1, result1.size(), "Wrong number of points");
	assertEquals(p1, result1.get(0), "Ray crosses tube from inside incorrectly.");

	// TC02: the ray starts from outside, intersects with the tube (2 point)
	Point p2 = new Point(1.440996463761104, 1.10249115940276, 0.440996463761104);
	Point p3 = new Point(1.938313881066482, 2.345784702666205, 0.938313881066482);
	List<Point> result2 = tube.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 2.5, 1)));
	assertEquals(2, result2.size(), "Wrong number of points");
	if (result2.get(0).getX() > result2.get(1).getX())
	    result2 = List.of(result2.get(1), result2.get(0));
	assertEquals(List.of(p2, p3), result2.get(0), "Ray crosses tube from inside incorrectly.");

	// TC03: the ray starts from outside, doesn't intersect with the tube (0 points)
	assertNull(tube1.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(2, 0, 1))), "Ray out of tube");

	// TC04: the ray starts from outside, doesn't intersect with the tube (0 points)
	assertNull(tube.findIntersections(new Ray(new Point(2, 2.5, 1), new Vector(1, 2.5, 1))), "Ray out of tube");

	// =============== Boundary Values Tests ==================
	//*************parallel******************
	// TC11: the ray is parallel to the tube from the outside
	assertNull(tube1.findIntersections(new Ray(new Point(1, 0, 2), new Vector(1, 1, 0))), "Ray's parallel to tube");

	// TC12: the ray is on top of the tube's surface and is parallel to the tube
	// axis ray
	assertNull(tube.findIntersections(new Ray(new Point(1, 2, 1), new Vector(2, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	// TC13: the ray is inside and is parallel to the tube
	// axis ray
	assertNull(tube.findIntersections(new Ray(new Point(1, 2, 0.5), new Vector(2, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	//**Group: merges with tube axis ray**//
	// TC14: the ray merges after axis ray point 
	assertNull(tube.findIntersections(new Ray(new Point(1, 2, 0), new Vector(1, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	// TC15: the ray merges before axis ray point 
	assertNull(tube.findIntersections(new Ray(new Point(-1, 2, 0), new Vector(1, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	// TC16: the ray merges with axis ray (point and ray)
	assertNull(tube.findIntersections(new Ray(new Point(0, 2, 0), new Vector(1, 0, 0))),
		"Ray's parallel to tube and on top of its surface");	
	
	///////parallel going in opposite directions
	// TC17: the ray is parallel to the tube from the outside
	assertNull(tube1.findIntersections(new Ray(new Point(1, 0, 2), new Vector(-1, -1, 0))), "Ray's parallel to tube");

	// TC18: the ray is on top of the tube's surface and is parallel to the tube
	// axis ray
	assertNull(tube.findIntersections(new Ray(new Point(1, 2, 1), new Vector(-2, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	// TC19: the ray is inside and is parallel to the tube
	// axis ray
	assertNull(tube.findIntersections(new Ray(new Point(1, 2, 0.5), new Vector(-2, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	// **Group: merges with tube axis ray**//
	// TC110: the ray merges after axis ray point
	assertNull(tube.findIntersections(new Ray(new Point(1, 2, 0), new Vector(-1, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	// TC111: the ray merges before axis ray point
	assertNull(tube.findIntersections(new Ray(new Point(-1, 2, 0), new Vector(-1, 0, 0))),
		"Ray's parallel to tube and on top of its surface");

	// TC112: the ray merges with axis ray (point and ray)
	assertNull(tube.findIntersections(new Ray(new Point(0, 2, 0), new Vector(-1, 0, 0))),
		"Ray's parallel to tube and on top of its surface");
	
	//*************orthogonal and perpendicular******************
	//////going through tube's axis ray point
	// TC21: the ray is perpendicular to the tube from the outside (0 points)
	assertNull(tube.findIntersections(new Ray(new Point(0,2,2), new Vector(0, 0, 1))),
		"Ray's perpendicular to tube");
	// TC22: the ray is orthogonal to the tube (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0,2,1), new Vector(0, 0, 1))),
			"Ray's orthogonal to tube");
		
	// TC23: the ray is perpendicular to the tube from the inside (1 points)
	Point p4 = new Point(0, 2, 1);
	List<Point> result3 = tube.findIntersections(new Ray(new Point(0, 2, 0.5), new Vector(0, 0, 1)));
	assertEquals(1, result3.size(), "Wrong number of points");
	assertEquals(p4, result3.get(0), "Ray crosses tube from inside incorrectly.");
		
	// TC24: the ray is orthogonal from the tube's axis ray point (1 points)
	List<Point> result4 = tube.findIntersections(new Ray(new Point(0, 2, 0), new Vector(0, 0, 1)));
	assertEquals(1, result4.size(), "Wrong number of points");
	assertEquals(p4, result4.get(0), "Ray crosses tube from inside incorrectly.");
	
	// TC25: the ray is perpendicular to the tube from the inside before axis ray point (1 points)
	List<Point> result5 = tube.findIntersections(new Ray(new Point(0, 2, -0.5), new Vector(0, 0, 1)));
	assertEquals(1, result5.size(), "Wrong number of points");
	assertEquals(p4, result5.get(0), "Ray crosses tube from inside incorrectly.");
	
	// TC26: the ray is orthogonal to the tube (1 point)
	assertNull(tube.findIntersections(new Ray(new Point(0,2,-1), new Vector(0, 0, 1))),
		"Ray's orthogonal to tube");
	
	// TC27: the ray is perpendicular to the tube from the inside before axis ray point (2 points)
	Point p5 = new Point(0,2,-1);
	List<Point> result6 = tube.findIntersections(new Ray(new Point(0, 2, -2), new Vector(0, 0, 1)));
	assertEquals(2, result6.size(), "Wrong number of points");
	if (result6.get(0).getZ() > result6.get(1).getZ())
	    result6 = List.of(result6.get(1), result6.get(0));
	assertEquals(List.of(p4, p5), result6.get(0), "Ray crosses through the tube incorrectly.");

	//*******tangent to tube*********
	// TC31: before tangent point (0 points)
	assertNull(tube.findIntersections(new Ray(new Point(1.5, 0, 1), new Vector(1.5, 4, 0))),
		"Ray's tangent to tube, before tangent point");
	
	// TC32: at tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(2.25, 2, 1), new Vector(1.5, 4, 0))),
			"Ray's tangent to tube, at tangent point");
		
	// TC33: after tangent point (0 points)
	assertNull(tube.findIntersections(new Ray(new Point(3, 4, 1), new Vector(1.5, 4, 0))),
		"Ray's tangent to tube, after tangent point");
	
	//*******through axis ray*******
	//TC41: outside of tube, facing the tube (2 points)
	Point p6 = new Point(1.097631072937818, 1.292893218813453, 0.707106781186547);
	Point p7 = new Point(1.569035593728849, 2.707106781186548, -0.707106781186548);
	List<Point> result7 = tube.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 3, -3)));
	assertEquals(2, result7.size(), "Wrong number of points");
	assertEquals(List.of(p6, p7), result7.get(0), "Ray crosses through the tube incorrectly.");
	
	// TC42: on tube surface, facing the tube (1 point)
	List<Point> result8 = tube.findIntersections(new Ray(p6, new Vector(1, 3, -3)));
	assertEquals(1, result8.size(), "Wrong number of points");
	assertEquals(p7, result8.get(0), "Ray crosses through the tube incorrectly.");

	// TC43: inside tube, before tube axis ray (1 point)
	List<Point> result9 = tube.findIntersections(new Ray(new Point(1.238240344551198, 1.714721033653591, 0.285278966346409), new Vector(1, 3, -3)));
	assertEquals(1, result9.size(), "Wrong number of points");
	assertEquals(p7, result9.get(0), "Ray crosses through the tube incorrectly.");
	
	// TC44: on tube axis ray (1 point)
	List<Point> result10 = tube.findIntersections(
		new Ray(new Point(1.333333333333334, 2, 0), new Vector(1, 3, -3)));
	assertEquals(1, result10.size(), "Wrong number of points");
	assertEquals(p7, result10.get(0), "Ray crosses through the tube incorrectly.");
	
	// TC44: inside tube after axis ray (1 point)
	List<Point> result11 = tube
		.findIntersections(new Ray(new Point(1.465399710013591, 2.396199130040771, -0.396199130040771), new Vector(1, 3, -3)));
	assertEquals(1, result11.size(), "Wrong number of points");
	assertEquals(p7, result11.get(0), "Ray crosses through the tube incorrectly.");
	
	//TC45: on tube surface, facing the outside (0 points)
	assertNull(tube.findIntersections(new Ray(p7, new Vector(1,3,-3))),
		"Ray's point on tube surface");
	
	// TC46: outside the tube, facing the outside (0 points)
	assertNull(tube.findIntersections(new Ray(new Point(2, 4, -2), new Vector(1, 3, -3))), "Ray's point outside the tube");
	
	// *******through axis ray and point*******
	// TC51: outside of tube, facing the tube (2 points)
	Point p9 = new Point(-0.235702260395515, 1.292893218813453, 0.707106781186547);
	Point p10 = new Point(0.235702260395516,2.707106781186548,-0.707106781186548);
	List<Point> result12 = tube.findIntersections(new Ray(new Point(-0.33333333333333, 1, 1), new Vector(1, 3, -3)));
	assertEquals(2, result12.size(), "Wrong number of points");
	assertEquals(List.of(p9, p10), result12.get(0), "Ray crosses through the tube incorrectly.");

	// TC52: on tube surface, facing the tube (1 point)
	List<Point> result13 = tube.findIntersections(new Ray(p9, new Vector(1, 3, -3)));
	assertEquals(1, result13.size(), "Wrong number of points");
	assertEquals(p10, result13.get(0), "Ray crosses through the tube incorrectly.");

	// TC53: inside tube, before tube axis ray (1 point)
	List<Point> result14 = tube.findIntersections(
		new Ray(new Point(-0.095092988781802, 1.714721033653591, 0.285278966346409), new Vector(1, 3, -3)));
	assertEquals(1, result14.size(), "Wrong number of points");
	assertEquals(p10, result14.get(0), "Ray crosses through the tube incorrectly.");

	// TC54: on tube axis ray (1 point)
	List<Point> result15 = tube
		.findIntersections(new Ray(new Point(0, 2, 0), new Vector(1, 3, -3)));
	assertEquals(1, result15.size(), "Wrong number of points");
	assertEquals(p10, result15.get(0), "Ray crosses through the tube incorrectly.");

	// TC54: inside tube after axis ray (1 point)
	List<Point> result16 = tube.findIntersections(
		new Ray(new Point(0.132066376680591, 2.396199130040771, -0.396199130040771), new Vector(1, 3, -3)));
	assertEquals(1, result16.size(), "Wrong number of points");
	assertEquals(p10, result16.get(0), "Ray crosses through the tube incorrectly.");

	// TC55: on tube surface, facing the outside (0 points)
	assertNull(tube.findIntersections(new Ray(p10, new Vector(1, 3, -3))), "Ray's point on tube surface");

	// TC56: outside the tube, facing the outside (0 points)
	assertNull(tube.findIntersections(new Ray(new Point(0.66666666667, 4, -2), new Vector(1, 3, -3))),
		"Ray's point outside the tube");
	
	//*********surface general bva**********
	// TC61: the ray starts from tube surface, intersects with the tube (1 point)
	//using p2 and p3
	List<Point> result20 = tube.findIntersections(new Ray(p2, new Vector(1, 2.5, 1)));
	assertEquals(1, result20.size(), "Wrong number of points");
	assertEquals(p3, result20.get(0), "Ray crosses tube from inside incorrectly.");
	
	// TC62: the ray starts from tube surface, doesn't intersect with the tube (0 point)
	assertNull(tube.findIntersections(new Ray(p3, new Vector(1, 2.5, 1))),
			"Ray's point outside the tube");
		
    }
}
