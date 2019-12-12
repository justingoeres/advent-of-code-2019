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
}
