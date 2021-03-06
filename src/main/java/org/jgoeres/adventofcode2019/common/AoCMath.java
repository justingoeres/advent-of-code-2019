package org.jgoeres.adventofcode2019.common;

public abstract class AoCMath {
    public static final XYPoint ORIGIN = new XYPoint(0, 0);

    public static Integer manhattanDistance(XYPoint p0, XYPoint p1) {
        int xDistance = Math.abs(p0.getX() - p1.getX());
        int yDistance = Math.abs(p0.getY() - p1.getY());

        return (xDistance + yDistance);
    }
}
