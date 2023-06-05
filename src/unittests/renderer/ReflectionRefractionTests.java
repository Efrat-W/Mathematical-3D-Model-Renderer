
package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private static final int SHININESS = 301;

	private final Material material = new Material().setKd(0.3).setKs(0.7).setShininess(SHININESS).setKr(0);

	private Scene scene = new Scene("Test scene");

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
						.setMaterial(
								new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a tube lighted by a spot light */
	@Test
	public void nicePicture() {
		Camera camera = new Camera(new Point(0, 0, -500), new Vector(0, 0, 1), new Vector(0, 1, 0)) //
				.setVPSize(500, 500).setVPDistance(1000);

		Point b = new Point(0, 12.5, -20);
		Point c = new Point(25, 30, 10);
		Point d = new Point(-25, 30, 10);
		Point e = new Point(25, -5, 0);
		Point f = new Point(-25, -5, 0);
		Point g = new Point(0, -22.5, 20);
		Color color1 = new Color(153, 0, 76);
		Color color2 = new Color(25, 150, 170);
		scene.geometries.add(
				new Plane(new Point(0, 0, 200), new Vector(0.3, 0, 1)).setEmission(new Color(150, 150, 150))
						.setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(100).setKr(0.2)), //
				new Sphere(new Point(0, 7, 0), 45).setEmission(new Color(BLACK)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.9).setShininess(200).setKt(0.9)),

				new Triangle(b, f, g).setMaterial(material).setEmission(color1),

				new Triangle(b, e, g).setMaterial(material).setEmission(color2),

				new Triangle(b, d, f).setMaterial(material).setEmission(color1),

				new Polygon(b, c, e).setMaterial(material).setEmission(color2),

				new Tube(new Ray(new Point(-80, 80, -20), new Vector(0, -120, 10)), 5).setEmission(color1)
						.setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.5)),

				new Tube(new Ray(new Point(-80, -80, -20), new Vector(-120, 0, -10)), 5).setEmission(color1.reduce(3))
						.setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.5)),

				new Tube(new Ray(new Point(80, -80, -20), new Vector(0, 120, 10)), 5).setEmission(color2)
						.setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.5)),

				new Tube(new Ray(new Point(80, 80, -20), new Vector(120, 0, -10)), 5).setEmission(color2.reduce(3))
						.setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.5)));
		
		scene.lights.add(new SpotLight(new Color(WHITE), new Point(0, 150, -60), new Vector(0, -150, 60)) //
				.setNarrowBeam(3).setKl(0.0004).setKq(0.000001));
		
		camera.setImageWriter(new ImageWriter("refractionTube", 5000, 5000)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
}
