package geometries;

import static primitives.Util.*;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
	/** List of polygon's vertices */
	protected final List<Point> vertices;
	/** Associated plane in which the polygon lays */
	protected final Plane plane;
	private final int size;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		size = vertices.length;

		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (size == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();
		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (var i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double dis) {
		var intersection = this.plane.findGeoIntersections(ray, dis);
		if (intersection == null)
			return null;

		Point p0 = ray.getPoint();
		Vector v = ray.getDir();
		int size = vertices.size();

		Vector[] v1ToVn = new Vector[size];
		for (int i = 0; i < size; ++i) {
			if (p0.equals(this.vertices.get(i)))
				return null;
			v1ToVn[i] = this.vertices.get(i).subtract(p0);
		}

		Vector[] N1ToNn = new Vector[size];
		try {
			for (int i = 0; i < size; ++i) {
				N1ToNn[i] = v1ToVn[i].crossProduct(v1ToVn[(i + 1) % v1ToVn.length]).normalize();
			}
		} catch (IllegalArgumentException e) {
			return null;
		}
		double[] vn1ToVNn = new double[size];
		for (int i = 0; i < size; ++i) {
			vn1ToVNn[i] = alignZero(v.dotProduct(N1ToNn[i]));
			if (isZero(vn1ToVNn[i]))
				return null;
		}
		boolean isAllPositive = true;
		boolean isAllNegative = true;
		for (int i = 0; i < size; ++i) {
			if (vn1ToVNn[i] < 0 && isAllPositive) {
				isAllPositive = false;
				break;
			}
		}
		for (int i = 0; i < size; ++i) {
			if (vn1ToVNn[i] > 0 && isAllNegative) {
				isAllNegative = false;
				break;
			}
		}
		if (isAllNegative || isAllPositive) {
			intersection.get(0).geometry = this;
			return intersection;
		}
		return null;
	}

	@Override
	public Vector getNormal(Point point) {
		return plane.getNormal();
	}

	@Override
	protected void findMinMax() {
		minX = Double.POSITIVE_INFINITY;
		maxX = Double.NEGATIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		minZ = Double.POSITIVE_INFINITY;
		maxZ = Double.NEGATIVE_INFINITY;
		// Adjust the size of the box according to the vertices
		for (Point v : vertices) {
			if (v.getX() < minX)
				minX = v.getX();
			if (v.getX() > maxX)
				maxX = v.getX();
			if (v.getY() < minY)
				minY = v.getY();
			if (v.getY() > maxY)
				maxY = v.getY();
			if (v.getZ() < minZ)
				minZ = v.getZ();
			if (v.getZ() > maxZ)
				maxZ = v.getZ();
		}
	}

}
