package edu.cwru.srw89.map;

import java.awt.*;

/*
    Class: Region

    author: Simon Wang
    date: December 1, 2020

    Routines to insert the marker into the region, and to count the markers of the region of a specific type, and to
    check point coordinates within the region
 */

class Region {
    private final int XMIN, XMAX, YMIN, YMAX;
    private Shape Map;


    /**
     * Constructs a region object that holds the perimeter of the Shape Object
     * @param shape Shape Object that gives the coordinates and perimeter of the shape
     */
    Region(Shape shape) {
        Map = shape;
        XMIN = (int)shape.getBounds().getMinX();
        XMAX = (int)shape.getBounds().getMaxX();
        YMIN = (int)shape.getBounds().getMinY();
        YMAX = (int)shape.getBounds().getMaxY();
    }

    /**
     * Getter Method. Returns the left most X-coordinate
     * @return Left most X-coordinate
     */
    int getXMIN() {
        return XMIN;
    }

    /**
     * Getter Method. Returns the right most X-coordinate
     * @return Right most X-coordinate
     */
    int getXMAX() {
        return XMAX;
    }

    /**
     * Getter Method. Returns the bottom most Y-coordinate
     * @return Bottom most Y-coordinate
     */
    int getYMIN() {
        return YMIN;
    }

    /**
     * Getter Method. Returns the upper most Y-coordinate
     * @return Upper most Y-coordinate
     */
    int getYMAX() {
        return YMAX;
    }

    /**
     * Getter Method. Returns the shape object of the region
     * @return Map perimeter of the region
     */
    public Shape getMap() {
        return Map;
    }

    /**
     * toString Method. Returns a String representation of the Region.
     * @return String Representation: "Boundaries : ( XMIN, YMIN), ( XMAX, YMIN), ( XMIN, YMAX), ( XMAX, YMAX)
     *                                 Points: LISTOFMARKERS"
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Boundaries : (" + getXMIN() + ", " + getYMIN() + "), (" + getXMAX() + ", " + getYMIN() + "), ("
                + getXMIN() + ", " + getYMAX() + "), (" + getXMAX() + ", " + getYMAX() + ")");
        return output.toString();
    }

    /**
     * Helper method. Checks for whether the coordinate is within the region.
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return True if coordinate is within the region, False if not
     */
    boolean isInMap(double x, double y) {
        return x >= XMIN && x <= XMAX && y >= YMIN && y <=YMAX;
    }
}
