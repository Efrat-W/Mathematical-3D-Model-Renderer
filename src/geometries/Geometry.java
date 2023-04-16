package geometries;

/*
* Interface Geometry is an interface that represents
* any geometry in general.
* @author Efrat Wexler and Sari Zilberlicht
*/
import primitives.Point;
import primitives.Vector;

public interface Geometry {

    /**
     * get normal vector, perpendicular at the point
     * 
     * @param p point on the geometric surface
     * @return normal vector
     */
    public Vector getNormal(Point p);
}
