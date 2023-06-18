package lighting;

import primitives.*;

/**
 * Class LightSource is an interface for representing a general light source for
 * the scene
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public interface LightSource {

	/**
	 * a getter for intensity in a given point
	 * 
	 * @param p the point to get the intensity of
	 * @return intensity
	 */
	public Color getIntensity(Point p);

	/**
	 * get the direction of the light at a point
	 * 
	 * @param p the point to get the direction of
	 * @return the direction vector
	 */
	public Vector getL(Point p);

	/**
	 * get the distance between the given point and the lightsource
	 * 
	 * @param point the point from which we calculate the distance
	 * @return distance between the given point and the lightsource
	 */
	public double getDistance(Point point);

}
