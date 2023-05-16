package renderer;

import primitives.*;
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

	private Color calcColor(Point p) {
		return scene.ambientLight.getIntensity();
	}

	/**
	 * traces given ray and returns color
	 * 
	 * @param r traced ray
	 * @return Color given from traced ray
	 */
	public Color traceRay(Ray r) {
		var lst = scene.geometries.findIntersections(r);
		if (lst == null)
			return scene.background;
		return calcColor(r.findClosestPoint(lst));
	}

}
