package org.jgoeres.adventofcode2019.Day24;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import static org.jgoeres.adventofcode2019.Day24.Cell.BUG;
import static org.jgoeres.adventofcode2019.Day24.Cell.EMPTY;

public class BugService {
    private final String DAY = "24";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final int AREA_SIZE = 5;

    private Area bugArea;
    private Area nextBugArea;
    private Area temp;
    private ArrayList<Cell> adjacentCells = new ArrayList<>();
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
        }
    }

    public void calculateNextGeneration() {
        // Calculate the next generation for each cell.
        for (int y = 0; y < AREA_SIZE; y++) {
            for (int x = 0; x < AREA_SIZE; x++) {
                Cell nextBug = calculateNextCellGeneration(x, y);
                nextBugArea.setAtLocation(x, y, nextBug);
            }
        }
        /*** DEBUG ***/
        if (DISPLAY) {
            generationCount++;
            System.out.println("After " + generationCount + " minutes:");
            printBugArea();
        }

        // Switch the nextArea in as current
        temp = bugArea;
        bugArea = nextBugArea;
        nextBugArea = temp;

        return;
    }

    public Cell calculateNextCellGeneration(int x, int y) {
        // A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
        // An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
        // Otherwise, a bug or empty space remains the same.

        Cell currentCell = bugArea.getAtLocation(x, y);
        adjacentCells.clear();  // clear the list so we can populate it
        // Check all for directions
        int[] dP = {-1, 1};
        for (int dp : dP) {
            // left & right
            adjacentCells.add(bugArea.getAtLocation(x + dp, y));
        }
        for (int dp : dP) {
            // up & down
            adjacentCells.add(bugArea.getAtLocation(x, y + dp));
        }
        // Having gotten all the adjacent cells, figure out the current cell's next generation
        Cell nextCell = currentCell;    // by default, the cell remains what it is
        int bugCount = 0;
        switch (currentCell) {
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

    public int calculateBioDiversity() {
        // To calculate the biodiversity rating for this layout,
        // consider each tile left-to-right in the top row, then left-to-right in the second row,
        // and so on. Each of these tiles is worth biodiversity points equal to
        // increasing powers of two: 1, 2, 4, 8, 16, 32, and so on.
        // Add up the biodiversity points for tiles with bugs.
        int cellScore = 0;
        int totalScore = 0;
        for (Cell cell : bugArea.getAreaMap()) {
            cellScore = (cellScore == 0) ? 1 : cellScore * 2;   // double the cellScore every time, starting from 1
            if (cell.equals(BUG)) {
                // if this is a bug, contribute it to the total score
                totalScore += cellScore;
            }
        }
        return totalScore;
    }

    public void printBugArea() {
        for (int y = 0; y < AREA_SIZE; y++) {
            String line = StringUtils.EMPTY;
            for (int x = 0; x < AREA_SIZE; x++) {
                line += bugArea.getAtLocation(x, y).getCharacter();
            }
            System.out.println(line);
        }
        System.out.println(); // blank line at end
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
            bugArea = new Area(AREA_SIZE, initialSystemState);
            nextBugArea = new Area(AREA_SIZE, initialSystemState);
            history.add(calculateBioDiversity());

            /*** DEBUG ***/
            if (DISPLAY) {
                // print initial state
                System.out.println("Initial state:");
                printBugArea();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
