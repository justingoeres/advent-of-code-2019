package org.jgoeres.adventofcode2019.Day24;

import org.apache.commons.lang3.StringUtils;
import org.jgoeres.adventofcode2019.common.XYZPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.jgoeres.adventofcode2019.Day24.Area.Position.*;
import static org.jgoeres.adventofcode2019.Day24.Cell.*;
import static org.jgoeres.adventofcode2019.Day24.Cell.BUG;

public class Area {
    Area above;
    Area below;

    private final Integer areaSize;
    ArrayList<Cell> areaMapToRefactor = new ArrayList<>();
    ArrayList<Cell> nextGenAreaToRefactor = new ArrayList<>(); // maybe can leave this null
    ArrayList<Cell> tempToRefactor;

    HashMap<XYZPoint, Cell> areaMap = new HashMap<>();
    HashMap<XYZPoint, Cell> nextGenArea = new HashMap<>(); // maybe can leave this null
    HashMap<XYZPoint, Cell> temp;


    enum Position {
        ABOVE,
        BELOW,
        BOTH,
        NEITHER
    }

    public Area(Integer areaSize, Integer depth) {
        this.areaSize = areaSize;
        // Fill the new area with 'empty'
        for (int y = 0; y < areaSize; y++) {
            for (int x = 0; x < areaSize; x++) {
                XYZPoint xyz = new XYZPoint(x, y, depth);
//                areaMap.add(EMPTY);
//                nextGenArea.add(EMPTY);
                areaMap.put(xyz, EMPTY);
                nextGenArea.put(xyz, EMPTY);
            }
        }
    }

    public Area(Integer areaSize, String areaString, Integer depth) {
        this.areaSize = areaSize;
        for (int i = 0; i < areaString.length(); i++) {
            int y = i / areaSize;
            int x = i % areaSize;
            XYZPoint xyz = new XYZPoint(x, y, depth);

//            areaMap.add(i, Cell.get(areaString.charAt(i)));
            areaMap.put(xyz, Cell.get(areaString.charAt(i)));
        }
    }

    //    public Area(Integer areaSize, ArrayList<Cell> areaMap) {
    public Area(Integer areaSize, HashMap<XYZPoint, Cell> areaMap) {
        this.areaSize = areaSize;
        this.areaMap = areaMap;
    }

    public HashMap<XYZPoint, Cell> getAreaMap() {
        return areaMap;
    }

    protected HashMap<XYZPoint, Cell> getNextGenArea() {
        return nextGenArea;
    }

//    public void calculateNextAreaGeneration(Position direction) {
//        // Calculate the next generation for this layer and all its neighbor layers (above & below)
//        nextGenArea.clear();
//        if ((direction == ABOVE || direction == Position.BOTH) && above != null) {
//            // if there's an area above this one, calculate it
//            above.calculateNextAreaGeneration(ABOVE);   // only go up so we don't recurse back into ourselves
//        }
//        if ((direction == Position.BELOW || direction == Position.BOTH) && below != null) {
//            // if there's an area below this one, calculate it
//            below.calculateNextAreaGeneration(BELOW);   // only go down so we don't recurse back into ourselves
//        }
//        for (int y = 0; y < areaSize; y++) {
//            for (int x = 0; x < areaSize; x++) {
//                Cell nextGenCell = calculateNextCellGeneration(x, y);
//                nextGenArea.add(nextGenCell);
//            }
//        }
//    }

    public void calculateNextAreaGeneration(Position direction) {
        // Calculate the next generation for this layer and all its neighbor layers (above & below)
        nextGenArea.clear();

        for (Map.Entry<XYZPoint, Cell> location : areaMap.entrySet()) {
            XYZPoint xyz = location.getKey();
            // For each cell in the whole area
            Cell nextGenCell = calculateNextCellGeneration(xyz.getX(), xyz.getY(), xyz.getZ());
            nextGenArea.put(xyz, nextGenCell);
        }
    }

    public void commitNextGenToCurrent(Position direction) {
        if ((direction == ABOVE || direction == Position.BOTH) && above != null) {
            // if there's an area above this one, calculate it
            above.commitNextGenToCurrent(ABOVE);   // only go up so we don't recurse back into ourselves
        }
        if ((direction == Position.BELOW || direction == Position.BOTH) && below != null) {
            // if there's an area below this one, calculate it
            below.commitNextGenToCurrent(BELOW);   // only go down so we don't recurse back into ourselves
        }
        // switch the next generation in as current for the next iteration
        temp = areaMap;
        areaMap = nextGenArea;
        nextGenArea = temp;
    }

    public Cell calculateNextCellGeneration(int x, int y, int depth) {
        // A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
        // An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
        // Otherwise, a bug or empty space remains the same.

        Cell currentCell = this.getAtLocation(x, y, depth);

        ArrayList<Cell> adjacentCells = this.getAdjacentCells(x, y, depth);

        // Having gotten all the adjacent cells, figure out the current cell's next generation
        Cell nextCell = currentCell;    // by default, the cell remains what it is
        int bugCount = 0;
        switch (currentCell) {
            case RECURSION:
                // Recursion cells stay as recursion
                nextCell = RECURSION;
                break;
            case BUG:
                // A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
                for (Cell cell : adjacentCells) {
                    if (cell.equals(BUG)) bugCount++;
                    if (bugCount > 1) break;    // If we've got more than one, quit looking
                }
                nextCell = (bugCount == 1) ? BUG : EMPTY;
                break;
            case EMPTY:
                // An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
                for (Cell cell : adjacentCells) {
                    if (cell.equals(BUG)) bugCount++;
                    if (bugCount > 2) break;    // If we've got more than one, quit looking
                }
                nextCell = (bugCount == 1 || bugCount == 2) ? BUG : EMPTY;
                break;
        }
        return nextCell;
    }

    public ArrayList<Cell> getAdjacentCells(int x, int y, int depth) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        // Check all for directions
        int[] dP = {-1, 1};
        for (int dp : dP) {
            // left & right
            adjacentCells.add(getAtLocation(x + dp, y, depth));
        }
        for (int dp : dP) {
            // up & down
            adjacentCells.add(getAtLocation(x, y + dp, depth));
        }
        return adjacentCells;
    }

    public Cell getAtLocation(int x, int y, int depth) {
        try {
            XYZPoint xyz = new XYZPoint(x, y, depth);
            // Brute-force range check
            if (x < 0 || x >= areaSize
                    || y < 0 || y >= areaSize) return EMPTY;
//            return areaMap.get(xyToIndex(x, y));
            return areaMap.get(xyz);
        } catch (IndexOutOfBoundsException e) {
            // Cells that don't exist are empty.
            return EMPTY;
        }
    }

    //    public void setAtLocation(int x, int y, Cell cellContents) {
    public void setAtLocation(int x, int y, int depth, Cell cellContents) {
        try {
//            areaMap.set(xyToIndex(x, y), cellContents);
            XYZPoint xyz = new XYZPoint(x, y, depth);
//            areaMap.set(xyToIndex(x, y), cellContents);
            areaMap.put(xyz, cellContents);
        } catch (IndexOutOfBoundsException e) { // This exception is probably irrelevant now
            System.out.println(e.getMessage());
        }
    }

    public Integer getAreaSize() {
        return areaSize;
    }

    protected int xyToIndex(int x, int y) {
        int index = y * areaSize + x;
        return index;
    }

    public void printArea(Position direction, Integer depth) {
        if ((direction == ABOVE || direction == Position.BOTH) && above != null) {
            // if there's an area above this one, calculate it
            above.printArea(ABOVE, depth - 1);   // only go up so we don't recurse back into ourselves
        }
        if ((direction == Position.BELOW || direction == Position.BOTH) && below != null) {
            // if there's an area below this one, calculate it
            below.printArea(BELOW, depth + 1);   // only go down so we don't recurse back into ourselves
        }
        System.out.println("Depth " + depth + ":");
        for (int y = 0; y < areaSize; y++) {
            String line = StringUtils.EMPTY;
            for (int x = 0; x < areaSize; x++) {
                line += getAtLocation(x, y, depth).getCharacter();
            }
            System.out.println(line);
        }
        System.out.println(); // blank line at end
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
