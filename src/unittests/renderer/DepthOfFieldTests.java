package unittests.renderer;

import org.junit.jupiter.api.Test;
import scene.*;
import geometries.*;
import primitives.*;

import static java.awt.Color.*;
import lighting.*;
import renderer.*;

/**
 * Unit tests for depth of field
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class DepthOfFieldTests {

	/* test DepthofFoield, creating a picture of balls in a line*/
	@Test
	void testDepthOfField1() {

		Scene scene = new Scene("Test scene");

		Geometry[] spheres = new Sphere[10];

		for (int i = 0; i < 10; i++) {
			spheres[i] = new Sphere(new Point(10 - i * 8, 0, 800 - 100 * i), 3) //
					.setEmission(new Color(BLUE).reduce(2)) //
					.setMaterial(new Material().setKd(new Double3(0.3)).setKs(new Double3(0.2)).setShininess(300)
							.setKr(new Double3(0.5)));
		}
		Geometry polygon = new Polygon(new Point(100, -50, 1000), new Point(-100, -50, 1000),
				new Point(-100, -50, -100), new Point(100, -50, -100)).setEmission(new Color(gray))
				.setMaterial(new Material().setKd(new Double3(0.2)).setKs(new Double3(0.3)).setShininess(300)
						.setKr(new Double3(0.5)));

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150) //
				.setVPDistance(1000)

				// set the DoF.
				.setDoFFlag(true).setNumOfPoints(100).setFPDistance(500).setApertureSize(1);

		// scene.geometries.add(sphere1, sphere2);
		scene.geometries.add(spheres);
		scene.geometries.add(polygon);
		scene.lights.add(new DirectionalLight(new Color(YELLOW), new Vector(1, -1, -0.5)));
		scene.lights
				.add(new SpotLight(new Color(WHITE), new Point(100, 100, 800), new Vector(-1, -1, 0))
						.setNarrowBeam(10));

		camera.setImageWriter(new ImageWriter("lightSphereDirectionalDepthOfFieldTesting1", 500,500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage(); //

	}
	
	
	/* test DepthofFoield, creating a picture of balls in a line*/
	@Test
	void testDepthOfField2() {

		Scene scene = new Scene("Test scene");

		
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(500, 500) //
				.setVPDistance(1000)

				// set the DoF.
				.setDoFFlag(true).setNumOfPoints(100).setFPDistance(500).setApertureSize(1);

		scene.geometries.add(//
				new Plane(new Point(0, 0, -200), new Vector(0.1, 0, 1)).setEmission(new Color(GREEN)),
				new Sphere(new Point(0, 7, 500), 30).setEmission(new Color(BLUE)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.9).setShininess(200).setKt(0.9))
				);
		// scene.geometries.add(sphere1, sphere2);
		scene.lights.add(new DirectionalLight(new Color(YELLOW), new Vector(1, -1, -0.5)));
		scene.lights
				.add(new SpotLight(new Color(WHITE), new Point(100, 100, 800), new Vector(-1, -1, 0))
						.setNarrowBeam(10));

		camera.setImageWriter(new ImageWriter("lightSphereDirectionalDepthOfFieldTesting2", 100,100)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage(); //

	}
}
