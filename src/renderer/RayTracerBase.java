package renderer;

import primitives.*;
import scene.Scene;

/**
 * Class RayTracerBase is the class representing the RayTracer abstract base
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public abstract class RayTracerBase {
	protected final Scene scene;

	/**
	 * abstract Ray Tracer constructor
	 * 
	 * @param sc scene
	 */
	public RayTracerBase(Scene sc) {
		scene = sc;
	}

	/**
	 * traces given ray and returns color
	 * 
	 * @param r traced ray
	 * @return Color given from traced ray
	 */
	public abstract Color traceRay(Ray r);

	
}
