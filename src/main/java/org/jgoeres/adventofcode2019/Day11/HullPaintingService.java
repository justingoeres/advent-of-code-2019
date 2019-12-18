package org.jgoeres.adventofcode2019.Day11;

import org.jgoeres.adventofcode2019.common.Rotation;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.jgoeres.adventofcode2019.common.robot.RobotURDL;

import java.util.HashMap;

import static org.jgoeres.adventofcode2019.Day11.Color.BLACK;
import static org.jgoeres.adventofcode2019.Day11.Color.WHITE;
import static org.jgoeres.adventofcode2019.common.Rotation.CLOCKWISE;
import static org.jgoeres.adventofcode2019.common.Rotation.COUNTERCLOCKWISE;

public class HullPaintingService {
    private final String XX = "11";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";
    private final Character HASH = '#';
    private final Character SPACE = ' ';
    private final String EMPTY = "";

    private IntCodeProcessorService intCodeProcessorService;
    private RobotURDL paintingRobot;
    private HashMap<XYPoint, Color> hullArea;

    public HullPaintingService() {
        loadInputs(DEFAULT_INPUTS_PATH);
        reset();
    }

    public HullPaintingService(String pathToFile) {
        // Create the intCode computer, the robot, and the (empty) hull area
        loadInputs(pathToFile);
        reset();
    }

    public void reset() {
        intCodeProcessorService.reset();
        paintingRobot = new RobotURDL(new XYPoint(0, 0));
        hullArea = new HashMap<>();
    }

    public void reset(Color startColor) {
        reset();
        hullArea.put(paintingRobot.getLocation(), startColor);
    }

    public int paintTheHull() {
        // run the robot program until we're done painting
        while (!intCodeProcessorService.isHalted()) {
//            Run until we halt

//            The program uses input instructions to access the robot's camera:
//            provide 0 if the robot is over a black panel
//            or 1 if the robot is over a white panel.
            intCodeProcessorService.executeToNextInput();
            sendColorToInput();

//            Then, the program will output two values:
//            First, it will output a value indicating the color to paint
//            the panel the robot is over: 0 means to paint the panel black,
//            and 1 means to paint the panel white.
            intCodeProcessorService.executeToNextOutput();
            // Now we can read what color we should paint, and do the painting
            paintPanelFromOutput();

//            Second, it will output a value indicating the direction the robot
//            should turn: 0 means it should turn left 90 degrees, and 1 means
//            it should turn right 90 degrees.
//            After the robot turns, it should always
//            move forward exactly one panel. The robot starts facing up.
            // Now we've done the painting, so run to the next output...
            intCodeProcessorService.executeToNextOutput();
            // then turn & move the robot.
            turnAndMove();
        }
        // Return the total number of panels painted (i.e. the total hull area we reached)
        return hullArea.size();
    }

    public void printHull() {
        // Find the min & max x & y values of the hull so we know the bounds to iterate over
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        for (XYPoint panel : hullArea.keySet()) {
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
            String line = EMPTY;
            // for each line
            for (int x = xMin; x <= xMax; x++) {
                // for each position on the line
                Color panelColor = readPanelColor(new XYPoint(x, y));    // If not found, this returns BLACK.
                Character panelChar = (panelColor == WHITE) ? HASH : SPACE;
                // Add the character to this line
                line += panelChar;
            }
            // when we're done with the line, print it
            System.out.println(line);
        }
    }

    private void sendColorToInput() {
        XYPoint location = paintingRobot.getLocation();
        Color panelColor = readPanelColor(location);
        // Convert panelColor to long for input
        long longPanelColor = panelColor.getIntColor();
        // Send the color to the input queue of the processor
        intCodeProcessorService.setCpuInputValue(longPanelColor);
    }

    private void paintPanelFromOutput() {
        XYPoint panelLocation = new XYPoint(paintingRobot.getLocation().getX(), paintingRobot.getLocation().getY());
        long longPanelColor = intCodeProcessorService.getProgramOutput();
        Color panelColor = (longPanelColor == 0L) ? BLACK : WHITE;
        paintPanel(panelLocation, panelColor);
    }

    private void paintPanel(XYPoint p, Color color) {
        // Put the color in the hull map, so we can read it later
        hullArea.put(p, color);
    }

    private Color readPanelColor(XYPoint p) {
        if (hullArea.containsKey(p)) {
            // If we've done something to this point before
            // read its color
            return hullArea.get(p);
        } else {
            // We've never been here.
            // All panels start out black.
            return BLACK;
        }
    }

    private void turnAndMove() {
        long rotationLong = intCodeProcessorService.getProgramOutput();
        Rotation rotation = (rotationLong == 0L) ? COUNTERCLOCKWISE : CLOCKWISE;
        paintingRobot.turnRobot(rotation);
        paintingRobot.stepRobot();
    }

    private void loadInputs(String pathToFile) {
        intCodeProcessorService = new IntCodeProcessorService(pathToFile);
    }
}
