package edu.cwru.srw89.map;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.Assert.*;

public class MarkerTestClasses {
    private MarkerMap map;
    private Rectangle2D rect;
    private Region region;

    @Before
    public void SetUp() throws Exception {
        map = new MarkerMap();
        rect = new Rectangle(0, 0, 25, 25);
        region = new Region(rect);
        map.addRegion(region);
    }

    @Test
    public void testCheckForObject() {
        /*
        Tests:
        Object.nonNull
        !Object.nonNull
        */

        map.insert(MarkerMap.Points.OIL, 25, 25);
        assertFalse(map.checkForObject(map.point(25, 25).getxCoord(), map.point(25, 25).getyCoord()));
        assertTrue(map.checkForObject(30, 30));
    }

    @Test
    public void testCount() {
        /*
        Tests:
            Entry.getValue().equals(type), Rec.contains(point.getxCoord(), point.getyCoord())
            !Entry.getValue().equals(type), Rec.contains(point.getxCoord(), point.getyCoord())
            Entry.getValue().equals(type), !Rec.contains(point.getxCoord(), point.getyCoord())
            !Entry.getValue().equals(type), !Rec.contains(point.getxCoord(), point.getyCoord())
        */

        map.insert(MarkerMap.Points.OIL, 25, 25);
        boolean equalType = map.point(25, 25).getType().equals(MarkerMap.Points.OIL);
        boolean inRect = rect.contains(map.point(25, 25).getxCoord(), map.point(25, 25).getyCoord());
        boolean notEqualType = map.point(25,25).getType().equals(MarkerMap.Points.ALIEN);
        boolean notInRect = rect.contains(60, 60);
        assertTrue(equalType && inRect);
        assertFalse(notEqualType && inRect);
        assertFalse(equalType && notInRect);
        assertFalse(notEqualType && notInRect);
    }

    MarkerMap.Points pickLabel() { //helper
        switch (ThreadLocalRandom.current().nextInt(2)) {
            case 0 : return MarkerMap.Points.OIL;
            case 1 : return MarkerMap.Points.ALIEN;
            case 2 : return MarkerMap.Points.OTHER;
            default : throw new IllegalStateException("Fourth case in a 3 case picker");
        }
    }

    @Test
    public void stressTest() {
        int OILCOUNT = 0;
        int ALIENCOUNT = 0;
        int OTHERCOUNT = 0;
        for (int i = 0; i < 1000000; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();
            MarkerMap.Points e = this.pickLabel();
            map.insert(e, x, y);
            assertEquals(map.point(x, y), new Marker(e, x, y));
            switch(e) {
                case OIL : OILCOUNT++;
                    break;
                case ALIEN: ALIENCOUNT++;
                    break;
                case OTHER: OTHERCOUNT++;
                    break;
            }
        }

        assertEquals(OILCOUNT, map.count(MarkerMap.Points.OIL));
        assertEquals(ALIENCOUNT, map.count(MarkerMap.Points.ALIEN));
        assertEquals(OTHERCOUNT, map.count(MarkerMap.Points.OTHER));
    }
}
