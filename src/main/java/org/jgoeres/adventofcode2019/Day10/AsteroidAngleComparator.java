package org.jgoeres.adventofcode2019.Day10;

import java.util.Comparator;

public class AsteroidAngleComparator implements Comparator<AsteroidAngle> {
    // Used for sorting in ascending order of
    // angleInRadians
    public int compare(AsteroidAngle a, AsteroidAngle b) {
        // Sort by angle first, not by XYPoint
       // if (!a.getAngleInRadians().equals(b.getAngleInRadians())) {
            return a.getAngleInRadians().compareTo(b.getAngleInRadians());
    //    } //else {
        // else they're at the same angle, so the one "closer to the center" is LESS

       // }
    }
}
