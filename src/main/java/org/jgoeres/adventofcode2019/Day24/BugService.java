package org.jgoeres.adventofcode2019.Day24;

import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.XYZPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.jgoeres.adventofcode2019.Day24.Area.Position.BOTH;
import static org.jgoeres.adventofcode2019.Day24.Cell.*;

public class BugService {
    private final String DAY = "24";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final int AREA_SIZE = 5;

    private Area bugArea;
    private Area nextBugArea;
    private Area temp;
    private HashSet<Integer> history = new HashSet<>();
    private int generationCount = 0;
    private final boolean DISPLAY = true;

    private HashMap<Integer, Area> allAreas = new HashMap<>();
    private HashMap<Integer, Area> nextAreas = new HashMap<>();

    public BugService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public BugService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public int runUntilDuplicate() {
        // Run until we find a duplicate generation.
        int bioDiversity;
        while (true) {
            calculateNextGeneration();
            bioDiversity = calculateBioDiversity();
            // Add the new biodiversity score to the history, but bail out if it's a duplicate
            if (!history.add(bioDiversity)) break;
        }
        return bioDiversity;
        // Return its biodiversity score
    }

    public void runNGenerations(int numGenerations) {
        for (int t = 0; t < numGenerations; t++) {
            calculateNextGeneration();
            if (DISPLAY) {
                printAllLevels();
            }
        }
    }

    public void calculateNextGeneration() {
        Iterator iter = allAreas.keySet().iterator();
        while (iter.hasNext()) {
            Integer level = (Integer) iter.next();
            // TODO: Implement this:
            bugArea.calculateNextAreaGeneration(BOTH);
//
//            Area currentArea = allAreas.get(level);
//            Area nextArea = nextAreas.get(level);
//
//            if (currentArea instanceof AreaRecursive) {
//                AreaRecursive areaRecursive = (AreaRecursive) currentArea;
//                // Create any required new areas for recursion
//                if (areaRecursive.getAbove() == null) {
//                    // If this area doesn't have anything above, create it
//                    AreaRecursive newAbove = new AreaRecursive(AREA_SIZE, BELOW, areaRecursive);
//                    newAbove.setAtLocation(2, 2, RECURSION);
////                    allAreas.put(level - 1, newAbove);
//                    allAreas.put(level - 1, newAbove);
//                    // Also create a new next-tick placeholder
//                    nextAreas.put(level - 1, new AreaRecursive(AREA_SIZE, BELOW, areaRecursive));
//                }
//                if (areaRecursive.getBelow() == null) {
//                    // If this area doesn't have anything  below, create it
//                    AreaRecursive newBelow = new AreaRecursive(AREA_SIZE, ABOVE, areaRecursive);
//                    newBelow.setAtLocation(2, 2, RECURSION);
//                    allAreas.put(level + 1, newBelow);
//                    nextAreas.put(level + 1, new AreaRecursive(AREA_SIZE, ABOVE, areaRecursive));
//
//                }
//            }
//            // Calculate the next generation for each cell in this area.
//            for (int y = 0; y < AREA_SIZE; y++) {
//                for (int x = 0; x < AREA_SIZE; x++) {
//                    Cell nextBug = currentArea.calculateNextCellGeneration(x, y);
//                    nextArea.setAtLocation(x, y, nextBug);
//                }
//            }
//            /*** DEBUG ***/
//            if (DISPLAY) {
//                generationCount++;
//                System.out.println("After " + generationCount + " minutes:");
//                printBugArea(bugArea);
//            }

            // Switch the nextArea in as current
//            temp = bugArea;
//            bugArea = nextBugArea;
//            nextBugArea = temp;
        }
        // After we're done calculating the "next" of all areas, copy the *cell values* of the "nextAreas" back into "currentAreas"
//        for (Map.Entry<Integer, Area> area : nextAreas.entrySet()) {
//            Integer level = area.getKey();
//            for (int y = 0; y < AREA_SIZE; y++) {
//                for (int x = 0; x < AREA_SIZE; x++) {
//                    Cell nextValue = nextAreas.get(level).getAtLocation(x, y);
//                    allAreas.get(level).setAtLocation(x, y, nextValue);
//                }
//            }
//        }
        // Commit the nextGen values for every area
        bugArea.commitNextGenToCurrent(BOTH);

        /*** DEBUG ***/
        if (DISPLAY) {
            generationCount++;
            System.out.println("After " + generationCount + " minutes:");
            printBugArea(bugArea);
        }

        return;
    }

    public int calculateBioDiversity() {
        // To calculate the biodiversity rating for this layout,
        // consider each tile left-to-right in the top row, then left-to-right in the second row,
        // and so on. Each of these tiles is worth biodiversity points equal to
        // increasing powers of two: 1, 2, 4, 8, 16, 32, and so on.
        // Add up the biodiversity points for tiles with bugs.
        int cellScore = 0;
        int totalScore = 0;
////        for (Cell cell : bugArea.getAreaMap().entrySet()) {
//        for (Map.Entry<XYZPoint, Cell> entry : bugArea.getAreaMap().entrySet()) {
//            Cell cell = entry.getValue();
//            cellScore = (cellScore == 0) ? 1 : cellScore * 2;   // double the cellScore every time, starting from 1
//            if (cell.equals(BUG)) {
//                // if this is a bug, contribute it to the total score
//                totalScore += cellScore;
//            }
//        }
        int depth = 0;  // Part A only uses one level and it's 0.
        for (int y = 0; y < AREA_SIZE; y++) {
            for (int x = 0; x < AREA_SIZE; x++) {
                Cell cell = bugArea.getAtLocation(x, y, depth);
                cellScore = (cellScore == 0) ? 1 : cellScore * 2;   // double the cellScore every time, starting from 1
                if (cell.equals(BUG)) {
                    // if this is a bug, contribute it to the total score
                    totalScore += cellScore;
                }
            }
        }
        return totalScore;
    }

    //    public void printBugArea(Area bugArea) {
//        for (int y = 0; y < AREA_SIZE; y++) {
//            String line = StringUtils.EMPTY;
//            for (int x = 0; x < AREA_SIZE; x++) {
//                line += bugArea.getAtLocation(x, y).getCharacter();
//            }
//            System.out.println(line);
//        }
//        System.out.println(); // blank line at end
//    }
    public void printBugArea(Area bugArea) {
        bugArea.printArea(BOTH, 0); // TODO 0 is a hack
    }

    public void printAllLevels() {
        for (Map.Entry<Integer, Area> area : allAreas.entrySet()) {
            Integer level = area.getKey();
//            System.out.println("Depth " + level + ":");
            printBugArea(area.getValue());
        }

    }

    private void loadInputs(String pathToFile) {
        String initialSystemState = "";
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            // example:
            //  ##.#.
            //  #..#.
            //  .....
            //  ....#
            //  #.###
            String line;
            while ((line = br.readLine()) != null) {
                // read all lines into one long string
                initialSystemState += line;
            }
            if (!initialSystemState.contains(RECURSION.getCharacter().toString())) {
                // If the input does NOT contain a recursive cell
//                bugArea = new Area(AREA_SIZE, initialSystemState);
//                nextBugArea = new Area(AREA_SIZE, initialSystemState);
                bugArea = new Area(AREA_SIZE, initialSystemState, 0);   // Initial layers are at depth 0.
                nextBugArea = new Area(AREA_SIZE, initialSystemState, 0);
                history.add(calculateBioDiversity());
//            } else {
//                // We have recursion so create a recursive area
//                // Create the area with no neighbors
//                bugArea = new AreaRecursive(AREA_SIZE, initialSystemState);
//                nextBugArea = new AreaRecursive(AREA_SIZE, initialSystemState);
            }
            allAreas.put(0, bugArea);
            nextAreas.put(0, nextBugArea);
            /*** DEBUG ***/
            if (DISPLAY) {
                // print initial state
                System.out.println("Initial state:");
                printBugArea(bugArea);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
