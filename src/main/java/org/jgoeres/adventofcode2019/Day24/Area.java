package org.jgoeres.adventofcode2019.Day24;

import org.jgoeres.adventofcode2019.Day10.AsteroidVector;

import java.util.ArrayList;
import java.util.Objects;

import static org.jgoeres.adventofcode2019.Day24.Cell.EMPTY;

public class Area {
    private final Integer areaSize;
    ArrayList<Cell> areaMap = new ArrayList<>();

    public Area(Integer areaSize) {
        this.areaSize = areaSize;
        // Fill the new area with 'empty'
        for (int y = 0; y < areaSize; y++) {
            for (int x = 0; x < areaSize; x++) {
                areaMap.add(EMPTY);
            }
        }
    }

    public Area(Integer areaSize, String areaString) {
        this.areaSize = areaSize;
        for (int i = 0; i < areaString.length(); i++) {
            areaMap.add(i, Cell.get(areaString.charAt(i)));
        }
    }

    public Area(Integer areaSize, ArrayList<Cell> areaMap) {
        this.areaSize = areaSize;
        this.areaMap = areaMap;
    }

    public ArrayList<Cell> getAreaMap() {
        return areaMap;
    }

    public ArrayList<Cell> getAdjacentCells(int x, int y) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        // Check all for directions
        int[] dP = {-1, 1};
        for (int dp : dP) {
            // left & right
            adjacentCells.add(getAtLocation(x + dp, y));
        }
        for (int dp : dP) {
            // up & down
            adjacentCells.add(getAtLocation(x, y + dp));
        }
        return adjacentCells;
    }

    public Cell getAtLocation(int x, int y) {
        try {
            // Brute-force range check
            if (x < 0 || x >= areaSize
                    || y < 0 || y >= areaSize) return EMPTY;
            return areaMap.get(xyToIndex(x, y));
        } catch (IndexOutOfBoundsException e) {
            // Cells that don't exist are empty.
            return EMPTY;
        }
    }

    public void setAtLocation(int x, int y, Cell cellContents) {
        try {
            areaMap.set(xyToIndex(x, y), cellContents);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer getAreaSize() {
        return areaSize;
    }

    private int xyToIndex(int x, int y) {
        int index = y * areaSize + x;
        return index;
    }

    @Override
    public int hashCode() {
        String areaString = areaMap.toString();
        return Objects.hash(areaSize, areaString);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Area))
            return false;
        if (obj == this)
            return true;
        // Two areas are equivalent if they have the same size and contents
        return ((this.areaSize == ((Area) obj).areaSize)
                && this.areaMap.equals(((Area) obj).areaMap));
    }
}
