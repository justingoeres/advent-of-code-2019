package org.jgoeres.adventofcode2019.Day13;

import java.util.HashMap;
import java.util.Map;

public enum Tile {
    EMPTY(0,' '),
    WALL(1,'#'),
    BLOCK(2,'['),
    PADDLE(3,'='),
    BALL(4,'*');

    private int intTile;
    char glyph;

    Tile(int intTile, char glyph) {
        this.intTile = intTile;
        this.glyph = glyph;
    }

    public int getIntTile() {
        return intTile;
    }

    public char getGlyph() {
        return glyph;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<Integer, Tile> lookup = new HashMap<Integer, Tile>();

    //Populate the lookup table on loading time
    static {
        for (Tile dir : Tile.values()) {
            lookup.put(dir.getIntTile(), dir);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Tile get(int intTile) {
        return lookup.get(intTile);
    }
}
