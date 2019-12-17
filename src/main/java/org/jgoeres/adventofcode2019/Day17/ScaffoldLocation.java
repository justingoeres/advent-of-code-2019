package org.jgoeres.adventofcode2019.Day17;


import java.util.HashMap;
import java.util.Map;

public enum ScaffoldLocation {
    SCAFFOLD('#'),
    EMPTY('.'),
    INTERSECTION('O'),
    LINEFEED('\n'),
    UNKNOWN(' ');

    private char locationChar;

    ScaffoldLocation(char locationChar) {
        this.locationChar = locationChar;
    }

    public char getLocationChar() {
        return locationChar;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<Character, ScaffoldLocation> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (ScaffoldLocation dir : ScaffoldLocation.values()) {
            lookup.put(dir.getLocationChar(), dir);
        }
    }

    //This method can be used for reverse lookup purpose
    public static ScaffoldLocation get(char scaffoldChar) {
        return lookup.get(scaffoldChar);
    }
}
