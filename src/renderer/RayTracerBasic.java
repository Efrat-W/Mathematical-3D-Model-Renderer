package renderer;

import static primitives.Util.*;
import primitives.*;
import geometries.Intersectable.GeoPoint;
import scene.Scene;

/**
 * Class RayTracerBasic is the class representing the RayTracer
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class RayTracerBasic extends RayTracerBase {
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
		return material.kS.scale(Math.pow(Math.max(0, -(v.dotProduct(r))), material.nShininess));
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
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) {
				Color iL = ls.getIntensity(p.point);
				color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
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
		if (lst == null)
			return scene.background;
		return calcColor(r.findClosestGeoPoint(lst), r);
	}

}
