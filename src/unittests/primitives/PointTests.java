/**
 * 
 */
package unittests.primitives;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import primitives.Vector;

class PointTests {

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
	
	// ============ Equivalence Partitions Tests ==============
	//TC 01:
	Point p1 = new Point(1, 2, 3);
	Vector v1 = new Vector(2, 3, 7);
	Vector pr = new Vector(-1, -1, -4);
	asssertEquals(pr, v1.subtract(), "Subtract() is wrong.");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
	Vector v1 = new Vector(1, 2, 3);
	// ============ Equivalence Partitions Tests ==============
	//TC 01:
	Point p = new Point(2, 8, 3);
	Vector pr = new Vectore(3, 10, 6);
	assertEquals(pr, p.add(v1), "Add() is wrong.");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
	// ============ Equivalence Partitions Tests ==============
	//TC 01:
	Point p1 = new Point(2, 8, 3);
	Point p2 = new Point(1, 2, 3);
	assertEquals(37, p1.distanceSquared(p2), 0.0001, "Distance() is wrong.");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
	// ============ Equivalence Partitions Tests ==============
	//TC 01:
	Point p1 = new Point(2, 8, 3);
	Point p2 = new Point(1, 2, 3);
	assertEquals(6.08, p1.distance(p2), 0.0001, "Distance() is wrong.");
    }

}
