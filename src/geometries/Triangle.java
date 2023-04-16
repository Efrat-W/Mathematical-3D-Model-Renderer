package geometries;

import primitives.Point;

public class Triangle extends Polygon {

    /**
     * Plane constructor based on 3 vertices of the triangle.
     * 
     * @param p1 vertex of the triangle
     * @param p2 vertex of the triangle
     * @param p3 vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
	super(p1, p2, p3);
    }

}
