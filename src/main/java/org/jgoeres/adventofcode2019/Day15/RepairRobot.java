package org.jgoeres.adventofcode2019.Day15;

import org.jgoeres.adventofcode2019.Day11.PaintingRobot;
import org.jgoeres.adventofcode2019.common.DirectionNESW;
import org.jgoeres.adventofcode2019.common.XYPoint;

public class RepairRobot extends PaintingRobot {
    public RepairRobot(XYPoint location) {
        super(location);
    }

    public void stepRobot(DirectionNESW directionNESW) {
        int DEFAULT_DISTANCE = 1;
        moveRobot(DEFAULT_DISTANCE, directionNESW);
    }

    public void moveRobot(int numSteps, DirectionNESW directionNESW) {
        switch (directionNESW) {
            case NORTH:
                location = new XYPoint(location.getX(), location.getY() + numSteps);
                break;
            case EAST:
                location = new XYPoint(location.getX() + numSteps, location.getY());
                break;
            case SOUTH:
                location = new XYPoint(location.getX(), location.getY() - numSteps);
                break;
            case WEST:
                location = new XYPoint(location.getX() - numSteps, location.getY());
                break;
        }
    }

    public XYPoint getRelativeLocation(DirectionNESW directionNESW) {
        return getRelativeLocation(1, directionNESW);
    }

    public XYPoint getRelativeLocation(int numSteps, DirectionNESW directionNESW) {
        switch (directionNESW) {
            case NORTH:
                return (new XYPoint(location.getX(), location.getY() + numSteps));
            case EAST:
                return (new XYPoint(location.getX() + numSteps, location.getY()));
            case SOUTH:
                return (new XYPoint(location.getX(), location.getY() - numSteps));
            case WEST:
                return (new XYPoint(location.getX() - numSteps, location.getY()));
        }
        return null;
    }
}
