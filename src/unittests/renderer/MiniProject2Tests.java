package unittests.renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

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

	
	/**
	 * test of cbr+bvh buildings and car mirror
	 */
	@Test
	void MiniProject2Test() {
		Color grayColor = new Color(150, 150, 150);

		Scene scene = new Scene("Test scene").setBackground(grayColor) //
			.setCBR()//
		;
		Material material1 = new Material().setKd(0.5).setKs(0.1).setShininess(150);
		Material material2 = new Material().setKd(0.2).setKs(0.8).setShininess(250).setKr(0.5);

		double margin = 1;
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
		double win_h = width / (num_windows) - 2*margin;
		double tmpy = y2;
		double tmpz = z1;
		Geometries g = new Geometries();
		while (tmpy < y1) {

			g.add(new Polygon(new Point(299, tmpy, z1), new Point(299, tmpy + margin, z1), new Point(299, tmpy + margin, z2),
					new Point(299, tmpy, z2)).setEmission(new Color(BLACK)).setMaterial(material1));
			tmpy += win_h + margin;// )
		}
		num_windows = 50;
		margin = 1.5;
		double win_w = width / (num_windows) - 2*margin;

		while (tmpz < z2) {

			g.add(new Polygon(new Point(299, y2, tmpz), new Point(299, y2, tmpz + margin), new Point(299, y1, tmpz + margin),
					new Point(299, y1, tmpz)).setEmission(new Color(BLACK)).setMaterial(material1));
			tmpz += win_w + margin;
		}
		
		Point p5 = new Point(1200, y1, z1);
		Point p6 = new Point(1200, y1, z2);
		Point p7 = new Point(1200, y2, z1);
		Point p8 = new Point(1200, y2, z2);
		Geometries building1 = new Geometries(
				new Polygon(p1, p2, p4, p3).setEmission(new Color(BLACK)).setMaterial(material2),//
				new Polygon(p2, p4, p6, p5).setEmission(new Color(BLACK)).setMaterial(material2),
				new Polygon(p1, p7, p8, p3).setEmission(new Color(BLACK)).setMaterial(material2));//


		y1 = 1000;
		y2 = 100;
		width = y1 - y2;
		margin = 1;
		z1 = -800;
		double x = -100;
		p1 = new Point(x, y2, z1);
		p2 = new Point(x, y1, z1);
		p3 = new Point(x, y2, z2);
		p4 = new Point(x, y1, z2);

		tmpy = y2;
		tmpz = z1;

		while (tmpy < y1) {

			g.add(new Polygon(new Point(x + margin, tmpy, z1), new Point(x + margin, tmpy + margin, z1),
					new Point(x + margin, tmpy + margin, z2), new Point(x + margin, tmpy, z2)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpy += win_h + margin;
		}

		margin = 1.5;
		while (tmpz < z2) {

			g.add(new Polygon(new Point(x + 1, y2, tmpz), new Point(x + 1, y2, tmpz + margin),
					new Point(x + 1, y1, tmpz + margin), new Point(x + 1, y1, tmpz)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpz += win_w + margin;
		}

		p5 = new Point(-1060, y2 - 1, z1);
		p6 = new Point(-1060, y2 - 1, z2);
		Geometries building2 = new Geometries(new Polygon(p1, p2, p4, p3).setMaterial(material2).setEmission(new Color(BLACK))//
				, new Polygon(p1, p3, p6, p5).setMaterial(material2).setEmission(new Color(BLACK))
		);

		double tmpx = -1060;
		margin = 1;
		double widthx = (x + 1060) / 30 - 2*margin;
		tmpz = z1;

		while (tmpx < x) {

			g.add(new Polygon(new Point(tmpx, y2 - 2, z1), new Point(tmpx - margin, y2 - 2, z1),
					new Point(tmpx - margin, y2 - 2, z2), new Point(tmpx, y2 - 2, z2)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpx += widthx + 1;
		}

		margin = 1.5;
		while (tmpz < z2) {

			g.add(new Polygon(new Point(x, y2 - 2, tmpz), new Point(x, y2 - 2, tmpz + margin),
					new Point(-1060, y2 - 2, tmpz + margin), new Point(-1060, y2 - 2, tmpz)).setEmission(new Color(GRAY))
					.setMaterial(material1));
			tmpz += win_w + margin;
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
				, new Polygon(p1, p3, p6, p5).setMaterial(material2).setEmission(blueColor)
		);

		tmpy = y2;
		tmpz = z1;
		margin = 1;
		
		while (tmpy < y1) {

			g.add(new Polygon(new Point(x - 1, tmpy, z1), new Point(x - 1, tmpy + margin, z1),
					new Point(x - 1, tmpy + margin, z2), new Point(x - 1, tmpy, z2)).setEmission(new Color(GRAY)));
			tmpy += win_h + 1;
		}

		margin = 1.5;
		while (tmpz < z2) {

			g.add(new Polygon(new Point(x - 1, y2, tmpz), new Point(x - 1, y2, tmpz + margin),
					new Point(x - 1, y1, tmpz + margin), new Point(x - 1, y1, tmpz)).setEmission(new Color(GRAY)));
			tmpz += win_w + margin;
		}
		
		margin = 1;
		tmpx = 700;
		widthx = (x + 700) / 30 - 2*margin;
		tmpz = z1;

		while (tmpx > x) {

			g.add(new Polygon(new Point(tmpx, y2 - 2, z1), new Point(tmpx + margin, y2 - 2, z1),
					new Point(tmpx + margin, y2 - 2, z2), new Point(tmpx, y2 - 2, z2)).setEmission(new Color(GRAY)));
			tmpx -= widthx + margin;
		}
		margin = 1.5;
		while (tmpz < z2) {

			g.add(new Polygon(new Point(x, y2 - 2, tmpz), new Point(x, y2 - 2, tmpz + margin),
					new Point(700, y2 - 2, tmpz + margin), new Point(700, y2 - 2, tmpz)).setEmission(new Color(GRAY)));
			tmpz += win_w + margin;
		}

		x=-50;
		z1=-700;
		z2=800;
		p1=new Point(x, -400, z1);
		p2=new Point(-200, -300, z1);
		p3=new Point(x, -400, z2);
		p4=new Point(-200, -300, z2);
		p5=new Point(0, -500, z1);
		p6=new Point(0, -500, z2);
		p7=new Point(-270, -350, z1);
		p8=new Point(-270, -350, z2);
		
		tmpx = x;
		tmpz = z1;
		widthx = (x + 200) / 10 - 2;

		while (tmpx > -200) {

			g.add(new Polygon(new Point(tmpx, y1(tmpx), z1), new Point(tmpx- 1, y1(tmpx-1), z1),
					new Point(tmpx- 1, y1(tmpx-1), z2), new Point(tmpx, y1(tmpx), z2)).setEmission(new Color(GRAY)));
			tmpx -= widthx - 1;
		}
		margin = 1;
		tmpx = x;

		while (tmpx < 0) {

			g.add(new Polygon(new Point(tmpx, y2(tmpx), z1), new Point(tmpx- margin, y2(tmpx-margin), z1),
					new Point(tmpx- margin, y2(tmpx-margin), z2), new Point(tmpx, y2(tmpx), z2)).setEmission(new Color(GRAY)));
			tmpx += widthx + 1;
		}
		
		tmpx = -200;

		while (tmpx > -270) {

			g.add(new Polygon(new Point(tmpx, y3(tmpx), z1), new Point(tmpx- margin, y3(tmpx-margin), z1),
					new Point(tmpx- margin, y3(tmpx-margin), z2), new Point(tmpx, y3(tmpx), z2)).setEmission(new Color(GRAY)));
			tmpx -= widthx - margin;
		}

		
		Geometries building4 = new Geometries(//
				new Polygon(p1, p2, p4, p3).setMaterial(material2).setEmission(blueColor),//
			
			new Polygon(p1, p5, p6, p3).setMaterial(material2).setEmission(blueColor),
		new Polygon(p2, p7, p8, p4).setMaterial(material2).setEmission(blueColor));


		z1=-6000;
		scene.geometries.add(building1, g, building2, building3, building4, new Polygon(new Point(z1,z1,z1),//
				new Point(z1,-z1,z1),new Point(-z1,-z1,z1),new Point(-z1,z1,z1))//
				.setEmission(new Color(150, 190, 230))//
				.setMaterial(new Material().setKd(0.8).setKs(0.1)));

		scene.lights.add(new PointLight(new Color(RED), new Point(-1000, -1000, -200)).setKl(0.00001).setKq(0.0000001));
		scene.lights.add(new PointLight(new Color(255, 153, 0), new Point(-1000, -1000, -200)).setKl(0.00001).setKq(0.0000001));
		scene.lights.add(new PointLight(new Color(YELLOW), new Point(-1000, -1000, -200)).setKl(0.0001).setKq(0.00001));
		scene.lights.add(new PointLight(new Color(MAGENTA), new Point(-1000, -1000, -200)).setKl(0.0001).setKq(0.000001));
		scene.lights.add(new PointLight(new Color(GREEN), new Point(-1000, -1000, -200)).setKl(0.0001).setKq(0.0000001));

		//scene.lights.add(new PointLight(new Color(RED), new Point(-102, 101, -820)).setKl(0.0001).setKq(0.000001));
		

		scene.setBVH();

		new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(1000, 1500) //
				.setVPDistance(1000) //
				.setImageWriter(new ImageWriter("miniProject2", 5000, 5000)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setDebugPrint(0.2) //
				.setMultiThreading(4) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * help function for creating windows fot building4
	 * @param x parameter for function
	 * @return y value of the y parameter
	 */
	private double y1(double x) {
		double y = (-2.0 / 3.0) * x - 433.33333333333333 + 1.0;
		return y;
	}
	/**
	 * help function for creating windows fot building4
	 * @param x parameter for function
	 * @return y value of the y parameter
	 */
	private double y2(double x) {
		double y = -2.0 * x - 500.0 + 1.0;
		return y;
	}
	/**
	 * help function for creating windows fot building4
	 * @param x parameter for function
	 * @return y value of the y parameter
	 */
	private double y3(double x) {
		double y = (5.0 / 7.0) * x -157.0-(1.0/7.0) + 1.0;
		return y;
	}
}
