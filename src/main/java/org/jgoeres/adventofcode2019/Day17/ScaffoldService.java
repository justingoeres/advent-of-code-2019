package org.jgoeres.adventofcode2019.Day17;

import org.jgoeres.adventofcode2019.Day11.PaintingRobot;
import org.jgoeres.adventofcode2019.common.Direction;
import org.jgoeres.adventofcode2019.Day15.RepairRobot;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.jgoeres.adventofcode2019.Day17.ScaffoldLocation.*;
import static org.jgoeres.adventofcode2019.common.Direction.DOWN;
import static org.jgoeres.adventofcode2019.common.Direction.LEFT;
import static org.jgoeres.adventofcode2019.common.Direction.RIGHT;
import static org.jgoeres.adventofcode2019.common.Direction.UP;

public class ScaffoldService extends IntCodeProcessorService {
    private final String DAY = "17";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final boolean DISPLAY = false;

    // TODO: Robot use PaintingRobot for now because it has movement built in; generalize this later
    private final XYPoint ORIGIN = new XYPoint(0, 0);
    private PaintingRobot robot;
    // areaMap maps xy locations to whatever is at that location (scaffold, empty)
    private HashMap<XYPoint, ScaffoldLocation> areaMap = new HashMap<>();

    private final char UP_CHAR = '^';
    private final char DOWN_CHAR = 'v';
    private final char LEFT_CHAR = '>';
    private final char RIGHT_CHAR = '<';

//    Deque<Direction> exploreTrail = new LinkedList<>();
//    HashSet<XYPoint> pointsToExplore = new HashSet<>();
//    HashSet<XYPoint> pointsExplored = new HashSet<>();
//    HashMap<XYPoint, Integer> distancesFromOrigin = new HashMap<>();

//    XYPoint oxygenXY;

    public ScaffoldService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = loadInputs();
        reset();
    }

    public ScaffoldService(String pathToFile) {
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
//        exploreTrail.clear();
//        pointsExplored.clear();
//        pointsExplored.add(ORIGIN);
//        pointsToExplore.clear();
//        pointsToExplore.add(ORIGIN);
//        distancesFromOrigin.clear();
//        distancesFromOrigin.put(ORIGIN, 0);
    }

    public void createScaffoldMap() {
        // Run the program until it halts
        // At each output, read the output character
        // and stick it in our areaMap
        int x = 0;
        int y = 0;

        Set<Character> robotChars = new HashSet<>();
        robotChars.add(UP_CHAR);
        robotChars.add(DOWN_CHAR);
        robotChars.add(RIGHT_CHAR);
        robotChars.add(LEFT_CHAR);
        try {
            while (!cpu.isHalted()) {
                // Run to the next output
                executeToNextOutput();
                if (cpu.isHalted()) break;  // If we halted before the next output, bail out
                // Get the character and turn it into a Location enum
                XYPoint p = new XYPoint(x, y);
                char mapChar = (char) getProgramOutput().intValue();
                if (!robotChars.contains(mapChar)) {
                    // If this is NOT a robot character, it's scaffolding or empty
                    ScaffoldLocation location = ScaffoldLocation.get(mapChar);
                    switch (location) {
                        case LINEFEED:
                            // If this is a linefeed, increment the line, reset x, and continue
                            y++;
                            x = 0;
                            break;
                        case EMPTY:
                            // If this spot is empty, just ignore it. We can deal with missing
                            // points in our areaMap
                            x++;
                            break;
                        default:
                            // In all other cases add this location to the map
                            areaMap.put(p, location);
                            // then increment x and continue
                            x++;
                    }
                } else {
                    // This IS the robot, so update the robot's position and
                    // its facing direction
                    Direction facing;
                    switch (mapChar) {
                        case UP_CHAR:
                        default:
                            facing = UP;
                            break;
                        case DOWN_CHAR:
                            facing = DOWN;
                            break;
                        case LEFT_CHAR:
                            facing = LEFT;
                            break;
                        case RIGHT_CHAR:
                            facing = RIGHT;
                            break;
                    }
                    robot = new PaintingRobot(p, facing);
                    // Other than the robot, this point on the map is empty
                    areaMap.put(p, EMPTY);
                    x++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public int calculateAlignmentParameters() {
        Set<XYPoint> intersections = findIntersections(areaMap);
        /*** DEBUG ***/
        if (DISPLAY) {
            printAreaMap();
        }

        // Locate all scaffold intersections; for each, its alignment parameter
        // is the distance between its left edge and the left edge of the view
        // multiplied by the distance between its top edge and the top edge of the view.
        int alignmentTotal = 0;
        for (XYPoint p : intersections) {
            int alignmentParameter = p.getX() * p.getY();
            alignmentTotal += alignmentParameter;
        }
        return alignmentTotal;
    }

    public Set<XYPoint> findIntersections(HashMap<XYPoint, ScaffoldLocation> scaffoldMap) {
        Set<XYPoint> intersections = new HashSet<>();
        // Iterate over the points of the scaffold and find the ones that are bordered by
        // scaffold on all four sides. Those are intersections
        ArrayList<XYPoint> relativeLocations = new ArrayList<>(Direction.values().length);
        final Direction[] allDirections = Direction.values();
        for (XYPoint p : scaffoldMap.keySet()) {
            boolean isIntersection = true;
            relativeLocations.clear();
            for (Direction direction : allDirections) {
                // Check each of the 4 points from p (up, down, left, right)
                XYPoint pRelative = getRelativeStep(p, direction);
                // If any
                if (!areaMap.containsKey(pRelative)) {
                    isIntersection = false;
                    break;
                }
            }
            if (isIntersection) {
                // If we checked all for directions and there was a SCAFFOLD (or robot) at each one
                // Change the entry in our map to flag this as an intersection
                areaMap.put(p, INTERSECTION);
                // And add it to our set of intersections to return
                intersections.add(p);
            }
        }
        return intersections;
    }

//    public void explore() {
//        int distanceFromOrigin = 0;
//
//        // Run the robot all over the map
//        while (true) {
//            // At current point R
//            // Check all four directions NESW from here
//            // For each one
//            for (Direction direction : Direction.values()) {
//                // Check if the point in question is unexplored.
//                XYPoint pointToExplore = robot.getRelativeLocation(direction);
//                if (pointsToExplore.contains(pointToExplore)) {
//                    //  If yes, skip it – we'll get to it eventually
//                } else {
//                    //  If no, then is that point already explored?
//                    if (pointsExplored.contains(pointToExplore)) {
//                        //  If yes, skip it – we've already been there
//                    } else {
//                        //  If no, add the point to the set of points to explore eventually
//                        pointsToExplore.add(pointToExplore);
//                        //  Also add the MOVE (NESW) to the roadmap
//                        exploreTrail.addFirst(direction);
//                    }
//                }
//            }
//
//            // Next, try to process the explore queue
//            if (exploreTrail.isEmpty()) {
//                /** If the trail is empty, we're done! **/
//                break;
//            }
//            //  Get the top item from the queue (it will be a DIRECTION)
//            Direction moveToTry = exploreTrail.poll();
//            XYPoint targetPoint = robot.getRelativeLocation(moveToTry);
//            if (pointsExplored.contains(targetPoint)) {
//                // If this point has already been explored,
//                // then we're backtracking and we know this spot is open
//                moveRobot(moveToTry);
//                // Update our distance to whatever the distance was to this point before
//                distanceFromOrigin = distancesFromOrigin.get(targetPoint);
//                // but since we're backtracking, don't ALSO add a new bracktracking step. Just finish
//            } else {
//                //  Try to move in that direction
//                boolean moveSuccessful = moveRobot(moveToTry);
//                // Remove it from the list of points to explore
//                pointsToExplore.remove(targetPoint);
//                pointsExplored.add(targetPoint);
//                if (moveSuccessful) {
//                    //  Was the move successful?
//                    //      If yes, add the OPPOSITE move to the explore queue AT THE FRONT
//                    exploreTrail.addFirst(moveToTry.opposite());
//                    // Also update our distance – we've just moved one step further from the origin
//                    distanceFromOrigin++;
//                    distancesFromOrigin.put(targetPoint, distanceFromOrigin);
//                }
//                //      If no, do nothing – we've mapped that target so loop and do the next queue item
//            }
//
//            /** DEBUG **/
//            if (DISPLAY) {
//                if (DISPLAY) {
//                    printAreaMap();
//                    System.out.println(robot.getLocation() + "\t" + distanceFromOrigin);
//                    System.out.println();
//                }
//            }
//        }
//    }

//    public int fillWithOxygen() {
//        // return the number of minutes it takes to fill
//        int minutesElapsed = 0;
//        // Start with the oxygen source from Part A
//        HashSet<XYPoint> sources = new HashSet<>();
//        sources.add(oxygenXY);
//
//        while (true) {
//            // Go until we stop :)
//            // Get all the non-oxygen points surrounding the source(s)
//            HashSet<XYPoint> nextSources = new HashSet<>();
//            for (XYPoint source : sources) {
//                for (Direction direction : Direction.values()) {
//                    // Check each point adjacent to this source
//                    XYPoint pointToCheck = getRelativeStep(source, direction);
//                    // Is this point empty?
//                    if (areaMap.get(pointToCheck) == EMPTY) {
//                        // Then fill it with oxygen and add it to the source list for next time
//                        areaMap.put(pointToCheck, OXYGEN);
//                        nextSources.add(pointToCheck);
//                        // Otherwise it's a wall or filled with oxygen already, so ignore it.
//                    }
//                }
//            }
//            // Now that we've filled all these sources and identified the next ones,
//            // Switch in the next list!
//            sources = nextSources;
//
//            if (nextSources.isEmpty()) {
//                // If there ARE no next sources, we're done!
//                break;
//            }
//
//            // Increment the timer
//            minutesElapsed++;
//
//            if (DISPLAY) {
//                printAreaMap();
//                System.out.println("ELAPSED TIME:\t" + minutesElapsed);
//            }
//        }
//        return minutesElapsed;  // return the elapsed time
//    }

    private XYPoint getRelativeStep(XYPoint p, Direction direction) {
        int numSteps = 1;
        switch (direction) {
            case UP:
                return (new XYPoint(p.getX(), p.getY() + numSteps));
            case RIGHT:
                return (new XYPoint(p.getX() + numSteps, p.getY()));
            case DOWN:
                return (new XYPoint(p.getX(), p.getY() - numSteps));
            case LEFT:
                return (new XYPoint(p.getX() - numSteps, p.getY()));
        }
        return null;
    }

    private ScaffoldLocation readAreaPoint(XYPoint p) {
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
        for (int y = yMin; y <= yMax; y++) {    // It's upside-down
            String line = "";
            // for each line
            for (int x = xMin; x <= xMax; x++) {
                XYPoint p = new XYPoint(x, y);
                Character locationChar;
                // for each position on the line
                if (robot.getLocation().equals(p)) {
                    // If this is the robot, draw it
                    switch (robot.getFacing()) {
                        case UP:
                        default:
                            locationChar = UP_CHAR;
                            break;
                        case DOWN:
                            locationChar = DOWN_CHAR;
                            break;
                        case RIGHT:
                            locationChar = RIGHT_CHAR;
                            break;
                        case LEFT:
                            locationChar = LEFT_CHAR;
                            break;
                    }
                } else {
                    // Draw the scaffold piece
                    ScaffoldLocation locationType = readAreaPoint(p);    // If not found, this returns BLACK.
                    locationChar = locationType.getLocationChar();
                }
                // Add the character to this line
                line += locationChar;
            }
            // when we're done with the line, print it
            System.out.println(line);
        }
    }
}
