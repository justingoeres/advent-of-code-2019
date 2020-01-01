package org.jgoeres.adventofcode2019.Day24;

import org.apache.commons.lang3.StringUtils;
import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.XYZPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.jgoeres.adventofcode2019.Day24.Cell.*;

public class Area {
    private final Integer areaSize;

    ArrayList<XYPoint> OUTER_EDGE = new ArrayList<>();
    ArrayList<XYPoint> INNER_EDGE = new ArrayList<>();

    HashMap<XYZPoint, Cell> areaMap = new HashMap<>();
    HashMap<XYZPoint, Cell> nextGenArea = new HashMap<>(); // maybe can leave this null
    HashMap<XYZPoint, Cell> temp;
    boolean recursive;

    {
        // Setup the constant ArrayLists for inner & outer edge
        int DEFAULT_AREA_SIZE = 5;
        for (int y = 0; y < DEFAULT_AREA_SIZE; y++) {
            for (int x = 0; x < DEFAULT_AREA_SIZE; x++) {
                if ((x == 0 || x == 4 || y == 0 || y == 4)) {
                    // If this cell is on an outer edge
                    OUTER_EDGE.add(new XYPoint(x, y));
                } else if (x == 2 && y == 2) {
                    // skip the center -- it's the recursion point
                } else if ((x == 2 || y == 2)) {
                    // If this cell is on an inner edge
                    INNER_EDGE.add(new XYPoint(x, y));
                }
            }
        }
    }

    public Area(Integer areaSize, Integer depth) {
        this.areaSize = areaSize;
        // Fill the new area with 'empty'
        for (int y = 0; y < areaSize; y++) {
            for (int x = 0; x < areaSize; x++) {
                XYZPoint xyz = new XYZPoint(x, y, depth);
                areaMap.put(xyz, EMPTY);
                nextGenArea.put(xyz, EMPTY);
            }
        }
    }

    public Area(Integer areaSize, String areaString, Integer depth, boolean recursive) {
        this.areaSize = areaSize;
        this.recursive = recursive;
        for (int i = 0; i < areaString.length(); i++) {
            int y = i / areaSize;
            int x = i % areaSize;
            XYZPoint xyz = new XYZPoint(x, y, depth);

            areaMap.put(xyz, Cell.get(areaString.charAt(i)));
        }
    }

    public Area(Integer areaSize, HashMap<XYZPoint, Cell> areaMap) {
        this.areaSize = areaSize;
        this.areaMap = areaMap;
    }

    public HashMap<XYZPoint, Cell> getAreaMap() {
        return areaMap;
    }

    public void calculateNextAreaGeneration() {
        // Calculate the next generation for this layer and all its neighbor layers (above & below)
        nextGenArea.clear();

        for (Map.Entry<XYZPoint, Cell> location : areaMap.entrySet()) {
            XYZPoint xyz = location.getKey();
            // For each cell in the whole area
            Cell nextGenCell = calculateNextCellGeneration(xyz.getX(), xyz.getY(), xyz.getZ());
            nextGenArea.put(xyz, nextGenCell);
        }
    }

    public void commitNextGenToCurrent() {
        // switch the next generation in as current for the next iteration
        temp = areaMap;
        areaMap = nextGenArea;
        nextGenArea = temp;
    }

    public Cell calculateNextCellGeneration(int x, int y, int depth) {
        Cell currentCell = this.getAtLocation(x, y, depth);

        ArrayList<Cell> adjacentCells;
        if (!recursive) {
            adjacentCells = this.getAdjacentCells(x, y, depth);
        } else {
            adjacentCells = this.getAdjacentCellsRecursive(x, y, depth);
        }
        Cell nextCell = applyGenerationRules(currentCell, adjacentCells);

        return nextCell;
    }

    public int findDeepestLevel() {
        // Find max level (downward)
        int maxLevel = Integer.MIN_VALUE;
        for (XYZPoint xyz : areaMap.keySet()) {
            int depth = xyz.getZ();
            if (depth > maxLevel) maxLevel = depth;
        }
        return maxLevel;
    }

    public int findHighestLevel() {
        // Find min level (upward)
        int minLevel = Integer.MAX_VALUE;
        for (XYZPoint xyz : areaMap.keySet()) {
            int depth = xyz.getZ();
            if (depth < minLevel) minLevel = depth;
        }
        return minLevel;
    }

    public boolean bugsOnTopEdge() {
        // check the "outer"/"top" edge of the current areaMap.
        // If there are _any_ bugs in it, return true.
        boolean bugsFound = false;  // assume no bugs
        int minLevel = findHighestLevel();  // Find the top level
        // Look around the edge to see if there are bugs
        for (XYPoint xy : OUTER_EDGE) {
            // Check all the outer edge points on the max level
            XYZPoint xyz = new XYZPoint(xy.getX(), xy.getY(), minLevel);
            if (areaMap.get(xyz) == BUG) {
                // If this cell is a bug, then we found one and can stop looking
                bugsFound = true;
                break;
            }
        }
        return bugsFound;
    }

    public boolean bugsOnBottomEdge() {
        // check the "outer"/"top" edge of the current areaMap.
        // If there are _any_ bugs in it, return true.
        boolean bugsFound = false;  // assume no bugs
        int maxLevel = findDeepestLevel();  // Find the top level
        // Look around the edge to see if there are bugs
        for (XYPoint xy : INNER_EDGE) {
            // Check all the outer edge points on the min level
            XYZPoint xyz = new XYZPoint(xy.getX(), xy.getY(), maxLevel);
            if (areaMap.get(xyz) == BUG) {
                // If this cell is a bug, then we found one and can stop looking
                bugsFound = true;
                break;
            }
        }
        return bugsFound;
    }

    public void initEdges() {
        // If any outer or inner edge cells have bugs in them, then add a new level
        // above or below, respectively

        if (bugsOnTopEdge()) {
            int newDepth = findHighestLevel() - 1;
            // If there are bugs on the top (outer) edge, add a new *inner* edge on a new level above
            Area newLevel = new Area(areaSize, newDepth); // Create it as an area
            newLevel.setAtLocation(2, 2, newDepth, RECURSION);
            areaMap.putAll(newLevel.getAreaMap());  // Then add its contents to the current area
        }

        if (bugsOnBottomEdge()) {
            int newDepth = findDeepestLevel() + 1;
            // If there are bugs on the top (outer) edge, add a new *inner* edge on a new level above
            Area newLevel = new Area(areaSize, newDepth); // Create it as an area
            newLevel.setAtLocation(2, 2, newDepth, RECURSION);
            areaMap.putAll(newLevel.getAreaMap());  // Then add its contents to the current area
        }
    }

    private Cell applyGenerationRules(Cell currentCell, ArrayList<Cell> adjacentCells) {
        // Having gotten all the adjacent cells, figure out the current cell's next generation

        // A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
        // An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
        // Otherwise, a bug or empty space remains the same.

        int bugCount = 0;
        Cell nextCell = currentCell;    // by default, the cell remains what it is
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
            Cell cell = areaMap.get(xyz);
            if (cell != null) {
                return cell;
            } else {
                // This is a cell we've never seen before, so create it as EMPTY and add it to the nextGenArea
                // so we track it going forward
                cell = EMPTY;
                nextGenArea.put(xyz, cell);
                return cell;
            }
//            return areaMap.get(xyz);
        } catch (IndexOutOfBoundsException e) {
            // Cells that don't exist are empty.
            return EMPTY;
        }
    }

    public ArrayList<Cell> getAdjacentCellsRecursive(int x, int y, int depth) {
        // Return the adjacent Cells to this one, accounting for recursive levels above or below as necesary
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        for (DirectionURDL direction : DirectionURDL.values()) {
            // Check all four directions
            Cell cell = null;
            switch (direction) {
                case UP:
                    if (y == 0) {
                        // If along the top row, then "up" is (2,1) of the next recursive level up
                        cell = getLocationAbove(2, 1, depth);
                        adjacentCells.add(cell);
                    } else if (x == 2 && y == 3) {
                        // If at 2,3 (below center), then "up" is the whole bottom row of the new recursive level DOWN
                        for (int xNeighbor = 0; xNeighbor < getAreaSize(); xNeighbor++) {
                            cell = getLocationBelow(xNeighbor, 4, depth);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x, y - 1, depth));
                    }
                    break;

                case DOWN:
                    if (y == 4) {
                        // If along the bottom row, then "down" is (2,3) of the next recursive level up
                        cell = getLocationAbove(2, 3, depth);
                        adjacentCells.add(cell);
                    } else if (x == 2 && y == 1) {
                        // If at 2,1 (above center), then "down" is the whole top row of the new recursive level DOWN
                        for (int xNeighbor = 0; xNeighbor < getAreaSize(); xNeighbor++) {
                            cell = getLocationBelow(xNeighbor, 0, depth);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x, y + 1, depth));
                    }
                    break;

                case LEFT:
                    if (x == 0) {
                        // If along the left column, then "left" is (1,2) of the next recursive level up
                        cell = getLocationAbove(1, 2, depth);
                        adjacentCells.add(cell);
                    } else if (x == 3 && y == 2) {
                        // If at 3,2 (right of center), then "left" is the whole right column of the new recursive level DOWN
                        for (int yNeighbor = 0; yNeighbor < getAreaSize(); yNeighbor++) {
                            cell = getLocationBelow(4, yNeighbor, depth);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x - 1, y, depth));
                    }
                    break;

                case RIGHT:
                    if (x == 4) {
                        // If along the right column, then "right" is (3,2) of the next recursive level up
                        cell = getLocationAbove(3, 2, depth);
                        adjacentCells.add(cell);
                    } else if (x == 1 && y == 2) {
                        // If at 3,2 (left of center), then "right" is the whole left column of the new recursive level DOWN
                        for (int yNeighbor = 0; yNeighbor < getAreaSize(); yNeighbor++) {
                            cell = getLocationBelow(0, yNeighbor, depth);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x + 1, y, depth));
                    }
                    break;

            }
        }
        return adjacentCells;
    }

    private Cell getLocationAbove(int x, int y, int depth) {
        // Get the specified location from the level above,
        // creating the level if we have to
        int newDepth = depth - 1;
        XYZPoint xyz = new XYZPoint(x, y, newDepth);
        Cell aboveCell = areaMap.get(xyz);
        if (aboveCell != null) {
            // Return the cell above, if it exists
            return aboveCell;
        } else {
            aboveCell = EMPTY;
            return aboveCell;
        }
    }

    private Cell getLocationBelow(int x, int y, int depth) {
        // Get the specified location from the level above,
        // creating the level if we have to
        int newDepth = depth + 1;
        XYZPoint xyz = new XYZPoint(x, y, newDepth);
        Cell belowCell = areaMap.get(xyz);
        if (belowCell != null) {
            // Return the cell above, if it exists
            return belowCell;
        } else {
            belowCell = EMPTY;
            // No longer need to add nonexistent cells when querying; we add new edges at the beginning of each generation
//            nextGenArea.put(xyz, belowCell);    // TODO This is wrong. Need to actually *calculate* the nextGen for new cells
            return belowCell;
        }
    }

    public void setAtLocation(int x, int y, int depth, Cell cellContents) {
        try {
            XYZPoint xyz = new XYZPoint(x, y, depth);
            areaMap.put(xyz, cellContents);
        } catch (IndexOutOfBoundsException e) { // This exception is probably irrelevant now
            System.out.println(e.getMessage());
        }
    }

    public Integer getAreaSize() {
        return areaSize;
    }

    public void printArea(Integer depth) {
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
