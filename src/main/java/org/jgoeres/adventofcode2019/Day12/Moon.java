package org.jgoeres.adventofcode2019.Day12;

import org.jgoeres.adventofcode2019.common.XYZPoint;

public class Moon {
    XYZPoint position;
    XYZPoint velocity;

    public Moon(XYZPoint position, XYZPoint velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Moon(XYZPoint position) {
        // Create with zero velocity
        this.position = position;
        this.velocity = new XYZPoint();
    }

    public Moon() {
        // Create with zero position & zero velocity
        this.position = new XYZPoint();
        this.velocity = new XYZPoint();
    }

    public void doTimerTick() {
        // Update this moon's position by its velocity
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();

        int vx = velocity.getX();
        int vy = velocity.getY();
        int vz = velocity.getZ();

        position.setX(x + vx);
        position.setY(y + vy);
        position.setZ(z + vz);
    }

    public int calculateTotalEnergy() {
        // A moon's potential energy is the sum of the absolute values of its x, y, and z position coordinates.
        int potentialEnergy = Math.abs(position.getX())
                + Math.abs(position.getY())
                + Math.abs(position.getZ());

        // A moon's kinetic energy is the sum of the absolute values of its velocity coordinates.
        int kineticEnergy = Math.abs(velocity.getX())
                + Math.abs(velocity.getY())
                + Math.abs(velocity.getZ());

        // The total energy for a single moon is its potential energy multiplied by its kinetic energy.
        return potentialEnergy * kineticEnergy;
    }

    @Override
    public String toString() {
        String toString = "pos=<x= " + position.getX()
                + ", y= " + position.getY()
                + ", z= " + position.getZ() + ">, "
                + "vel =<x= " + velocity.getX()
                + ", y= " + velocity.getY()
                + ", z= " + velocity.getZ() + ">";
        return toString;
    }
}
