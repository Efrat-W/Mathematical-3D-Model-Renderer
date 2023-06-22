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

	/* test DepthofFoield, creating a picture of flowers in a the nature */
	@Test
	void testDepthOfField2() {

		Scene scene = new Scene("Test scene");

		Camera camera = new Camera(new Point(3, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(500, 500) //
				.setVPDistance(1000)

				// set the DoF.
				.setDoFFlag(true).setNumOfPoints(100).setFPDistance(100).setApertureSize(1);

		scene.geometries.add(//
				new Plane(new Point(0, -20, -400), new Vector(0, 1, 0.005)).setEmission(new Color(GREEN).reduce(2))//
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(200).setKt(0.5)),
				new Plane(new Point(0, 200, 400), new Vector(0, 1, -0.005)).setEmission(new Color(42, 227, 240))//
						.setMaterial(new Material().setKd(0.5).setKs(0.9).setShininess(200).setKt(0.5)),

				// flowers:
				new Sphere(new Point(0, -2, 900), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(-7, -1, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(7, -1, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(-0.5, 3, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(-5, -7, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(5, -7, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Triangle(new Point(0.5, -30, 900), new Point(-0.5, -30, 900), new Point(0., 0, 880))
						.setEmission(new Color(GREEN)),
				new Sphere(new Point(-4, -20, 900), 4).setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.7).setKs(0.4).setShininess(200).setKr(0.1)),

				new Sphere(new Point(-30, -2, 800), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-37, -1, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-23, -1, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-30.5, 3, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-35, -7, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-25, -7, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Triangle(new Point(-29.5, -30, 800), new Point(-30.5, -30, 800), new Point(-30, 0, 780))
						.setEmission(new Color(GREEN)),
				new Sphere(new Point(-34, -20, 801), 4).setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),

				new Sphere(new Point(50, -2, 700), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(57, -1, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(43, -1, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(50.5, 3, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(55, -7, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(45, -7, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Triangle(new Point(49.5, -30, 700), new Point(50.5, -30, 700), new Point(50, 0, 680))
						.setEmission(new Color(GREEN)),
				new Sphere(new Point(54, -20, 701), 4).setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),

				new Sphere(new Point(20, -12, 930), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(27, -11, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(13, -11, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(20.5, -7, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(15, -17, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(15, -17, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),

//				//clouds
				new Sphere(new Point(-11, 40, 805), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(-1, 50, 790), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.7)),
				new Sphere(new Point(9, 42, 795), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.9)),

				new Sphere(new Point(-80, 80, 500), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(-90, 90, 490), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),
				new Sphere(new Point(-100, 80, 495), 12).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),

				new Sphere(new Point(60, 80, 650), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(70, 90, 640), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),
				new Sphere(new Point(80, 80, 645), 12).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),

				new Sphere(new Point(80, 80, 300), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(90, 90, 290), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),
				new Sphere(new Point(100, 80, 295), 12).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5))

		);

		scene.geometries.setBVH();
		scene.lights.add(new DirectionalLight(new Color(YELLOW), new Vector(0, -1, 0)));
		scene.lights
				.add(new SpotLight(new Color(WHITE), new Point(100, 100, 400), new Vector(-1, -1, -1)).setKq(0.000001));

		camera.setImageWriter(new ImageWriter("lightSphereDirectionalDepthOfFieldTesting", 300, 300)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage(); //

	}

	@Test
	void testDepthOfField3() {

		Scene scene = new Scene("Test scene");

		Camera camera = new Camera(new Point(3, 0, 300), new Vector(0, 0, 1), new Vector(0, 1, 0)) //
				.setVPSize(500, 500) //
				.setVPDistance(1000)

				// set the DoF.
				.setDoFFlag(true).setNumOfPoints(100).setFPDistance(400).setApertureSize(1);

		scene.geometries.add(//
				new Plane(new Point(0, -20, -400), new Vector(0, 1, 0.005)).setEmission(new Color(GREEN).reduce(2))//
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(200).setKt(0.5)),
				new Plane(new Point(0, 200, 400), new Vector(0, 1, -0.005)).setEmission(new Color(42, 227, 240))//
						.setMaterial(new Material().setKd(0.5).setKs(0.9).setShininess(200).setKt(0.5)),

				// flowers:

				new Sphere(new Point(0, -2, 900), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(-7, -1, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(7, -1, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(-0.5, 3, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(-5, -7, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Sphere(new Point(5, -7, 900), 6).setEmission(new Color(PINK)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKr(0.01)),
				new Triangle(new Point(0.5, -30, 900), new Point(-0.5, -30, 900), new Point(0., 0, 880))
						.setEmission(new Color(GREEN)),
				new Sphere(new Point(-4, -20, 900), 4).setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.7).setKs(0.4).setShininess(200).setKr(0.1)),

				new Sphere(new Point(-30, -2, 800), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-37, -1, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-23, -1, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-30.5, 3, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-35, -7, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(-25, -7, 800), 6).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Triangle(new Point(-29.5, -30, 800), new Point(-30.5, -30, 800), new Point(-30, 0, 780))
						.setEmission(new Color(GREEN)),
				new Sphere(new Point(-34, -20, 801), 4).setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),

				new Sphere(new Point(50, -2, 700), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(57, -1, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(43, -1, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(50.5, 3, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(55, -7, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(45, -7, 700), 6).setEmission(new Color(magenta)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Triangle(new Point(49.5, -30, 700), new Point(50.5, -30, 700), new Point(50, 0, 680))
						.setEmission(new Color(GREEN)),
				new Sphere(new Point(54, -20, 701), 4).setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),

				new Sphere(new Point(20, -12, 930), 5).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(27, -11, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(13, -11, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(20.5, -7, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(15, -17, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),
				new Sphere(new Point(15, -17, 930), 6).setEmission(new Color(255, 153, 0)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1)),

				// clouds

				new Sphere(new Point(-11, 40, 805), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(-1, 50, 790), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.7)),
				new Sphere(new Point(9, 42, 795), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.9)),

				new Sphere(new Point(-80, 80, 500), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(-90, 90, 490), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),
				new Sphere(new Point(-100, 80, 495), 12).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),

				new Sphere(new Point(60, 80, 650), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(70, 90, 640), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),
				new Sphere(new Point(80, 80, 645), 12).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),

				new Sphere(new Point(80, 80, 300), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.8)),
				new Sphere(new Point(90, 90, 290), 10).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5)),
				new Sphere(new Point(100, 80, 295), 12).setEmission(new Color(white)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(200).setKt(0.5))

		);
		// scene.geometries.add(sphere1, sphere2);
		scene.lights.add(new DirectionalLight(new Color(YELLOW), new Vector(0, -1, 0)));
		scene.lights
				.add(new SpotLight(new Color(WHITE), new Point(100, 100, 400), new Vector(-1, -1, -1)).setKq(0.000001));

		camera.setImageWriter(new ImageWriter("lightSphereDirectionalDepthOfFieldTesting2", 100, 100)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage(); //

	}

	@Test
	void test() {
		Color yellowColor = new Color(YELLOW);
		Material sphereMaterial = new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1);

		Scene scene = new Scene("Test scene") //
				.setCBR() //
				;
		for (int i = -100; i <= 100; i += 5)
			for (int j = -50; j <= 50; j += 5)
				scene.geometries.add(
						new Sphere(new Point(i, j, 800), 0.5).setEmission(yellowColor).setMaterial(sphereMaterial));

		scene.lights.add(new DirectionalLight(new Color(YELLOW), new Vector(0, 1, 0)));
		scene.lights
				.add(new SpotLight(new Color(WHITE), new Point(100, 100, 1000), new Vector(1, -1, 1)).setKq(0.000001));

		scene.setBVH();

		new Camera(new Point(3, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(1000, 1000) //
				.setVPDistance(1000) //
				.setImageWriter(new ImageWriter("lightSphereDirectionalDepthOfFieldTesting3", 2000, 2000)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage(); //
	}

}
