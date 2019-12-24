package org.jgoeres.adventofcode2019.Day24;

import java.util.HashMap;
import java.util.Map;

public enum Cell {
    EMPTY('.'),
    BUG('#'),
    RECURSION('?');

    private Character character;

    Cell(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<Character, Cell> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (Cell cell : Cell.values()) {
            lookup.put(cell.getCharacter(), cell);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Cell get(Character c) {
        return lookup.get(c);
    }
}
