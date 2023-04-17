/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for geometries.Plane class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class PlaneTests {

	
	 /**
     * Test method for {@link geometries.Plane#Plane(Point p1, Point p2, Point p3)}.
     */
    @Test
    void testConstructor() {
    	// ============ Equivalence Partitions Tests ==============
    	//TC01: a normal plane:
    	assertDoesNotThrow(()-> new Plane(new Point(0, 0, 0), new Point(0, 1, 0), new Point(1, 0, 0)),//
    			"Error: some exception was thrown in the plane constructor");
    	// =============== Boundary Values Tests ==================
    	//TC11: the 1st and 2nd points coalescing
    	assertThrows(IllegalArgumentException.class,()-> new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(1, 0, 0)),//
    			"Error: two points are coalescing");
    	//TC12: the 3 points are on the same straight
    	assertThrows(IllegalArgumentException.class,()-> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)),//
    			"Error: the points are on the same straight");
    }
    
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
    	// ============ Equivalence Partitions Tests ==============
    	// TC01: There is a simple single test here - using a quad
    	Plane plane = new Plane(new Point(0, 0, 0), new Point(0, 1, 0), new Point(1, 0, 0));
    	// ensure there are no exceptions
    	assertDoesNotThrow(() -> plane.getNormal(new Point(0, 0, 0)), "");
    	// generate the test result
    	Vector result = plane.getNormal(new Point(0, 0, 0));    	
    	// ensure |result| = 1
    	assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
    	// ensure the result is orthogonal to all the edges
    	Point[] pts = { new Point(0, 0, 0), new Point(0, 1, 0), new Point(1, 0, 0)};
    	for (int i = 0; i < 3; ++i)
    	    assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
    		    "plane's normal is not orthogonal to one of the edges");
        }
    


    /**
     * Test method for {@link geometries.Plane#getPoint()}.
     */
    @Test
    void testGetPoint() {
    	Plane plane = new Plane(new Point(0, 0, 0), new Point(0, 1, 0), new Point(1, 0, 0));
    	assertEquals(plane.getPoint(), new Point(0, 0, 0), "point has wrong value");
    }

}
