package edu.cwru.srw89.map;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class MarkerMapTest {
    private MarkerMap map;
    private Region region;


    @Before
    public void SetUp() throws Exception {
        map = new MarkerMap();
        region = new Region(new Rectangle(0, 0, 100, 150));
        map.addRegion(region);
        map.insert(MarkerMap.Points.OIL, 33, 50);
        map.insert(MarkerMap.Points.ALIEN, 66, 100);
        map.insert(MarkerMap.Points.OTHER, 100, 150);
    }

    @Test
    public void insertAll() {
        Set<Marker> set = new HashSet<>();
        for (int i = 1; i <= 10; i++) {
            set.add(new Marker(MarkerMap.Points.OIL, i, i));
        }
        assertTrue(map.insertAll(set));
        assertFalse(map.insert(null));
    }

    MarkerMap.Points pickLabel() { //helper
        switch (ThreadLocalRandom.current().nextInt(0,2)) {
            case 0 : return MarkerMap.Points.OIL;
            case 1 : return MarkerMap.Points.ALIEN;
            case 2 : return MarkerMap.Points.OTHER;
            default : throw new IllegalStateException("Fourth case in a 3 case picker");
        }
    }

    @Test
    public void insertStressTest() {
        int count = 0;

        for (int i = 0; i < 1000000; i++) {
            assertEquals(map.count(MarkerMap.Points.ALIEN), count);
            assertFalse(map.insert(null));
            double x = ThreadLocalRandom.current().nextDouble(0, 100);
            double y = ThreadLocalRandom.current().nextDouble(0, 100);
            MarkerMap.Points Label = this.pickLabel();
            Marker marker = new Marker(Label, x, y);
            double fx = ThreadLocalRandom.current().nextDouble(-150,-100);
            double fy = ThreadLocalRandom.current().nextDouble(-150, -100);
            MarkerMap.Points FalseLabel = this.pickLabel();

            assertEquals(false, map.insert(FalseLabel, fx, fy));
            if (Label.equals(MarkerMap.Points.ALIEN) && map.checkForObject(x, y)) {
                assertTrue(map.insert(Label, x, y));
                count++;
                assertEquals(marker, map.point(x, y));
            }
        }
    }

    @Test
    public void addRegionStressTest() {
        int count = 0;
        for (int i = 0; i < 1000000; i++) {
            int range = ThreadLocalRandom.current().nextInt(count, count + 20);
            Region GoodRegion1 = new Region(new Rectangle(count, count, range, range));
            assertTrue(map.addRegion(GoodRegion1));
            assertTrue(map.addRegion(new Rectangle(count, count, range + 20, range + 20)));
            Region BadRegion = new Region(new Rectangle(count + 1, count + 1, range, range));
            assertFalse(map.addRegion(BadRegion));
            count += 40;
            assertFalse(map.addRegion((Region) null));
        }
    }
}