package org.jgoeres.adventofcode2019.Day24;

import org.jgoeres.adventofcode2019.common.DirectionURDL;

import java.util.ArrayList;

import static org.jgoeres.adventofcode2019.Day24.AreaRecursive.Position.ABOVE;
import static org.jgoeres.adventofcode2019.Day24.AreaRecursive.Position.BELOW;
import static org.jgoeres.adventofcode2019.Day24.Cell.EMPTY;
import static org.jgoeres.adventofcode2019.Day24.Cell.RECURSION;

public class AreaRecursive extends Area {
    AreaRecursive above;
    AreaRecursive below;

    public AreaRecursive(Integer areaSize, String areaString) {
        super(areaSize, areaString);
    }

    public AreaRecursive(Integer areaSize, Position positionOfNeighbor, AreaRecursive neighborArea) {
        super(areaSize);
        switch (positionOfNeighbor) {
            case ABOVE:
                setAbove(neighborArea);
                break;
            case BELOW:
                setBelow(neighborArea);
        }
    }

    private Cell getLocationAbove(int x, int y) {
        // Get the specified location from the level above,
        // creating the level if we have to
        if (above != null) {
            return above.getAtLocation(x, y);
        } else {
            // 'above' does not exist, so create it with ourselves in the below position
            above = new AreaRecursive(getAreaSize(), BELOW, this);
            // then return EMPTY because the new level is created empty
            above.setAtLocation(2,2,RECURSION);
            return EMPTY;
        }
    }

    private Cell getLocationBelow(int x, int y) {
        // Get the specified location from the level above,
        // creating the level if we have to
        if (below != null) {
            return below.getAtLocation(x, y);
        } else {
            // 'below' does not exist, so create it with ourselves in the above position
            below = new AreaRecursive(getAreaSize(), ABOVE, this);
            // Put a recursive spot in the new level
            below.setAtLocation(2,2,RECURSION);
            // then return EMPTY because the new level is created empty
            return EMPTY;
        }
    }

    @Override
    public ArrayList<Cell> getAdjacentCells(int x, int y) {
        // Return the adjacent Cells to this one, accounting for recursive levels above or below as necesary
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        for (DirectionURDL direction : DirectionURDL.values()) {
            // Check all four directions
            Cell cell = null;
            switch (direction) {
                case UP:
                    if (y == 0) {
                        // If along the top row, then "up" is (2,1) of the next recursive level up
                        cell = getLocationAbove(2, 1);
                        adjacentCells.add(cell);
                    } else if (x == 2 && y == 3) {
                        // If at 2,3 (below center), then "up" is the whole bottom row of the new recursive level DOWN
                        for (int xNeighbor = 0; xNeighbor < getAreaSize(); xNeighbor++) {
                            cell = getLocationBelow(xNeighbor, 4);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x, y - 1));
                    }
                    break;

                case DOWN:
                    if (y == 4) {
                        // If along the bottom row, then "down" is (2,3) of the next recursive level up
                        cell = getLocationAbove(2, 3);
                        adjacentCells.add(cell);
                    } else if (x == 2 && y == 1) {
                        // If at 2,1 (above center), then "down" is the whole top row of the new recursive level DOWN
                        for (int xNeighbor = 0; xNeighbor < getAreaSize(); xNeighbor++) {
                            cell = getLocationBelow(xNeighbor, 0);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x, y + 1));
                    }
                    break;

                case LEFT:
                    if (x == 0) {
                        // If along the left column, then "left" is (1,2) of the next recursive level up
                        cell = getLocationAbove(1, 2);
                        adjacentCells.add(cell);
                    } else if (x == 3 && y == 2) {
                        // If at 3,2 (right of center), then "left" is the whole right column of the new recursive level DOWN
                        for (int yNeighbor = 0; yNeighbor < getAreaSize(); yNeighbor++) {
                            cell = getLocationBelow(4, yNeighbor);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x - 1, y));
                    }
                    break;

                case RIGHT:
                    if (x == 4) {
                        // If along the right column, then "right" is (3,2) of the next recursive level up
                        cell = getLocationAbove(3, 2);
                        adjacentCells.add(cell);
                    } else if (x == 1 && y == 2) {
                        // If at 3,2 (left of center), then "right" is the whole left column of the new recursive level DOWN
                        for (int yNeighbor = 0; yNeighbor < getAreaSize(); yNeighbor++) {
                            cell = getLocationBelow(0, yNeighbor);
                            adjacentCells.add(cell);
                        }
                    } else {
                        // else we're not in any special place, so just get the value of the cell
                        adjacentCells.add(getAtLocation(x + 1, y));
                    }
                    break;

            }
        }
        return adjacentCells;
    }

    public AreaRecursive findTopArea() {
        // Scales the stack and finds the topmost area, then returns it
        AreaRecursive area = this;
        while ((area.above) != null) {
            area = area.above;
        }
        return area;
    }

    public AreaRecursive getAbove() {
        return above;
    }

    public void setAbove(AreaRecursive above) {
        this.above = above;
    }

    public AreaRecursive getBelow() {
        return below;
    }

    public void setBelow(AreaRecursive below) {
        this.below = below;
    }

    enum Position {
        ABOVE,
        BELOW;
    }
}


