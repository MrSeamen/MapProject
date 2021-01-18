package edu.cwru.srw89.map;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class RegionTest {
    private Rectangle2D rect, rect1;
    private Region region;

    @Before
    public void SetUp() throws Exception {
        rect = new Rectangle(0, 0, 25, 25);
        rect1 = new Rectangle(0, 0, 28, 25);
        region = new Region(rect);
    }

    @Test
    public void isInMap() {
        assertTrue(region.isInMap(10, 10));
        assertTrue(region.isInMap(25,25));
        assertTrue(region.isInMap(0,0));
        assertTrue(region.isInMap(25, 0));
        assertTrue(region.isInMap(0, 25));
        assertFalse(region.isInMap(-10, -10));
        assertFalse(region.isInMap(50, 50));
        assertFalse(region.isInMap(-10, 50));
        assertFalse(region.isInMap(50, -10));
    }

    @Test
    public void getters() {
        assertEquals(rect, region.getMap());
        assertNotEquals(rect1, region.getMap());
    }


}