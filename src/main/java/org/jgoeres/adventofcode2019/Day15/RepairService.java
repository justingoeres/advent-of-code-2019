package org.jgoeres.adventofcode2019.Day15;

import org.jgoeres.adventofcode2019.Day11.Color;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.HashMap;

import static org.jgoeres.adventofcode2019.Day15.Direction.*;
import static org.jgoeres.adventofcode2019.Day15.Location.*;

public class RepairService extends IntCodeProcessorService {
    private final String DAY = "15";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    // TODO: Robot inherits from PaintingRobot for now because it has movement built in; generalize this later
    private final XYPoint ORIGIN = new XYPoint(0, 0);
    private RepairRobot robot;
    // areaMap maps xy locations to whatever is at that location (wall, empty, oxygen system)
    private HashMap<XYPoint, Location> areaMap = new HashMap<>();

    public RepairService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = loadInputs();
        reset();
    }

    public RepairService(String pathToFile) {
        inputFile = pathToFile;
        cpu = loadInputs();
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        // Reset the robot to the origin
        robot = new RepairRobot(ORIGIN);
        // Reset the map – the first spot is empty because that's where the robot starts
        areaMap.clear();
        areaMap.put(ORIGIN, EMPTY);
    }

    public void explore() {
        // Run the robot all over the map, finding all the walls.

        // Solve it via the "follow the wall on the right" maze solving method


        while (true) {
            for (Direction direction : Direction.values()){
            // Try to move north.
            // If wall, try to move east.
            // If wall, try to move south.
            // If wall, try to move west.
                if (moveRobot(direction)) {
                    // Move was successful
                    break;
                }
                // else move was not successful; try the next direction
            }
            /** DEBUG **/
            printAreaMap();
            System.out.println();
        }
    }

    public boolean moveRobot(Direction direction) {
        // Tries to move the robot, returns TRUE if the move succeeded.
        /**
         * The remote control program executes the following steps in a loop forever:
         *
         * Accept a movement command via an input instruction.
         * Send the movement command to the repair droid.
         * Wait for the repair droid to finish the movement operation.
         */

        // First, execute the program until the next time it asks for input
        executeToNextInput();

        // Give the CPU our move instruction
        setCpuInputValue(direction.getDirectionInt());
        XYPoint targetLocation = robot.getRelativeLocation(direction);

        // Next, let the program run until it tells us the result of the move command
        executeToNextOutput();

        /**
         * The repair droid can reply with any of the following status codes:
         *
         * 0: The repair droid hit a wall. Its position has not changed.
         * 1: The repair droid has moved one step in the requested direction.
         * 2: The repair droid has moved one step in the requested direction; its new position is the location of the oxygen system.
         */
        Long moveResult = getProgramOutput();
        switch (moveResult.intValue()) {
            case 0:
                // WALL
                // Add the target point to the area map
                areaMap.put(targetLocation, WALL);
                // Don't move the robot
                return false;   // no move
            case 1:
                // EMPTY / successful move
                // The move was successful; mark this point as empty and move the robot there
                // Add the target point to the area map
                areaMap.put(targetLocation, EMPTY);
                // Move the robot
                robot.stepRobot(direction);
                return true;    // robot moved
            case 2:
                // OXYGEN / successful move
                // The move was successful; mark this point as empty and move the robot there
                // Add the target point to the area map
                areaMap.put(targetLocation, OXYGEN);
                // Move the robot
                robot.stepRobot(direction);
                return true;    // robot moved
        }
        // We should never get here but if we do, just say the robot didn't move
        return false;
    }

    private Location readAreaPoint(XYPoint p) {
        if (areaMap.containsKey(p)) {
            // If we've done something to this point before
            // read its color
            return areaMap.get(p);
        } else {
            // We've never been here.
            return UNKNOWN;
        }
    }

    public void printAreaMap() {
        // Find the min & max x & y values of the hull so we know the bounds to iterate over
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        for (XYPoint panel : areaMap.keySet()) {
            int x = panel.getX();
            int y = panel.getY();
            if (x < xMin) xMin = x;
            if (x > xMax) xMax = x;
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
        }
//        System.out.println("X range: " + xMin + " - " + xMax);
//        System.out.println("Y range: " + yMin + " - " + yMax);
        // Now that we know our boundaries, print the hull
        // y is lines, x is characters
        for (int y = yMax; y >= yMin; y--) {    // It's upside-down
            String line = "";
            // for each line
            for (int x = xMin; x <= xMax; x++) {
                // for each position on the line
                Location locationType = readAreaPoint(new XYPoint(x, y));    // If not found, this returns BLACK.
                Character locationChar;
                switch (locationType) {
                    case EMPTY:
                        locationChar = '.';
                        // If the robot is here,print it instead of empty
                        if (robot.getLocation().equals(new XYPoint(x,y))) locationChar = 'R';
                        break;
                    case WALL:
                        locationChar = '#';
                        break;
                    case OXYGEN:
                        locationChar = 'O';
                        break;
                    default:
                        locationChar = '?';
                }
                // Add the character to this line
                line += locationChar;
            }
            // when we're done with the line, print it
            System.out.println(line);
        }
    }
}
