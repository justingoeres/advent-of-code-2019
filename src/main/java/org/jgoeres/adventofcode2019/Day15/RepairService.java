package org.jgoeres.adventofcode2019.Day15;

import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.*;

import static org.jgoeres.adventofcode2019.Day15.Location.*;

public class RepairService extends IntCodeProcessorService {
    private final String DAY = "15";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final boolean DISPLAY = false;

    // TODO: Robot inherits from PaintingRobot for now because it has movement built in; generalize this later
    private final XYPoint ORIGIN = new XYPoint(0, 0);
    private RepairRobot robot;
    // areaMap maps xy locations to whatever is at that location (wall, empty, oxygen system)
    private HashMap<XYPoint, Location> areaMap = new HashMap<>();

    Deque<Direction> exploreTrail = new LinkedList<>();
    HashSet<XYPoint> pointsToExplore = new HashSet<>();
    HashSet<XYPoint> pointsExplored = new HashSet<>();
    HashMap<XYPoint, Integer> distancesFromOrigin = new HashMap<>();

    XYPoint oxygenXY;

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
        exploreTrail.clear();
        pointsExplored.clear();
        pointsExplored.add(ORIGIN);
        pointsToExplore.clear();
        pointsToExplore.add(ORIGIN);
        distancesFromOrigin.clear();
        distancesFromOrigin.put(ORIGIN, 0);
    }

    public void explore() {
        int distanceFromOrigin = 0;

        // Run the robot all over the map
        while (true) {
            // At current point R
            // Check all four directions NESW from here
            // For each one
            for (Direction direction : Direction.values()) {
                // Check if the point in question is unexplored.
                XYPoint pointToExplore = robot.getRelativeLocation(direction);
                if (pointsToExplore.contains(pointToExplore)) {
                    //  If yes, skip it – we'll get to it eventually
                } else {
                    //  If no, then is that point already explored?
                    if (pointsExplored.contains(pointToExplore)) {
                        //  If yes, skip it – we've already been there
                    } else {
                        //  If no, add the point to the set of points to explore eventually
                        pointsToExplore.add(pointToExplore);
                        //  Also add the MOVE (NESW) to the roadmap
                        exploreTrail.addFirst(direction);
                    }
                }
            }

            // Next, try to process the explore queue
            if (exploreTrail.isEmpty()) {
                /** If the trail is empty, we're done! **/
                break;
            }
            //  Get the top item from the queue (it will be a DIRECTION)
            Direction moveToTry = exploreTrail.poll();
            XYPoint targetPoint = robot.getRelativeLocation(moveToTry);
            if (pointsExplored.contains(targetPoint)) {
                // If this point has already been explored,
                // then we're backtracking and we know this spot is open
                moveRobot(moveToTry);
                // Update our distance to whatever the distance was to this point before
                distanceFromOrigin = distancesFromOrigin.get(targetPoint);
                // but since we're backtracking, don't ALSO add a new bracktracking step. Just finish
            } else {
                //  Try to move in that direction
                boolean moveSuccessful = moveRobot(moveToTry);
                // Remove it from the list of points to explore
                pointsToExplore.remove(targetPoint);
                pointsExplored.add(targetPoint);
                if (moveSuccessful) {
                    //  Was the move successful?
                    //      If yes, add the OPPOSITE move to the explore queue AT THE FRONT
                    exploreTrail.addFirst(moveToTry.opposite());
                    // Also update our distance – we've just moved one step further from the origin
                    distanceFromOrigin++;
                    distancesFromOrigin.put(targetPoint, distanceFromOrigin);
                }
                //      If no, do nothing – we've mapped that target so loop and do the next queue item
            }

            /** DEBUG **/
            if (DISPLAY) {
                if (DISPLAY) {
                    printAreaMap();
                    System.out.println(robot.getLocation() + "\t" + distanceFromOrigin);
                    System.out.println();
                }
            }
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
                // We found the oxygen!
                oxygenXY = targetLocation;
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

    public int getOxygenDistance() {
        return distancesFromOrigin.get(oxygenXY);
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
//                        locationChar = '.';
                        locationChar = ' ';
                        // If the robot is here,print it instead of empty
                        if (ORIGIN.equals(new XYPoint(x, y))) locationChar = 'X';
                        if (robot.getLocation().equals(new XYPoint(x, y))) locationChar = 'R';
                        break;
                    case WALL:
                        locationChar = '#';
                        break;
                    case OXYGEN:
                        locationChar = 'O';
                        break;
                    default:
                        locationChar = ' ';
                }
                // Add the character to this line
                line += locationChar;
            }
            // when we're done with the line, print it
            System.out.println(line);
        }
    }
}
