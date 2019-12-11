package org.jgoeres.adventofcode2019.Day11;

import org.jgoeres.adventofcode2019.common.Rotation;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.ArrayList;
import java.util.HashMap;

import static org.jgoeres.adventofcode2019.Day11.Color.BLACK;
import static org.jgoeres.adventofcode2019.Day11.Color.WHITE;
import static org.jgoeres.adventofcode2019.common.AoCMath.ORIGIN;
import static org.jgoeres.adventofcode2019.common.Rotation.CLOCKWISE;
import static org.jgoeres.adventofcode2019.common.Rotation.COUNTERCLOCKWISE;

public class HullPaintingService {
    private final String XX = "11";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private IntCodeProcessorService intCodeProcessorService;
    private PaintingRobot robot;
    private HashMap<XYPoint, Color> hullArea;

    public HullPaintingService() {
        loadInputs(DEFAULT_INPUTS_PATH);
        robot = new PaintingRobot(ORIGIN);
        hullArea = new HashMap<>();
    }

    public HullPaintingService(String pathToFile) {
        // Create the intCode computer, the robot, and the (empty) hull area
        loadInputs(pathToFile);
        robot = new PaintingRobot(ORIGIN);
        hullArea = new HashMap<>();
    }

    public int paintTheHull() {
        // run the robot program until we're done painting
        while (!intCodeProcessorService.isHalted()) {
//            Run until we halt

//            The program uses input instructions to access the robot's camera:
//            provide 0 if the robot is over a black panel
//            or 1 if the robot is over a white panel.
            executeToNextInput();
            sendColorToInput();

//            Then, the program will output two values:
//            First, it will output a value indicating the color to paint
//            the panel the robot is over: 0 means to paint the panel black,
//            and 1 means to paint the panel white.
            executeToNextOutput();
            // Now we can read what color we should paint, and do the painting
            paintPanelFromOutput();

//            Second, it will output a value indicating the direction the robot
//            should turn: 0 means it should turn left 90 degrees, and 1 means
//            it should turn right 90 degrees.
//            After the robot turns, it should always
//            move forward exactly one panel. The robot starts facing up.
            // Now we've done the painting, so run to the next output...
            executeToNextOutput();
            // then turn & move the robot.
            turnAndMove();
        }
        // Return the total number of panels painted (i.e. the total hull area we reached)
        return hullArea.size();
    }

    private void executeToNextInput() {
        while (!intCodeProcessorService.isWaitingForInput() &&
                !intCodeProcessorService.isHalted()) {
            intCodeProcessorService.executeNext();
        }
    }

    private void executeToNextOutput() {
        while (!intCodeProcessorService.isOutputReady()
                && !intCodeProcessorService.isHalted()) {
            intCodeProcessorService.executeNext();
        }
    }

    private void sendColorToInput() {
        XYPoint location = robot.getLocation();
        Color panelColor = readPanelColor(location);
        // Convert panelColor to long for input
        long longPanelColor = panelColor.getIntColor();
        // Send the color to the input queue of the processor
        intCodeProcessorService.setCpuInputValue(longPanelColor);
    }

    private void paintPanelFromOutput() {
        XYPoint panelLocation = new XYPoint(robot.getLocation().getX(), robot.getLocation().getY());
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
        robot.turnRobot(rotation);
        robot.stepRobot();
    }

    private void loadInputs(String pathToFile) {
        intCodeProcessorService = new IntCodeProcessorService(pathToFile);
    }
}
