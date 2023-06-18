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
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;

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
		return material.kD.scale(nl < 0 ? -nl : nl);
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
	 * @param ls light source we check
	 * @return boolean if unshaded or not
	 */
	@SuppressWarnings("unused")
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource ls) {
		Vector lightDirection = l.scale(-1);
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		var intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(lightRay.getPoint()));
		if (intersections == null)
			return true;
		for (GeoPoint g : intersections)
			if (g.geometry.getMaterial().kT.equals(Double3.ZERO))
				return false;
		return true;
	}

	/**
	 * returns level of transparency
	 * 
	 * @param gp the geoPoint that we check
	 * @param ls light source we check
	 * @param ls the light source direction
	 * @param n  the normal at gp
	 * @return level of transparency
	 */
	private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
		Double3 ktr = new Double3(1);
		Vector lightDirection = l.scale(-1);
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		var intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(lightRay.getPoint()));
		if (intersections == null)
			return ktr;
		for (GeoPoint g : intersections) {
			ktr = ktr.product(g.geometry.getMaterial().kT);
			if (ktr.lowerThan(MIN_CALC_COLOR_K))
				return Double3.ZERO;
		}
		return ktr;
	}

	/**
	 * add the effects to the color according to Phong module
	 * 
	 * @param p   geoPoint which we check the color in
	 * @param ray traced ray
	 * @param k   attenuation coefficient
	 * @return the effects (color)
	 */
	private Color calcLocalEffects(GeoPoint p, Ray ray, Double3 k) {
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
				Double3 ktr = transparency(p, ls, l, n);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = ls.getIntensity(p.point).scale(ktr);
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
	 * @param p     geoPoint which we check the color in
	 * @param ray   traced ray
	 * @param level recursion iteration
	 * @param k     attenuation coefficient
	 * @return the color at the point
	 */
	private Color calcColor(GeoPoint p, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(p, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(p, ray, level, k));
	}

	/**
	 * traces given ray and returns color
	 * 
	 * @param r traced ray
	 * @return Color given from traced ray
	 */
	public Color traceRay(Ray r) {
		GeoPoint closestPoint = findClosestIntersection(r);
		return closestPoint == null ? scene.background : calcColor(closestPoint, r);
	}

	/**
	 * construct reflection ray
	 * 
	 * @param gp the intersection GeoPoint
	 * @param v  direction vector of the ray sent from camera
	 * @param n  normal vector
	 * @return reflection ray
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
		double vn = v.dotProduct(n);
		return new Ray(gp.point, v.subtract(n.scale(2 * vn)), n);
	}

	/**
	 * construct refraction ray
	 * 
	 * @param gp the intersection GeoPoint
	 * @param v  direction vector of the ray sent from camera
	 * @param n  normal vector
	 * @return refraction ray
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
		return new Ray(gp.point, v, n);
	}

	/**
	 * calculate the closest GeoPoint from those which intersect with a given ray
	 * 
	 * @param ray the intersecting ray
	 * @return closest GeoPoint
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * auxiliary function for calcColor
	 * 
	 * @param gp  closest point sent from calcColor
	 * @param ray traced ray
	 * @return calculated color
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}

	/**
	 * calculating the global effects
	 * 
	 * @param gp    geoPoint that the effects are calculated at
	 * @param ray   traced ray
	 * @param level recursion iteration
	 * @param k     attenuation coefficient
	 * @return color with global effects
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcColorGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR)
				.add(calcColorGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
	}

	/**
	 * help function for the recursion
	 * 
	 * @param ray   traced ray
	 * @param level recursion iteration
	 * @param k     attenuation coefficient
	 * @param kx    reflection/refraction attenuation coefficient
	 * @return color with global effects
	 */
	private Color calcColorGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)
			return scene.background.scale(kx);

		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);
	}

}
