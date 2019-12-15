package org.jgoeres.adventofcode2019.Day15;

import org.jgoeres.adventofcode2019.common.Rotation;

import java.util.HashMap;
import java.util.Map;

import static org.jgoeres.adventofcode2019.common.Rotation.CLOCKWISE;
import static org.jgoeres.adventofcode2019.common.Rotation.COUNTERCLOCKWISE;

public enum Direction {
    NORTH(1L),
    EAST(4L),
    SOUTH(2L),
    WEST(3L);

    private final Long directionInt;

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<Long, Direction> lookup = new HashMap<Long, Direction>();

    //Populate the lookup table on loading time
    static {
        for (Direction dir : Direction.values()) {
            lookup.put(dir.getDirectionInt(), dir);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Direction get(String directionString) {
        return lookup.get(directionString);
    }

    Direction(Long directionInt) {
        this.directionInt = directionInt;
    }

    public Long getDirectionInt() {
        return directionInt;
    }

    public Direction opposite(){
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }
        return null;
    }

    public Direction rotate(Rotation rotation) {
        int newDirection = this.ordinal();
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