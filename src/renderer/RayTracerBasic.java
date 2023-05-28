package renderer;

import static primitives.Util.*;
import primitives.*;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import scene.Scene;

/**
 * Class RayTracerBasic is the class representing the RayTracer
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class RayTracerBasic extends RayTracerBase {
	private static final double DELTA = 0.1;

	/**
	 * Ray Tracer constructor by scene
	 * 
	 * @param scene Scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * calculate the diffusive part of Phong module
	 * 
	 * @param material the material of the geometry
	 * @param nl       dot product of the normal and the light direction
	 * @return the diffusivity
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(Math.abs(nl));
	}

	/**
	 * calculate the specular part of Phong module
	 * 
	 * @param material the material of the geometry
	 * @param n        normal
	 * @param l        light direction
	 * @param nl       dot product of the normal and the light direction
	 * @param v        camera direction
	 * @return the specularity
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		Vector r = l.subtract(n.scale(2 * nl));
		double vr = alignZero(v.dotProduct(r));
		return vr >= 0 ? Double3.ZERO : material.kS.scale(Math.pow(-vr, material.nShininess));
	}

	/**
	 * checks if the point is unshaded or not.
	 * 
	 * @param gp the geoPoint that we check
	 * @param l  the light source direction
	 * @param n  the normal at gp
	 * @return boolean if unshaded or not
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource ls) {
		Vector lightDirection = l.scale(-1);
		Vector epsVector = n.scale(alignZero(n.dotProduct(lightDirection)) > 0 ? DELTA : -DELTA);
		Point point = gp.point.add(epsVector);
		Ray lightRay = new Ray(point, lightDirection);
		var intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(point));
		return intersections == null;
	}

	/**
	 * add the effects to the color according to Phong module
	 * 
	 * @param p   geoPoint which we check the color in
	 * @param ray traced ray
	 * @return the effects (color)
	 */
	private Color calcLocalEffects(GeoPoint p, Ray ray) {
		Color color = p.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = p.geometry.getNormal(p.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return color;
		Material material = p.geometry.getMaterial();

		for (var ls : scene.lights) {
			Vector l = ls.getL(p.point);
			double nl = l == null ? 0 : (n.dotProduct(l));
			if (nl * nv > 0) {
				if (unshaded(p, l, n, ls)) {
					Color iL = ls.getIntensity(p.point);
					color = color.add(iL.scale(calcDiffusive(material, nl)),
							iL.scale(calcSpecular(material, n, l, nl, v)));
				}
			}
		}
		return color;

	}

	/**
	 * help function for calculating the color
	 * 
	 * @param p   geoPoint which we check the color in
	 * @param ray traced ray
	 * @return the color at the point
	 */
	private Color calcColor(GeoPoint p, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(p, ray));
	}

	/**
	 * traces given ray and returns color
	 * 
	 * @param r traced ray
	 * @return Color given from traced ray
	 */
	public Color traceRay(Ray r) {
		var lst = scene.geometries.findGeoIntersections(r);
		return lst == null ? scene.background : calcColor(r.findClosestGeoPoint(lst), r);
	}

}
