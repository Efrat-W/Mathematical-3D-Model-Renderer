package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;

/**
 * Unit tests for integration
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class IntegrationTests {

	int numIntersections(Intersectable shape, Camera c) {
		int num = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				List<Point> list = shape.findIntersections(c.constructRay(3, 3, j, i));
				if (list != null)
					num += list.size();
			}
		return num;
	}

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int) and
	 * geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	void sphereIntegrationTest() {
		Camera cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPSize(3, 3)
				.setVPDistance(1);
		// TC01: 2 intersections
		assertEquals(numIntersections(new Sphere(new Point(0, 0, -2.5), 1), cam), 2, "wrong number of intersections");
		// TC02: 18 intersections
		assertEquals(numIntersections(new Sphere(new Point(0, 0, -2.5), 2.5), cam), 18,
				"wrong number of intersections");
		// TC03: 10 intersections
		assertEquals(numIntersections(new Sphere(new Point(0, 0, -2), 2), cam), 10, "wrong number of intersections");
		// TC04: 9 intersections
		assertEquals(numIntersections(new Sphere(new Point(0, 0, 0), 4), cam), 9, "wrong number of intersections");
		// TC05: 0 intersections
		assertEquals(numIntersections(new Sphere(new Point(0, 0, 1.5), 0.5), cam), 0, "wrong number of intersections");

	}

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int) and
	 * geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	void planeIntegrationTest() {
		Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPSize(3, 3)
				.setVPDistance(1);
		// TC01: 9 intersections
		assertEquals(numIntersections(new Plane(new Point(0, 0, -2.5), new Vector(0, 0, -1)), cam), 9,
				"wrong number of intersections");
		// TC02: 9 intersections
		assertEquals(numIntersections(new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, -1, -2.9)), cam),
				9, "wrong number of intersections");
		// TC03: 6 intersections
		assertEquals(numIntersections(new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, -1, -1.1)), cam),
				6, "wrong number of intersections");

	}

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int) and
	 * geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	@Test
	void triangleIntegrationTest() {
		Camera cam = new Camera(new Point(0, 0, -0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPSize(3, 3)
				.setVPDistance(1);
		// TC01: 1 intersection
		assertEquals(
				numIntersections(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), cam),
				1, "wrong number of intersections");
		// TC02: 2 intersections
		assertEquals(
				numIntersections(new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), cam),
				2, "wrong number of intersections");

	}

}
