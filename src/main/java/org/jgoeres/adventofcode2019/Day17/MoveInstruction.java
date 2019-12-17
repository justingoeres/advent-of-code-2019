package org.jgoeres.adventofcode2019.Day17;

import org.jgoeres.adventofcode2019.common.Rotation;

public class MoveInstruction {
    Rotation rotation;
    int distance;

    public MoveInstruction(Rotation rotation, int distance) {
        this.rotation = rotation;
        this.distance = distance;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        // e.g. "R8"
        return rotation.getRotationString() + "," + distance;
    }
}
