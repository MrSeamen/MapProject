package edu.cwru.srw89.map;

import java.util.Objects;

/*
    Class: Marker

    author: Simon Wang
    date: December 1, 2020

    Marker Object to hold the marker type, and coordinates
 */

public class Marker {
    final private MarkerMap.Points type;
    final private double xCoord;
    final private double yCoord;

    /**
     * Makes a Marker Object that holds a MarkerMap.Points as an identification type, and two doubles to represent
     * the "Coordinates" of the Marker
     * @param type Designated type of marker
     * @param xCoord X-Coordinate of the object
     * @param yCoord Y-Coordinate of the object
     */
    Marker(MarkerMap.Points type, double xCoord, double yCoord) {
        //boundaries and type check are checked in the insert method
        assert Objects.nonNull(type) : "Type of Object cannot be null";
        this.type = type;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * Getter Method
     * @return Type of Marker Object
     */
    MarkerMap.Points getType() {
        return type;
    }

    /**
     * Getter Method
     * @return X-coordinate of Marker Object
     */
    double getxCoord() {
        return xCoord;
    }

    /**
     * Getter Method
     * @return Y-coordinate of Marker Object
     */
    double getyCoord() {
        return yCoord;
    }

    /**
     * toString Method. Returns a String representation of the Marker.
     * @return String Representation: "Type: MARKERTYPE | Coordinates: ( XCOORDINATE, YCOORDINATE)"
     */
    @Override
    public String toString() {
        return "Type: " + type + " | Coordinates: ( " + xCoord + ", " + yCoord + ")";
    }
}
