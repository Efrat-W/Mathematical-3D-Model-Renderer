package geometries;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Tube is the basic class representing a tube in Cartesian 3-Dimensional
 * coordinate system.
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */

public class Tube extends RadialGeometry {

	/**
	 * axis ray
	 */
	protected final Ray axisRay;

	/**
	 * Tube constructor based on axis ray and radius.
	 * 
	 * @param axis_ray axis ray
	 * @param rad      radius
	 */
	public Tube(Ray axis_ray, double rad) {
		super(rad);
		axisRay = axis_ray;
	}

	/**
	 * Get radius of tube
	 * 
	 * @return radius
	 */
	public Double getRadius() {
		return radius;
	}

	/**
	 * Get axis ray
	 * 
	 * @return axis ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	@Override
	public Vector getNormal(Point p) {
		Vector v = axisRay.getDir();
		Point p1 = axisRay.getPoint();
		double t = v.dotProduct(p.subtract(p1));
		if (isZero(t)) // in case of p being right on top of axis ray point
			return p.subtract(p1);
		Point o = p1.add(v.scale(t));
		return p.subtract(o).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
//		Point rayPoint = ray.getPoint();
//		Vector rayVector = ray.getDir(),
//				axisRayVector = axisRay.getDir(), temp1, temp2;
//		if(ray.equals(axisRay)||(rayPoint.equals(axisRay.getPoint())&& rayVector.equals(axisRayVector.scale(-1))))
//			return null;
//		
//		Vector deltaPoints=rayPoint.subtract(axisRay.getPoint());
//		
//		try {
//			rayVector.crossProduct(axisRayVector);
//		}
//		catch (IllegalArgumentException e) {
//			return null;
//
//		}// 0 Intersections
//		
//		if (deltaPoints.dotProduct(axisRayVector) == 0) // 0 Intersections
//			return null;
//		double dot1 = rayVector.dotProduct(axisRayVector),
//			   dot2 = deltaPoints.dotProduct(axisRayVector);
//
//		temp1 = dot1 != 0 ? rayVector.subtract(axisRayVector.scale(dot1)) : rayVector;
//		temp2 = deltaPoints.subtract(axisRayVector.scale(dot2));
//
//		double A = temp1.dotProduct(temp1);
//		double B = dot1 != 0
//				? 2 * rayVector.subtract(axisRayVector.scale(dot1))
//						.dotProduct(deltaPoints.subtract(axisRayVector.scale(dot2)))
//				: 2 * rayVector.dotProduct(deltaPoints.subtract(axisRayVector.scale(dot2)));
//		double C = temp2.dotProduct(temp2) - radius * radius;
//		double delta = B * B - 4 * A * C;
//
//		if (delta < 0) {// 0 Intersections
//			return null;
//		}
//		double t1 = (-B + Math.sqrt(delta)) / (2 * A), 
//			   t2 = (-B - Math.sqrt(delta)) / (2 * A);
//
//		if(t1==0||t2==0)
//			return null;
//		
////		Point p1=rayPoint.add(rayVector.scale(t1));
////		Point p2=rayPoint.add(rayVector.scale(t2));
//
//		if (delta == 0) {
//			return null;
////			if (-B / (2 * A) < 0 )
////				return null;
////			return List.of(rayPoint.add(rayVector.scale(-B / (2 * A))));// 1
////			
//		} else if (t1 < 0 && t2 < 0) {
//			return null;
//			
//		} else if (t1 < 0 && t2 > 0 ) {
//			return List.of(rayPoint.add(rayVector.scale(t2)));
//		} else if (t1 > 0 && t2 < 0) {
//			return List.of(rayPoint.add(rayVector.scale(t1)));
//
//		} else
//			return List.of(rayPoint.add(rayVector.scale(t1)),rayPoint.add(rayVector.scale(t2)));

		Point d;
		Point e;
		double dis;
		double ab;

		// Given ray (A + ta)
		Point pointA = ray.getPoint();
		Vector vectorA = ray.getDir();
		// Tube ray (B + tb)
		Point pointB = axisRay.getPoint();
		Vector vectorB = axisRay.getDir();

		ab = vectorA.dotProduct(vectorB);
		// if is parallel to tube
		try {
			vectorA.crossProduct(vectorB);
		} catch (IllegalArgumentException ex) {
			return null;
		}

		double bb = 1; // it is a unit vector therefore it's squared size is 1
		double aa = 1;
		double bc, ac;
		try {
			// Vector AB
			Vector c = pointB.subtract(pointA);
			// dot-product calc
			bc = vectorB.dotProduct(c);
			ac = vectorA.dotProduct(c);

			// The closest point on (A + t1a)
			double t1 = (-ab * bc + ac * bb) / (/* aa * bb */ 1 - ab * ab);
			try {
				d = pointA.add(vectorA.scale(t1));
			} catch (IllegalArgumentException ex) {
				d = pointA;
			}

			// The closest point on (B + t2b)
			double t2 = (ab * ac - bc * aa) / (/* aa * bb */ 1 - ab * ab);

			try {
				e = pointB.add(vectorB.scale(t2));
			} catch (IllegalArgumentException ex) {
				e = pointB;
			}

			// distance between two rays
			dis = d.distance(e);

		} catch (IllegalArgumentException ex) {
			// If A and B are the same
			d = ray.getPoint();
			dis = 0;
		}

		double diff = alignZero(dis - radius);
		// The ray doesn't touch the Tube
		if (diff > 0.0)
			return null;

		// The ray is tangent to the Tube
		if (diff == 0.0) {
			return null;
//			// The ray starts at the point
//			if (d.equals(pointA)) {
//				return List.of(d);
//			}
//			// The ray starts after the point
//			if (d.subtract(pointA).dotProduct(vectorA) < 0.0) {
//				return null;
//			}
//			// The ray starts before the point
//			return List.of(d);

		}

		/*
		 * We know that the ray goes through the tube. Lets cut the tube parallel to the
		 * ray. We will get a ellipse where the height is radius. We need to calculate
		 * the width
		 */
		double width;
		// Whether the ray is orthogonal to the tube?
		try {
			// sin's between (B + tb) and (A + ta) is |VxU|
			double sinA = vectorA.crossProduct(vectorB).length();
			// ellipse width
			width = radius / sinA;
		} catch (IllegalArgumentException ex) { // it is orthogonal
			width = radius;
		}
		// ellipse equation x^2/k^2 + y^2 = radius^2
		// if the width is w then k is w/r
		double k = width / radius;
		// y is d for our ray x^2/k^2 + k^2 = radius^2 => x^2/k^2 = radius^2 -d^2 =>
		// x^2 = (radius^2 -d^2)*k^2 => x = sqrt(radius^2 -d^2)*k
		double th = Math.sqrt(radius * radius - dis * dis) * k;

		// the two points
		Point p1 = d.subtract(vectorA.scale(th));
		Point p2 = d.add(vectorA.scale(th));

		// Check if the points are in range and return them

		try {
			// the ray starts before point 1
			if (!(p1.subtract(pointA).dotProduct(vectorA) < 0.0) && !(p2.subtract(pointA).dotProduct(vectorA) < 0.0)) {
				return List.of(p1, p2);
			}
		} catch (IllegalArgumentException ex) {
			// the ray starts at point1

		}

		try {
			// the ray starts before point 1
			if (!(p1.subtract(pointA).dotProduct(vectorA) < 0.0)) {
				return List.of(p1);
			}
		} catch (IllegalArgumentException ex) {
			// the ray starts at point1

		}

		try {
			// the ray starts before point 2
			if (!(p2.subtract(pointA).dotProduct(vectorA) < 0.0)) {
				return List.of(p2);
			}
		} catch (IllegalArgumentException ex) {
			// the ray starts at point2

		}

		return null;

	}

	@Override
	public String toString() {
		return "" + axisRay + ", " + radius;
	}

}
