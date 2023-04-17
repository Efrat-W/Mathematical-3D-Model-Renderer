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
 * Unit tests for geometries.Sphere class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getRadius()}.
     */
    @Test
    void testGetRadius() {
	// ============ Equivalence Partitions Tests ==============
	// TC01:
	double radius = 7;
	Sphere s = new Sphere(new Point(1, 2, 3), radius);
	assertEquals(radius, s.getRadius(), "getRadius() does not return the correct radius value.");
	//TC02: negative radius
	//s = new Sphere(new Point(1, 2, 3), -3);
	//assertThrow
    }

    /**
     * Test method for {@link geometries.Sphere#getPoint()}.
     */
    @Test
    void testGetPoint() {
	// TC01:
	Point p = new Point(1, 2, 3);
	Sphere s = new Sphere(p, 7);
	assertEquals(p, s.getPoint(), "getPoint() does not return the center point.");
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
	// ============ Equivalence Partitions Tests ==============
	// TC01: 
	Point center = new Point (0,0,0);
	double radius = 1;
	Sphere s = new Sphere(center, radius);
	Point tangentP = new Point (1, 0, 0);
	Vector n = tangentP.subtract(center).normalize();
	assertEquals(n, s.getNormal(tangentP), "getNormal() is wrong.");
	//TC02:
	Vector result = s.getNormal(new Point(1, 0, 0));
	// ensure |result| = 1
	assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
	
    }

}