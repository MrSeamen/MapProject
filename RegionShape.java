package edu.cwru.srw89.map;

import java.awt.*;

public interface RegionShape {
    public int count(MarkerMap.Points type);

    public boolean insert(Marker marker);

    public boolean insert(MarkerMap.Points type, double x, double y);
}
