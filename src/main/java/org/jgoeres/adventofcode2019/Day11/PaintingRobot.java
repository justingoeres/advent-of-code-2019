package org.jgoeres.adventofcode2019.Day11;

import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.robot.RobotURDL;

public class PaintingRobot extends RobotURDL {
    public PaintingRobot(XYPoint location) {
        super(location);
    }

    public PaintingRobot(XYPoint location, DirectionURDL facing) {
        super(location, facing);
    }

    @Override
    public void moveRobot(int numSteps) {
        switch (getFacing()) {
            case UP:
                // positive-Y is UP
                location.setY(location.getY() + numSteps);
                break;
            case RIGHT:
                location.setX(location.getX() + numSteps);
                break;
            case DOWN:
                // negative-Y is DOWN
                location.setY(location.getY() - numSteps);
                break;
            case LEFT:
                location.setX(location.getX() - numSteps);
                break;
        }
    }
}
