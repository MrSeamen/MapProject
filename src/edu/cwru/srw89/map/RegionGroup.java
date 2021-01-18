package edu.cwru.srw89.map;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
    Class: RegionGroup

    author: Simon Wang
    date: December 1, 2020

    Routines to add regions to the region group, check the points with the regions
 */

public class RegionGroup {
    Set<Region> RegionSet = new HashSet<>();
    private final LogClass log = new LogClass();

    /**
     * Adds a region into the group of regions
     * @param region region being added to the group
     * @return True if the region was added successfuly. False if the region intersects antoher region, or if the region
     * is null
     */
    public boolean addRegion(Region region) {
        if (Objects.nonNull(region)) {
            if (!RegionSet.isEmpty()) {
                if (!checkIntersect(region)) {
                    return RegionSet.add(region);
                } else { //if - the region being added intersects any existing region
                    log.log("Region intersected with another region: failed to add region. ADDREGION in REGIONGROUP");
                    return false;
                }
            } else { //if - the set of regions is empty
                RegionSet.add(region);
            }
        } //if - region being added was null
        log.log("Object was null. ADDREGION in REGIONGROUP");
        throw new IllegalArgumentException("Region being added was null");
    }

    /**
     * Helper Method. Checks whether the region intersects another region in the region group
     * @param check Region to be checked
     * @return True if the region does not intersect, false if the region does
     */
    private boolean checkIntersect(Region check) {
        boolean output = false;
        if (Objects.nonNull(check)) {
            for (Region r : RegionSet) {
                //checks if any corner of the region intersects with the set of existing region
                if (!checkPoints(check, r)) {
                    output = true;
                } //checks whether the region intersects the other region
            } //for - each region in the set
            return output;
        } //if - object is null
        log.log("Object was null. checkIntersect in REGIONGROUP");
        return false;
    }

    /**
     * Helper Method. Checks if the any of the corners fo the region being added are within the area of the original region
     * @param added region being added, with its points being checked
     * @param origin original region having being a point of reference
     * @return True if any of the points of the added region lie within the original region
     */
    private boolean checkPoints(Region added, Region origin) {
        return checkXBounds(added.getXMAX(), origin) || checkXBounds(added.getXMIN(), origin) || checkYBounds(added.getYMIN(), origin) || checkYBounds(added.getYMAX(), origin);
    }

    /**
     * Helper Method. Checks if the Y coordinates lie within the region
     * @param y Y-Coordinate
     * @param r Region being checked in
     * @return True if the coordinate lies within the region, false if not.
     */
    private boolean checkYBounds(double y, Region r) {
        return y >= r.getYMIN() && y <= r.getYMAX();
    }

    /**
     * Helper Method. Checks if the X coordinates lie within the region
     * @param x X-Coordinate
     * @param r Region being checked in
     * @return True if the coordinate lies within the region, false if not.
     */
    private boolean checkXBounds(double x, Region r) {
        return x >= r.getXMIN() && x <= r.getXMAX();
    }

    /**
     * Helper Method. Checks whether the X and Y coordinates lie within the region
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param r Region being checked
     * @return True if the coordinates lie within the region, false if not.
     */
    private boolean checkBounds(double x, double y, Region r) {
        return checkXBounds(x, r) && checkYBounds(y, r);
    }

    /**
     * Getter Method. Returns the group of regions.
     * @return Set of regions
     */
    Set<Region> getRegionSet() {
        return RegionSet;
    }

    /**
     * toString Method. Returns a string representation of the region group
     * @return String Representation: "Region (NUMBER): LISTOFREGION"
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int count = 1;
        for (Region r: RegionSet) {
            output.append("Regions (").append(count).append("): \n").append(r.toString()).append(" ");
            count++;
        }
        return output.toString();
    }

}

