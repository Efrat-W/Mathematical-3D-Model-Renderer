package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class AmbientLight is the basic class representing ambient light for the scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class AmbientLight {
    private final Color intensity;
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
    
    /**
     * a constructor of ambient light (ip) based on a Double3 value
     * 
     * @param ka a Double3 
     */
    public AmbientLight(Color ia, Double3 ka) {
	this.intensity = ia.scale(ka);
    }
    
    /**
     * a constructor of ambient light (ip) based on a double value
     * 
     * @param ka a double 
     */
    public AmbientLight(Color ia, double ka) {
	this.intensity = ia.scale(ka);
    }
    
    /**
     * returns ip
     * 
     * @return intensity
     */
    public Color getIntensity() {
	return intensity;
    }
}
