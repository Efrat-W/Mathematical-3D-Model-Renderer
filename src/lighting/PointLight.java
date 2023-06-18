package lighting;

import primitives.*;

/**
 * Class PointLight is a class for representing point light for the scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class PointLight extends Light implements LightSource {
	private final Point position;
	private double kC = 1;
	private double kL = 0;
	private double kQ = 0;

	/**
	 * a constructor for a point light by a given intensity color and a position
	 * point
	 * 
	 * @param intensity intensity color
	 * @param position  position of the light source
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * a setter for kC
	 * 
	 * @param k the value to set
	 * @return this point light
	 */
	public PointLight setKc(double k) {
		kC = k;
		return this;
	}

	/**
	 * a setter for kL
	 * 
	 * @param k the value to set
	 * @return this point light
	 */
	public PointLight setKl(double k) {
		kL = k;
		return this;
	}

	/**
	 * a setter for kQ
	 * 
	 * @param k the value to set
	 * @return this point light
	 */
	public PointLight setKq(double k) {
		kQ = k;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double dSquared = p.distanceSquared(position);
		return intensity.reduce(kC + kL * Math.sqrt(dSquared) + kQ * dSquared);
	}

	@Override
	public Vector getL(Point p) {
		try {
			return p.subtract(position).normalize();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public double getDistance(Point point) {
		return point.distance(position);
	}
}
