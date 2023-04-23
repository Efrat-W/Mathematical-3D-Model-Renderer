package geometries;

import primitives.*;
import java.util.List;

/**
 * Interface Intersectable is an interface that represents any Intersectable
 * object.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public interface Intersectable {

	List<Point> findIntsersections(Ray ray);

}
