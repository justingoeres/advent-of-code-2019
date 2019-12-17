package org.jgoeres.adventofcode2019.Day17;

import org.jgoeres.adventofcode2019.Day11.PaintingRobot;
import org.jgoeres.adventofcode2019.common.Direction;
import org.jgoeres.adventofcode2019.common.XYPoint;

public class ScaffoldRobot extends PaintingRobot {

    public ScaffoldRobot(XYPoint location) {
        super(location);
    }

    public ScaffoldRobot(XYPoint location, Direction facing) {
        super(location, facing);
    }

    public void moveRobot(int numSteps) {
        switch (getFacing()) {
            case UP:
                // The ScaffoldRobot has negative-Y as UP
                location.setY(location.getY() - numSteps);
                break;
            case RIGHT:
                location.setX(location.getX() + numSteps);
                break;
            case DOWN:
                // The ScaffoldRobot has positive-Y as DOWN
                location.setY(location.getY() + numSteps);
                break;
            case LEFT:
                location.setX(location.getX() - numSteps);
                break;
        }
    }
}
