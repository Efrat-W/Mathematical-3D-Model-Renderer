package primitives;

import lighting.PointLight;

/**
 * Class Material is the basic class representing the materials
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
public class Material {
	/**
	 * diffusion attenuation coefficient
	 */
	public Double3 kD = Double3.ZERO;
	/**
	 * specular attenuation coefficient
	 */
	public Double3 kS = Double3.ZERO;
	/**
	 * the concentration of the lightning
	 */
	public int nShininess = 1;

	/**
	 * a setter for kD
	 * 
	 * @param k the double value to set
	 * @return this material
	 */
	public Material setKd(double k) {
		kD = new Double3(k);
		return this;
	}

	/**
	 * a setter for kD
	 * 
	 * @param k the Double3 value to set
	 * @return this material
	 */
	public Material setKd(Double3 k) {
		kD = k;
		return this;
	}

	/**
	 * a setter for kS
	 * 
	 * @param k the double value to set
	 * @return this material
	 */
	public Material setKs(double k) {
		kS = new Double3(k);
		return this;
	}

	/**
	 * a setter for kS
	 * 
	 * @param k the Double3 value to set
	 * @return this material
	 */
	public Material setKs(Double3 k) {
		kS = k;
		return this;
	}

	/**
	 * a setter for nShininess
	 * 
	 * @param k the int value to set
	 * @return this material
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}