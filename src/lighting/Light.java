package lighting;

import primitives.Color;

/**
 * Class Light is an abstract class for representing a general light source for
 * the scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
abstract class Light {
	protected final Color intensity;

	/**
	 * a constructor for light by an intensity color
	 * 
	 * @param intensity for the intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * a getter for intensity
	 * 
	 * @return intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
}
