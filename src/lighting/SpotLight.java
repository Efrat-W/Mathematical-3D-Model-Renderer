package lighting;

import primitives.*;

/**
 * Class SpotLight is a class for representing spot light for the scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class SpotLight extends PointLight {
	private final Vector direction;

	/**
	 * a constructor for a spot light by a given intensity color and a direction
	 * vector
	 * 
	 * @param intensity intensity color
	 * @param position  position of the light source
	 * @param direction direction of the light
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		Vector l = getL(p);
		return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(l)));
	}
}
