package lighting;

import primitives.*;
import static primitives.Util.*;

/**
 * Class SpotLight is a class for representing spot light for the scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class SpotLight extends PointLight {
	private final Vector direction;
	private double narrowBeam = 1;
	
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
		double dirL = alignZero(direction.dotProduct(getL(p)));
		dirL = Math.pow(dirL, narrowBeam);
		return dirL <= 0 ? Color.BLACK : super.getIntensity(p).scale(dirL);
	}
	
	/**
	 * sets narrowness of the spotlight beam
	 * 
	 * @param narrowBeam value to set
	 * @return this lightSource
	 */
	public SpotLight setNarrowBeam(double narrowBeam) {
	    this.narrowBeam = narrowBeam;
	    return this;
	}
}
