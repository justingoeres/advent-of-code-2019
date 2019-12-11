package org.jgoeres.adventofcode2019.Day11;

import java.util.HashMap;
import java.util.Map;

public enum Color {
    BLACK(0L),
    WHITE(1L);

    private long intColor;

    Color(long intColor) {
        this.intColor = intColor;
    }

    public long getIntColor() {
        return intColor;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<Long, Color> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (Color dir : Color.values()) {
            lookup.put(dir.getIntColor(), dir);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Color get(long intColor) {
        return lookup.get(intColor);
    }
}
