package org.jgoeres.adventofcode2019.Day10;

import org.jgoeres.adventofcode2019.common.XYPoint;

public class AsteroidAngle {
    private XYPoint asteroid;
    private Double angleInRadians;

    public AsteroidAngle(XYPoint asteroid, double angleInRadians) {
        this.asteroid = asteroid;
        this.angleInRadians = angleInRadians;
    }

    public XYPoint getAsteroid() {
        return asteroid;
    }

    public void setAsteroid(XYPoint asteroid) {
        this.asteroid = asteroid;
    }

    public Double getAngleInRadians() {
        return angleInRadians;
    }

    public void setAngleInRadians(Double angleInRadians) {
        this.angleInRadians = angleInRadians;
    }
}
