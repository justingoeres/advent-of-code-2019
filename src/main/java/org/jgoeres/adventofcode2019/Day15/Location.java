package org.jgoeres.adventofcode2019.Day15;


import java.util.HashMap;
import java.util.Map;

public enum Location {
    WALL(0L),
    EMPTY(1L),
    OXYGEN(2L),
    UNKNOWN(99L);

    private long intLocation;

    Location(long intLocation) {
        this.intLocation = intLocation;
    }

    public long getIntLocation() {
        return intLocation;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<Long, Location> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (Location dir : Location.values()) {
            lookup.put(dir.getIntLocation(), dir);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Location get(long intLocation) {
        return lookup.get(intLocation);
    }
}
