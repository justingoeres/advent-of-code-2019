package org.jgoeres.adventofcode2019.Day03;

public class XYPoint {
    private int x;
    private int y;

    public XYPoint(int x, int y) {
        this.x = x;
        this.y = y;
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
        return ((this.x == ((XYPoint) obj).getX()) && (this.y == ((XYPoint) obj).getY()));
    }

    @Override
    public int hashCode() {
        // Make the hash code things like (4,3) -> 40003
        return (this.x * 10000 + this.y);
    }
}
