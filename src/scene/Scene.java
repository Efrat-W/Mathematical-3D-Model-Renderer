package scene;
/**
 * Class Scene is a class representing a scene of geometric shapes lighting etc
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    Geometries geometries = new Geometries();
    
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
     */
    public Scene setBackground(Color bg) {
    	background = bg;
    	return this;
    }
    
    /**
     * ambient light setter
     * 
     * @param al AmbientLight
     */
    public Scene setAmbientLight(AmbientLight al) {
    	ambientLight = al;
    	return this;
    }
    
    /**
     * Geometries setter
     * 
     * @param g Geometries list
     */
    public Scene setGeometries(Geometries g) {
	geometries = g;
    	return this;
    }
}
