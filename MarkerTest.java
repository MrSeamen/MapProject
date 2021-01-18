package edu.cwru.srw89.map;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class MarkerTest {

    @org.junit.Test
    public void getTypeTest() {
        Marker point2 = new Marker(MarkerMap.Points.ALIEN, 3, 4);
        Marker point1 = new Marker(MarkerMap.Points.OTHER, 3, 4);
        assertEquals(point1.getType(), MarkerMap.Points.ALIEN);
        assertEquals(point2.getType(), MarkerMap.Points.OTHER);
    }

    @org.junit.Test
    public void getxCoordTest() {
        Marker point = new Marker(MarkerMap.Points.ALIEN, 3, 4);
        assertEquals(3, point.getxCoord(), 0.1);
    }

    @org.junit.Test
    public void getyCoordTest() {
        Marker point = new Marker(MarkerMap.Points.ALIEN, 3, 4);
        assertEquals(4, point.getyCoord(), 0.1);
    }
}