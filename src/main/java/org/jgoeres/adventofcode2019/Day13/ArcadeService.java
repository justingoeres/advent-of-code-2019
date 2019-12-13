package org.jgoeres.adventofcode2019.Day13;

import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.HashMap;

import static org.jgoeres.adventofcode2019.Day13.Tile.*;

public class ArcadeService {
    private final String DAY = "13";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private IntCodeProcessorService intCodeProcessorService;
    private HashMap<XYPoint, Tile> screenArea = new HashMap<>();

    public ArcadeService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public ArcadeService(String pathToFile) {
        loadInputs(pathToFile);
    }


    public int playTheGame() {
        // run the robot program until the game ends
        // The arcade cabinet runs Intcode software like the game the Elves sent (your puzzle input).
        // It has a primitive screen capable of drawing square tiles on a grid.
        while (!intCodeProcessorService.isHalted()) {
//            Run until we halt

            // The software draws tiles to the screen with output instructions: every three output instructions
            // specify the x position (distance from the left), y position (distance from the top), and tile id.
            intCodeProcessorService.executeToNextOutput();
            // Read the x position
            int tileX = intCodeProcessorService.getProgramOutput().intValue();

            intCodeProcessorService.executeToNextOutput();
            // Read the y position
            int tileY = intCodeProcessorService.getProgramOutput().intValue();

            intCodeProcessorService.executeToNextOutput();
            // Read the tile glyph
            int tileGlyphInt = intCodeProcessorService.getProgramOutput().intValue();
            Tile tileGlyph = Tile.get(tileGlyphInt);

            // Put it together in tile data
            TileData tileData = new TileData(new XYPoint(tileX, tileY), tileGlyph);
            /** DEBUG **/
//            System.out.println(tileData);
            // Put the tile onscreen
            putTileOnScreen(tileData);
        }
        // Return the total number of BLOCK tiles onscreen
        /** DEBUG **/
        // render the screen
        renderScreen();
        return countTilesOfType(BLOCK);
    }

    public int playTheGamePartB() {
        // run the robot program until the game ends, but pause for input
        // The arcade cabinet runs Intcode software like the game the Elves sent (your puzzle input).
        // It has a primitive screen capable of drawing square tiles on a grid.
        while (!intCodeProcessorService.isHalted()) {
//            Run until we halt
            if (!intCodeProcessorService.isWaitingForInput()) { // If we're NOT waiting for input
                // The software draws tiles to the screen with output instructions: every three output instructions
                // specify the x position (distance from the left), y position (distance from the top), and tile id.
                intCodeProcessorService.executeToNextOutput();
                // Read the x position
                int tileX = intCodeProcessorService.getProgramOutput().intValue();

                intCodeProcessorService.executeToNextOutput();
                // Read the y position
                int tileY = intCodeProcessorService.getProgramOutput().intValue();

                intCodeProcessorService.executeToNextOutput();
                // Read the tile glyph (or player score)
                int tileGlyphInt = intCodeProcessorService.getProgramOutput().intValue();

                // When three output instructions specify X=-1, Y=0, the third output instruction is not a tile;
                // the value instead specifies the new score to show in the segment display
                if (tileX == -1 && tileY == 0) {
                    System.out.println("SCORE:\t" + tileGlyphInt);
                    // it's the score display!
                } else {
                    // it's tile data!
                    // Put it together in tile data
                    Tile tileGlyph = Tile.get(tileGlyphInt);
                    TileData tileData = new TileData(new XYPoint(tileX, tileY), tileGlyph);
                    /** DEBUG **/
//            System.out.println(tileData);
                    // Put the tile onscreen
                    putTileOnScreen(tileData);
                }
            } else {
                // We ARE waiting for input
                // Draw the screen
                renderScreen();
                // Get some input
                intCodeProcessorService.setCpuInputValue(0L);
                intCodeProcessorService.executeNext();  // and execute the instruction
            }
        }
        // Return the total number of BLOCK tiles onscreen
        /** DEBUG **/
        // render the screen
        renderScreen();
        return countTilesOfType(BLOCK);
    }


    public void setFreePlay(boolean freePlay) {
//        Memory address 0 represents the number of quarters that have been inserted;
//        set it to 2 to play for free.
        long free = freePlay ? 2L : 0L;
        intCodeProcessorService.setValueAtPosition(0L, free);
    }

    private void putTileOnScreen(TileData tileData) {
        screenArea.put(tileData.getPosition(), tileData.getTile());
    }

    private int countTilesOfType(Tile tileToCount) {
        int count = 0;
        for (Tile tile : screenArea.values()) {
            if (tile.equals(tileToCount)) count++;
        }
        return count;
    }

    public void renderScreen() {
        // Find the min & max x & y values of the screen so we know the bounds to iterate over
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        for (XYPoint panel : screenArea.keySet()) {
            int x = panel.getX();
            int y = panel.getY();
            if (x < xMin) xMin = x;
            if (x > xMax) xMax = x;
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
        }
//        System.out.println("X range: " + xMin + " - " + xMax);
//        System.out.println("Y range: " + yMin + " - " + yMax);
        // Now that we know our boundaries, print the screen
        // y is lines, x is characters
        for (int y = yMin; y <= yMax; y++) {    // It's upside-down
            String line = "";
            // for each line
            for (int x = xMin; x <= xMax; x++) {
                // for each position on the line
                Tile tileType = readTileType(new XYPoint(x, y));    // If not found, this returns BLACK.
                Character tileGlyph = tileType.getGlyph();
                // Add the character to this line
                line += tileGlyph;
            }
            // when we're done with the line, print it
            System.out.println(line);
        }
    }

    private Tile readTileType(XYPoint p) {
        if (screenArea.containsKey(p)) {
            // If we've done something to this point before
            // read its tile type
            return screenArea.get(p);
        } else {
            // We've never been here.
            // All tiles start out empty.
            return EMPTY;
        }
    }

    public void reset() {
        intCodeProcessorService.reset();
        screenArea.clear();
    }


    private void loadInputs(String pathToFile) {
        intCodeProcessorService = new IntCodeProcessorService(pathToFile);
    }
}
