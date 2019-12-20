package org.jgoeres.adventofcode2019.common;

import java.util.Objects;

public class XYPoint {
    private int x = 0;
    private int y = 0;

    public XYPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public XYPoint() {
        // Create at 0,0
    }

    public void set(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public XYPoint getRelativeLocation(DirectionURDL directionURDL) {
        return getRelativeLocation(1, directionURDL);
    }

    public XYPoint getRelativeLocation(int numSteps, DirectionURDL directionURDL) {
        switch (directionURDL) {
            case UP:
                // Default negative-Y direction is UP
                return (new XYPoint(getX(), getY() - numSteps));
            case RIGHT:
                return (new XYPoint(getX() + numSteps, getY()));
            case DOWN:
                // Default positive-Y direction is DOWN
                return (new XYPoint(getX(), getY() + numSteps));
            case LEFT:
                return (new XYPoint(getX() - numSteps, getY()));
        }
        return null;
    }

    @Override
    public String toString() {
        return (getX() + ", " + getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof XYPoint))
            return false;
        if (obj == this)
            return true;
        // Two points are equivalent if they have the same coordinates
        return ((this.x == ((XYPoint) obj).getX())
                && (this.y == ((XYPoint) obj).getY()));
    }

    @Override
    public int hashCode() {
        return (Objects.hash(x, y));
    }
}
