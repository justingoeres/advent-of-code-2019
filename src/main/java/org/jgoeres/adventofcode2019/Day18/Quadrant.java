package org.jgoeres.adventofcode2019.Day18;

import com.google.common.collect.Range;
import org.jgoeres.adventofcode2019.common.XYPoint;

public enum Quadrant {
//    UPPER_LEFT(1, 39, 1, 39),  // upper left
//    UPPER_RIGHT(41, 80, 1, 39), //  upper right
//    LOWER_LEFT(1, 39, 41, 80), //  lower left
//    LOWER_RIGHT(41, 80, 41, 80);    //  lower right

//    private int xMin;
//    private int xMax;
//    private int yMin;
//    private int yMax;

    UPPER_LEFT(1, 39, 1, 39),  // upper left
    UPPER_RIGHT(41, 80, 1, 39), //  upper right
    LOWER_LEFT(1, 39, 41, 80), //  lower left
    LOWER_RIGHT(41, 80, 41, 80);    //  lower right

    private Range xRange;
    private Range yRange;

    Quadrant(int xMin, int xMax, int yMin, int yMax) {
        this.xRange = Range.closed(xMin, xMax);
        this.yRange = Range.closed(yMin, yMax);
    }

    public Range getxRange() {
        return xRange;
    }

    public Range getyRange() {
        return yRange;
    }

    public boolean inQuadrant(XYPoint p) {
        return (xRange.contains(p.getX()) && yRange.contains(p.getY()));
    }

    static Quadrant findQuadrant(XYPoint p) {
        for (Quadrant q : values()) {
            if (q.inQuadrant(p)) return q;
        }
        // If point p is in no quadrant, return null
        return null;
    }

    public void setxRange(Range xRange) {
        this.xRange = xRange;
    }

    public void setyRange(Range yRange) {
        this.yRange = yRange;
    }
}
