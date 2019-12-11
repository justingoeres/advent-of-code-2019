package org.jgoeres.adventofcode2019.Day11;

import org.jgoeres.adventofcode2019.common.Direction;
import org.jgoeres.adventofcode2019.common.Rotation;
import org.jgoeres.adventofcode2019.common.XYPoint;

import static org.jgoeres.adventofcode2019.common.Direction.UP;

public class PaintingRobot {
    XYPoint location;
    Direction facing;

    public PaintingRobot(XYPoint location) {
        this.location = location;
        this.facing = UP;   // Robot starts facing UP.
    }

    public void turnRobot(Rotation rotation) {
        facing = facing.rotate(rotation);
    }

    public void moveRobot() {
        int DEFAULT_DISTANCE = 1;
        moveRobot(DEFAULT_DISTANCE);
    }

    public void moveRobot(int numSteps) {
        switch (facing) {
            case UP:
                location.setY(location.getY() + numSteps);
            case RIGHT:
                location.setX(location.getX() + numSteps);
            case DOWN:
                location.setY(location.getY() - numSteps);
            case LEFT:
                location.setX(location.getX() - numSteps);
        }
    }
}
