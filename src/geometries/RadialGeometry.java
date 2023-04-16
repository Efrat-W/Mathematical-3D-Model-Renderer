package geometries;

/*
* Class RadialGeometry is an abstract class representing radial
* geometries in Cartesian 3-Dimensional coordinate system.
* @author Efrat Wexler and Sari Zilberlicht
*/

public abstract class RadialGeometry implements Geometry {

	/*
	* the radius of the radial geometry
	*/
	protected final double radius;

    /**
     * Radial Geometry constructor that sets the radius
     * 
     * @param r radius
     */
    public RadialGeometry(double r) {
    	radius = r;
    }
}
