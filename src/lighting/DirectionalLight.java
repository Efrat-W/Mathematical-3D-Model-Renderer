package lighting;

import primitives.*;

/**
 * Class DirectionalLight is a class for representing directional light for the
 * scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class DirectionalLight extends Light implements LightSource {
	private final Vector direction;

	/**
	 * a constructor for a directional light by a given intensity color and a
	 * direction vector
	 * 
	 * @param intensity intensity color
	 * @param direction direction of the light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}

	@Override
	public double getDistance(Point point) {
		return Double.POSITIVE_INFINITY;
	}
}
