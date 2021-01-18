package edu.cwru.srw89.map;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
    Class: MarkerMap

    author: Simon Wang
    date: December 1, 2020

    Routines to add regions to the local region group, and to insert markers into the regions of the region group
 */

public class MarkerMap implements RegionShape{
    public enum Points { OIL, ALIEN, OTHER }; //Available Point types to be used for Markers
    private Set<Marker> listOfValidPoints = new HashSet<>(); //Set of all the points correctly inserted
    private final LogClass log = new LogClass(); //logger
    RegionGroup regionGroup; //Group of Regions

    MarkerMap() {
        regionGroup = new RegionGroup();
    }
    MarkerMap(RegionGroup region) {regionGroup = region;}

    /**
     * Adds a new region of the shape dimensions to the region group
     * @param shape Shape Object that the region is being made from
     * @return True if shape is correctly added, false if not
     */
    boolean addRegion(Shape shape) {
        return regionGroup.addRegion(new Region(shape));
    }

    /**
     * Adds a new region of the shape dimensions to the region group
     * @param region Shape Object that the region is being made from
     * @return True if shape is correctly added, false if not
     */
    boolean addRegion(Region region) {
        return regionGroup.addRegion(region);
    }

    /**
     * Inserts a Marker Object into the region
     * @param marker Marker being inserted into the region
     * @return True if the marker's coordinates exist in the region and if there isn't already an existing marker in
     * the coordinates. False if not, or if the object is null.
     */
    @Override
    public boolean insert(Marker marker) {
        if (Objects.nonNull(marker)) {
            for (Region region : regionGroup.getRegionSet()) {
                if (region.isInMap(marker.getxCoord(), marker.getyCoord())) {
                    if (!checkForObject(marker.getxCoord(), marker.getyCoord())) {
                        listOfValidPoints.add(marker);
                    } else { //if - checking if there already exists a marker in the coordinates
                        log.log("Object already exists in position, cannot insert over object. INSERT IN REGION");
                    }
                } else { //if - checking if marker's coordinates are in region
                    log.log("Coordinates are invalid: not within the region. INSERT IN REGION");
                }
            } //for - looping through the regions to find matching region
        } else { //if - checking of object is null
            log.log("Marker was null, could not be Inserted. INSERT IN REGION");
            throw new IllegalArgumentException("Marker was null.");
        }
        //double checks whether the point was actually inserted
        return doubleCheck(marker.getxCoord(), marker.getyCoord(), marker);
    }

    /**
     * Inserts all the markers of the collection into the region
     * @param c Collection of markers being inserted into hte region
     * @return True if ALL the markers were inserted correctly. False if any one of the inserts fails, or if the collection is null.
     */
    public boolean insertAll(Collection<Marker> c) {
        boolean output = true;
        if (Objects.nonNull(c)) {
            for (Marker marker : c) {
                //if inserting fails
                if (!insert(marker)) {
                    output = false;
                } //no else because only looking for one instance of a failure of the insert method
            }//for - looping through the collection of markers and calling insert
            return output;
        } else { //if - checking if object is null
            log.log("Collection was null, unable to insert null objects. INSERTALL IN REGION");
            throw new IllegalArgumentException("Collection cannot be null.");
        }
    }

    /**
     * Counts the number of markers in the region with the specified type
     * @param type Type of marker being accounted for
     * @return Number of markers of the type specified
     */
    public int count(MarkerMap.Points type) {
        int count = 0; //count number that actually checks through the list
        for (Marker m : listOfValidPoints) {
            //actually checking whether the points of the marker are in the map and equal the type
            for (Region region : regionGroup.getRegionSet()) {
                if (region.isInMap(m.getxCoord(), m.getyCoord())) {
                    if (m.getType().equals(type)) { //if - checks if the marker type equals the type being accounted for
                        count++;
                    }//No need for else because it will not do anything if it does not find the object of right type
                } else {//if - marker's coordinates are contained by the region
                    log.log("Coordinates entered were out of bounds. COUNT IN REGION");
                }
            } //for - loops through the regions to find one that matches
        } //for - loops through all the markers
        return count;
    }

    /**
     * Inserts a Marker into the region, using the parameters of a marker.
     * @param type Type of the Marker
     * @param x X-coordinate of the Marker
     * @param y Y-coordinate of the Marker
     * @return True if the marker's coordinates exist in the region and if there isn't already an existing marker in
     * the coordinates. False if not, or if the object is null.
     */
    @Override
    public boolean insert(MarkerMap.Points type, double x, double y) {
        Marker point = new Marker(type, x, y); //Creates a new marker with the specifications
        return insert(point);
    }

    /**
     * Helper method. Double checks whether the marker located on the map equals the marker specified
     * @param x Xcoordinate being checked
     * @param y Ycoordinate being checked
     * @param point Marker being checked
     * @return True if marker equals the point, false if not
     */
    boolean doubleCheck(double x, double y, Marker point) {
        return (point(x,y).equals(point));
    }

    /**
     * Finds the point located at the coordinates at ( x, y)
     * @param x X-Coordinates of the point being looked for
     * @param y Y-Coordinates of the point being looked for
     * @return Marker object with coordinates at ( x, y), null if point is not within any of the regions
     */
    public Marker point(double x, double y) {
        //looping through the regions and checking the coordinates is an extra way of double checking whether the point
        //exists in the region
        for (Region region : regionGroup.getRegionSet()) {
            if (region.isInMap(x, y)) {
                for (Marker m : listOfValidPoints) {
                    //checking matching coordinates between the marker and parameter
                    if (m.getxCoord() == x && m.getyCoord() == y) {
                        return m;
                    } //no else becasue it moves onto the next marker
                } //for - loops through the markers
                log.log("Coordinates do not match point found. POINT IN REGION");
            } else {//if - checks whether the coordinates are within the region
                log.log("Coordinates are invalid: not within the region. POINT IN REGION");
            }
        } //for - loops through the region to find one that matches
        log.log("Point does not exist in region. POINT IN REGION");
        throw new IllegalArgumentException("Coordinates point to a nonexistent marker in the region.");
    }

    /**
     * Helper method. Checks for whether there is a marker with the coordinates specified.
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return True if there is a marker that already exists with the specified coordinates, false if not
     */
    boolean checkForObject(double x, double y) {
        for (Marker marker : listOfValidPoints) {
            if (marker.getxCoord() == x && marker.getyCoord() == y) { //if statement because dont want to return on immediately
                return true;
            } //no else because only checking if there is one
        } // for - loops through the markers
        return false;
    }

    /**
     * Getter Method. Returns the list of points that exist within the region
     * @return List of existing points
     */
    public Set<Marker> getListOfValidPoints() {
        return listOfValidPoints;
    }

    @Override
    public String toString() {
        String output = "";
        int count = 1;
        for (Region region : regionGroup.getRegionSet()) {
            output += "Region (" + count + "): " + region.toString() + "n/ Points: ";
            for (Marker marker : listOfValidPoints) {
                if (region.isInMap(marker.getxCoord(), marker.getyCoord())) {
                    output += marker.toString() + " | ";
                } //if - marker is in the region selected
            } //for - each marker in the list
        } //for - each region in the set
        return output;
    }
}

