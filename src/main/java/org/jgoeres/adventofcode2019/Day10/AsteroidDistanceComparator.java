package org.jgoeres.adventofcode2019.Day10;

import org.jgoeres.adventofcode2019.common.XYPoint;

import java.util.Comparator;

import static org.jgoeres.adventofcode2019.common.AoCMath.manhattanDistance;

public class AsteroidDistanceComparator implements Comparator<XYPoint> {
    XYPoint c;

    // Used for sorting in ascending order of
    // disance to point c
    public int compare(XYPoint a, XYPoint b) {
        // Sort by angle first, not by XYPoint
        // if (!a.getAngleInRadians().equals(b.getAngleInRadians())) {
//            return a.getAngleInRadians().compareTo(b.getAngleInRadians());
        //    } //else {
        // else they're at the same angle, so the one "closer to the center" is LESS

        // }
        int compareDistance = manhattanDistance(a, c)
                .compareTo(manhattanDistance(b, c));
        return compareDistance;
    }

    public AsteroidDistanceComparator(XYPoint c) {
        this.c = c;
    }

}
