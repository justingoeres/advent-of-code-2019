package org.jgoeres.adventofcode2019.Day24;

import org.jgoeres.adventofcode2019.common.XYZPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.jgoeres.adventofcode2019.Day24.Cell.*;

public class BugService {
    private final String DAY = "24";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final int AREA_SIZE = 5;

    private Area bugArea;
    private HashSet<Integer> history = new HashSet<>();
    private int generationCount = 0;
    private final boolean DISPLAY = false;

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

    public int countBugs() {
        int bugCount = 0;
        for (Map.Entry<XYZPoint, Cell> entry : bugArea.areaMap.entrySet()) {
            if (entry.getValue() == BUG) bugCount++;
        }
        return bugCount;
    }

    public void calculateNextGeneration() {
        // Before calculating the next generation, make sure we have any cells initialized (to empty)
        // along the outer & inner edges of the area.
        if (bugArea.recursive) {
            bugArea.initEdges();
        }
        bugArea.calculateNextAreaGeneration();
        // Commit the nextGen values to current
        bugArea.commitNextGenToCurrent();

        /*** DEBUG ***/
        if (DISPLAY) {
            generationCount++;
            System.out.println("After " + generationCount + " minutes:");
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

    public void printBugArea(Area bugArea, int depth) {
        bugArea.printArea(depth);
    }

    public void printAllLevels() {
        // Find max and min levels
        int topLevel = bugArea.findHighestLevel();
        int bottomLevel = bugArea.findDeepestLevel();
        for (int z = topLevel; z <= bottomLevel; z++) {
            printBugArea(bugArea, z);
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
            boolean recursive = initialSystemState.contains(RECURSION.getCharacter().toString());
            bugArea = new Area(AREA_SIZE, initialSystemState, 0, recursive);   // Initial layer is at depth 0.
            history.add(calculateBioDiversity());

            /*** DEBUG ***/
            if (DISPLAY) {
                // print initial state
                System.out.println("Initial state:");
//                printBugArea(bugArea);
                printAllLevels();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
