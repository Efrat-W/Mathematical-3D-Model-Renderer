package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class AmbientLight is the basic class representing ambient light for the
 * scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class AmbientLight extends Light {
	/**
	 * ambient light of the scene
	 */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * a constructor of ambient light (ip) based on a Double3 value
	 * 
	 * @param ia intensity of the light source
	 * @param ka a Double3
	 */
	public AmbientLight(Color ia, Double3 ka) {
		super(ia.scale(ka));
	}

	/**
	 * a constructor of ambient light (ip) based on a double value
	 * 
	 * @param ia intensity of the light source
	 * @param ka a double
	 */
	public AmbientLight(Color ia, double ka) {
		super(ia.scale(ka));
	}

}
