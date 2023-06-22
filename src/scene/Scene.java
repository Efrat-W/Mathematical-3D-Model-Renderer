package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
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
	 * the lights in the scene
	 */
	public List<LightSource> lights = new LinkedList<>();

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

	/**
	 * lights setter
	 * 
	 * @param ls light sources list
	 * @return scene object itself
	 */
	public Scene setLights(LightSource... ls) {
		lights.addAll(List.of(ls));
		return this;
	}

	/**
	 * Sets Conservative Bounding Region for creating the scene (for its 3D model).<br>
	 * It must be called <b><u>before</u></b> creating the 3D model (adding bodyes to the scene).
	 * @return scene object itself
	 */
	public Scene setCBR() {
		Intersectable.setCbr();
		return this;
	}

	/**
	 * Creates Bounding Volume Hierarchy in the scene's 3D model<br>
	 * It must be called <b><u>after</u></b> creating the 3D model (adding bodyes to the scene).
	 * @return scene object itself
	 */
	public Scene setBVH() {
		geometries.setBVH();	
		return this;
	}
}
