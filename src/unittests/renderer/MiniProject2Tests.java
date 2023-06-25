package unittests.renderer;

import org.junit.jupiter.api.Test;
import org.junit.runners.model.FrameworkField;

import static java.awt.Color.*;

import java.util.Iterator;

import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

/**
 * Unit tests for BVH
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class MiniProject2Tests {

	private Geometries buildingMaker(int x, int y, int z, Material matB, Material matW) {

		final int win_h = 30; // building window's height
		final int win_w = 25; // building window's width
		final int margin = 10;
		final int width = win_w * 10 + margin * 12;
		final int height = win_h * 20 + margin * 22; // random height

		var parts = new Geometries();
		parts.add( //
				new Polygon(new Point(x, y, z), //
						new Point(x + width, y, z), new Point(x + width, y + height, z), //
						new Point(x, y + height, z)).setMaterial(matB).setEmission(new Color(GREEN)) // front face
		// new Polygon(), //left side
		// new Polygon(), // right side
		);

		int tmpy = y + margin;
		while (tmpy < y + height - 2 * margin) {
			int tmpx = x + margin;
			while (tmpx < x + width - 2 * margin) {
				parts.add(new Polygon(//
						new Point(tmpx, tmpy, z + 0.002), new Point(tmpx + win_w, tmpy, z + 0.002),
						new Point(tmpx + win_w, tmpy + win_h, z + 0.002), new Point(tmpx, tmpy + win_h, z + 0.002)//
				).setMaterial(matW).setEmission(new Color(BLUE)));
				tmpx += win_w + margin / 4;
			}
			tmpy += win_h + margin / 2;
		}
		return parts;

	}

	/**
	 * test of cbr+bvh buildings and car mirror
	 */
	@Test
	void MiniProject2Test() {

		Scene scene = new Scene("Test scene").setBackground(new Color(110, 190, 230)) //
				.setCBR()//
		;
		Material material1 = new Material().setKd(0.5).setKs(0.1).setShininess(150);
		Material material2 = new Material().setKd(0.2).setKs(0.6).setShininess(250).setKr(0.4);

		final int margin = 1;
		// final int width = win_w *10 +margin *12;
		// final int height = win_h*20+margin*22; //random height
		double floor = -500;
		double z1 = -1000;
		double z2 = 800;

		double y1 = 150;
		double y2 = -400;
		double width = y1 - y2;
		Point p1 = new Point(300, y2, z1);
		Point p2 = new Point(300, y1, z1);
		Point p3 = new Point(300, y2, z2);
		Point p4 = new Point(300, y1, z2);

		int num_windows = 20;
		double win_h = width / (num_windows) - 2;
		double tmpy = y2;
		double tmpz = z1;
		Geometries g = new Geometries();
		while (tmpy < y1) {

			g.add(new Polygon(new Point(299, tmpy, z1), new Point(299, tmpy + 1, z1), new Point(299, tmpy + 1, z2),
					new Point(299, tmpy, z2)).setEmission(new Color(BLACK)).setMaterial(material1));
			tmpy += win_h + 1;// )
		}
		num_windows = 50;
		double win_w = width / (num_windows) - 3;

		while (tmpz < z2) {

			g.add(new Polygon(new Point(299, y2, tmpz), new Point(299, y2, tmpz + 1.5), new Point(299, y1, tmpz + 1.5),
					new Point(299, y1, tmpz)).setEmission(new Color(BLACK)).setMaterial(material1));
			tmpz += win_w + 1.5;
//)
		}
		Geometries building1 = new Geometries(
				new Polygon(p1, p2, p4, p3).setEmission(new Color(75, 20, 35)).setMaterial(material2));//

		y1 = 1000;
		y2 = 100;
		width = y1 - y2;
		z1 = -800;
		double x = -100;
		p1 = new Point(x, y2, z1);
		p2 = new Point(x, y1, z1);
		p3 = new Point(x, y2, z2);
		p4 = new Point(x, y1, z2);

		tmpy = y2;
		tmpz = z1;

		while (tmpy < y1) {

			g.add(new Polygon(new Point(x + 1, tmpy, z1), new Point(x + 1, tmpy + 1, z1),
					new Point(x + 1, tmpy + 1, z2), new Point(x + 1, tmpy, z2)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpy += win_h + 1;
//)
		}

		while (tmpz < z2) {

			g.add(new Polygon(new Point(x + 1, y2, tmpz), new Point(x + 1, y2, tmpz + 1.5),
					new Point(x + 1, y1, tmpz + 1.5), new Point(x + 1, y1, tmpz)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpz += win_w + 1.5;
//)
		}

		Point p5 = new Point(-1060, y2 - 1, z1);
		// p5=p1.add(new Vector(-500, 0, 0));
		Point p6 = new Point(-1060, y2 - 1, z2);
		Color grayColor = new Color(150, 150, 150);
		Geometries building2 = new Geometries(new Polygon(p1, p2, p4, p3).setMaterial(material2).setEmission(grayColor)//
				, new Polygon(p1, p3, p6, p5).setMaterial(material2).setEmission(grayColor)// , new
																							// Polygon(p1,p5,p6,p3).setMaterial(material1).setEmission(new
																							// Color(GREEN))//
		);

		double tmpx = -1060;
		double widthx = (x + 1060) / 30 - 2;
		tmpz = z1;

		while (tmpx < x) {

			g.add(new Polygon(new Point(tmpx, y2 - 2, z1), new Point(tmpx - 1, y2 - 2, z1),
					new Point(tmpx - 1, y2 - 2, z2), new Point(tmpx, y2 - 2, z2)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpx += widthx + 1;
//)
		}

		while (tmpz < z2) {

			g.add(new Polygon(new Point(x, y2 - 2, tmpz), new Point(x, y2 - 2, tmpz + 1.5),
					new Point(-1060, y2 - 2, tmpz + 1.5), new Point(-1060, y2 - 2, tmpz)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpz += win_w + 1.5;
//)
		}
		x = 400;
		y1 = 900;
		y2 = 600;
		z1 = -600;
		p1 = new Point(x, y2, z1);
		p2 = new Point(x, y1, z1);
		p3 = new Point(x, y2, z2);
		p4 = new Point(x, y1, z2);
		p5 = new Point(700, y2 - 1, z1);
		p6 = new Point(700, y2 - 1, z2);
		Color blueColor = new Color(0, 204, 204).reduce(5);
		Geometries building3 = new Geometries(new Polygon(p1, p2, p4, p3).setMaterial(material2).setEmission(blueColor)//
				, new Polygon(p1, p3, p6, p5).setMaterial(material2).setEmission(blueColor)// , new
																							// Polygon(p1,p5,p6,p3).setMaterial(material1).setEmission(new
																							// Color(GREEN))//
		);

		tmpy = y2;
		tmpz = z1;

		while (tmpy < y1) {

			g.add(new Polygon(new Point(x - 1, tmpy, z1), new Point(x - 1, tmpy + 1, z1),
					new Point(x - 1, tmpy + 1, z2), new Point(x - 1, tmpy, z2)).setEmission(new Color(GRAY)));
			tmpy += win_h + 1;
//)
		}

		while (tmpz < z2) {

			g.add(new Polygon(new Point(x - 1, y2, tmpz), new Point(x - 1, y2, tmpz + 1.5),
					new Point(x - 1, y1, tmpz + 1.5), new Point(x - 1, y1, tmpz)).setEmission(new Color(GRAY)));
			tmpz += win_w + 1.5;
//)
		}

		tmpx = 700;
		widthx = (x + 700) / 30 - 2;
		tmpz = z1;

		while (tmpx > x) {

			g.add(new Polygon(new Point(tmpx, y2 - 2, z1), new Point(tmpx + 1, y2 - 2, z1),
					new Point(tmpx + 1, y2 - 2, z2), new Point(tmpx, y2 - 2, z2)).setEmission(new Color(GRAY)));
			tmpx -= widthx + 1;
//)
		}

		while (tmpz < z2) {

			g.add(new Polygon(new Point(x, y2 - 2, tmpz), new Point(x, y2 - 2, tmpz + 1.5),
					new Point(700, y2 - 2, tmpz + 1.5), new Point(700, y2 - 2, tmpz)).setEmission(new Color(GRAY)));
			tmpz += win_w + 1.5;
//)
		}

		scene.geometries.add(building1, g, building2, building3);

		// scene.geometries.add(buildingMaker(-100, -300, 10, material1, material2) );
		scene.lights.add(
				new SpotLight(new Color(white), new Point(-500, 500, 0), new Vector(1, 0, 1)).setKl(0.0000000001).setKq(0.000000001));//
		//scene.lights.add(new PointLight(new Color(YELLOW), new Point(-500, 0, 200)).setKl(0.0001).setKq(0.0000000001));
		scene.setBVH();

		new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(1000, 1500) //
				.setVPDistance(1000) //
				.setImageWriter(new ImageWriter("miniProject2", 1000, 1000)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setDebugPrint(0.2) //
				.setMultiThreading(4) //
				.renderImage() //
				.writeToImage(); //
	}
}
