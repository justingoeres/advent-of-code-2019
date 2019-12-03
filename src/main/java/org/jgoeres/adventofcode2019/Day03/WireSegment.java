package org.jgoeres.adventofcode2019.Day03;

public class WireSegment {
    private Direction direction;
    private Integer length;
    private XYPoint p1;
    private XYPoint p2;

    public WireSegment(Direction direction, Integer length, int x0, int y0) {
        this.direction = direction;
        this.length = length;

        // Set the origin and endpoint of the segment
        this.p1 = new XYPoint(x0, y0);
        this.p2 = calculateP2();
    }

    private XYPoint calculateP2() {
        int x = 0;
        int y = 0;
        switch (direction) {
            case UP:
                x = p1.getX();
                y = p1.getY() + length;
                break;
            case RIGHT:
                x = p1.getX() + length;
                y = p1.getY();
                break;
            case DOWN:
                x = p1.getX();
                y = p1.getY() - length;
                break;
            case LEFT:
                x = p1.getX() - length;
                y = p1.getY();
                break;
        }
        return new XYPoint(x, y);
    }
}
