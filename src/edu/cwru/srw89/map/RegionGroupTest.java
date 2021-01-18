package edu.cwru.srw89.map;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class RegionGroupTest {

    private MarkerMap Map;
    private RegionGroup RegionGroup;
    private Rectangle2D rect;
    private Region region1, region2, region3;

    @Before
    public void SetUp() throws Exception {
        RegionGroup =  new RegionGroup();
        Map = new MarkerMap(RegionGroup);
        rect = new Rectangle(0, 0, 25, 25);
        region1 = new Region(rect);
        region2 = new Region(new Rectangle(100, 100, 50, 50));
        region3 = new Region(new Rectangle(15, 15, 50, 50));
        RegionGroup.addRegion(region1);
    }

    @Test
    public void addRegion() {
        assertTrue(RegionGroup.addRegion(region2));
        assertFalse(RegionGroup.addRegion(region3));
        assertFalse(RegionGroup.addRegion(new Region(null)));
    }

    @Test
    public void stressTest() {
        int count = 0;
        for (int i = 0; i < 1000000; i++) {
            int range = ThreadLocalRandom.current().nextInt(count, count + 20);
            int coord = ThreadLocalRandom.current().nextInt(count+5, count + 15);
            Region GoodRegion = new Region(new Rectangle(count, count, range, range));
            assertTrue(RegionGroup.addRegion(GoodRegion));
            Region BadRegion = new Region(new Rectangle(count + 1, count + 1, range, range));
            assertFalse(RegionGroup.addRegion(BadRegion));
            count += 20;
            assertFalse(RegionGroup.addRegion(null));
            Marker mark = new Marker(MarkerMap.Points.OIL, coord, coord);
            Map.insert(mark);
        }
    }
}