package org.jgoeres.adventofcode2019.Day17;

import org.jgoeres.adventofcode2019.Day11.PaintingRobot;
import org.jgoeres.adventofcode2019.common.Direction;
import org.jgoeres.adventofcode2019.Day15.RepairRobot;
import org.jgoeres.adventofcode2019.common.Rotation;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.jgoeres.adventofcode2019.Day17.ScaffoldLocation.*;
import static org.jgoeres.adventofcode2019.common.Direction.DOWN;
import static org.jgoeres.adventofcode2019.common.Direction.LEFT;
import static org.jgoeres.adventofcode2019.common.Direction.RIGHT;
import static org.jgoeres.adventofcode2019.common.Direction.UP;
import static org.jgoeres.adventofcode2019.common.Rotation.CLOCKWISE;
import static org.jgoeres.adventofcode2019.common.Rotation.COUNTERCLOCKWISE;

public class ScaffoldService extends IntCodeProcessorService {
    private final String DAY = "17";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final boolean DISPLAY = true;

    private final XYPoint ORIGIN = new XYPoint(0, 0);
    private ScaffoldRobot robot;
    // areaMap maps xy locations to whatever is at that location (scaffold, empty)
    private HashMap<XYPoint, ScaffoldLocation> areaMap = new HashMap<>();

    private final char UP_CHAR = '^';
    private final char DOWN_CHAR = 'v';
    private final char LEFT_CHAR = '<';
    private final char RIGHT_CHAR = '>';

//    Deque<Direction> exploreTrail = new LinkedList<>();
//    HashSet<XYPoint> pointsToExplore = new HashSet<>();
//    HashSet<XYPoint> pointsExplored = new HashSet<>();
//    HashMap<XYPoint, Integer> distancesFromOrigin = new HashMap<>();

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
        robot = new ScaffoldRobot(ORIGIN);
        // Reset the map – the first spot is empty because that's where the robot starts
        areaMap.clear();
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
                    robot = new ScaffoldRobot(p, facing);
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

    public void explore() {
        // Run the robot along all the pieces of the scaffold until we get to the end
        // Record each step as we go, then output them.
        // (We don't need the intcode processor for this, just our areaMap)
        int distance = 0;
        Rotation rotation = null;
        ArrayList<MoveInstruction> moves = new ArrayList<>();
        while (true) {
            // Track our current move (in steps taken)
            // Also track when we rotate
            // When we DO rotate, store the PREVIOUS rotation and the length of the move
            // for our records

            // At current point P
            // Check the point immediately ahead of us.
            // If it's a scaffold or intersection, move there
            if (aheadIsScaffold()) {
                // If the point ahead is NOT EMPTY (i.e. it's either SCAFFOLD or INTERSECTION)
                // Move there
                robot.moveRobot(1);
                distance++; // Track our distance
            } else {
                // If not, check the points on either side of us
                // One of them will be a scaffold; turn toward it

                // We're going to rotate, so our previous move is finished
                if (rotation != null) {
                    // Add the just-completed move to our moves list
                    MoveInstruction moveInstruction = new MoveInstruction(rotation, distance);
                    moves.add(moveInstruction);
                }

                // Now try to find the next place to go
                rotation = rotateToScaffold(robot);

                if (rotation != null) {
                    // Reset our distance tracking and continue
                    distance = 0;
                } else {
                    // we're done! Bail out
                    break;
                }
            }
            if (DISPLAY) {
                printAreaMap();
            }
        }
        /*** DEBUG ***/
        // Print our moves list
        String movesString = String.join(",", moves.toString());
        System.out.println(movesString);
    }


    public Rotation rotateToScaffold(PaintingRobot robot) {
        // Rotates the robot left & right to find which point is the next SCAFFOLD or INTERSECTION.
        // Returns the direction it rotated.

        // Check left
        robot.turnRobot(COUNTERCLOCKWISE);
        if (aheadIsScaffold()) return COUNTERCLOCKWISE;

        // Check right (rotate twice to get over there)
        robot.turnRobot(CLOCKWISE);
        robot.turnRobot(CLOCKWISE);
        if (aheadIsScaffold()) return CLOCKWISE;

        // We should only get here at the end of the scaffold, when we have nowhere to turn
        return null;
    }

    private boolean aheadIsScaffold() {
        XYPoint pAhead = getRelativeStep(robot.getLocation(), robot.getFacing());
        if (areaMap.containsKey(pAhead) && !areaMap.get(pAhead).equals(EMPTY)) {
            // Point ahead is NOT EMPTY, so is SCAFFOLD OR INTERSECTION
            return true;
        } else {
            // point ahead is EMPTY or something else we can't drive on
            return false;
        }
    }


    private XYPoint getRelativeStep(XYPoint p, Direction direction) {
        int numSteps = 1;
        switch (direction) {
            case UP:
                return (new XYPoint(p.getX(), p.getY() - numSteps));
            case RIGHT:
                return (new XYPoint(p.getX() + numSteps, p.getY()));
            case DOWN:
                return (new XYPoint(p.getX(), p.getY() + numSteps));
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
