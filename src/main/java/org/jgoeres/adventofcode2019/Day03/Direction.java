package org.jgoeres.adventofcode2019.Day03;

import java.util.HashMap;
import java.util.Map;

public enum Direction
{
    UP("U"),
    RIGHT("R"),
    DOWN("D"),
    LEFT("L");

    private final String directionString;

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, Direction> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static
    {
        for(Direction dir : Direction.values())
        {
            lookup.put(dir.getDirectionString(), dir);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Direction get(String directionString)
    {
        return lookup.get(directionString);
    }
    Direction(String directionString) {
        this.directionString = directionString;
    }

    public String getDirectionString() {
        return directionString;
    }

}