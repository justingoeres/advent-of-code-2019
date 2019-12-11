package org.jgoeres.adventofcode2019.common;

import java.util.HashMap;
import java.util.Map;

import static org.jgoeres.adventofcode2019.common.Rotation.CLOCKWISE;
import static org.jgoeres.adventofcode2019.common.Rotation.COUNTERCLOCKWISE;

public enum Direction {
    UP("U"),
    RIGHT("R"),
    DOWN("D"),
    LEFT("L");

    private final String directionString;

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, Direction> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (Direction dir : Direction.values()) {
            lookup.put(dir.getDirectionString(), dir);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Direction get(String directionString) {
        return lookup.get(directionString);
    }

    Direction(String directionString) {
        this.directionString = directionString;
    }

    public String getDirectionString() {
        return directionString;
    }

    public Direction rotate(Rotation rotation) {
        int newDirection = this.ordinal();
        Direction newDirectionEnum = null;
        if (rotation == CLOCKWISE) {
            newDirection += 1;
            if (newDirection >= this.values().length) {
                // wrap it
                newDirection -= this.values().length;
            }
        }
        if (rotation == COUNTERCLOCKWISE) {
            newDirection -= 1;
            if (newDirection < 0) {
                // wrap it the other way
                newDirection += this.values().length;
            }
        }
        return Direction.values()[newDirection];
    }
}