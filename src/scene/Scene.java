package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Class Scene is a class representing a scene of geometric shapes lighting etc
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class Scene {
	/**
	 * name of the scene
	 */
	public final String name;
	/**
	 * background color - default black
	 */
	public Color background = Color.BLACK;
	/**
	 * ambient light of the scene - default NONE
	 */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/**
	 * geometries 3D model
	 */
	public Geometries geometries = new Geometries();

	/**
	 * a constructor of Scene with string name
	 * 
	 * @param name String name for image result
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * background color setter
	 * 
	 * @param bg background Color color
	 * @return scene object itself
	 */
	public Scene setBackground(Color bg) {
		background = bg;
		return this;
	}

	/**
	 * ambient light setter
	 * 
	 * @param al AmbientLight
	 * @return scene object itself
	 */
	public Scene setAmbientLight(AmbientLight al) {
		ambientLight = al;
		return this;
	}

	/**
	 * Geometries setter
	 * 
	 * @param g Geometries list
	 * @return scene object itself
	 */
	public Scene setGeometries(Geometries g) {
		geometries = g;
		return this;
	}
}