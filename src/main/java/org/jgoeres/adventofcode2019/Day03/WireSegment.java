package org.jgoeres.adventofcode2019.Day03;

import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;

public class WireSegment {
    private DirectionURDL directionURDL;
    private Integer length;
    private XYPoint p1;
    private XYPoint p2;

    public WireSegment(DirectionURDL directionURDL, Integer length, XYPoint p1) {
        this.directionURDL = directionURDL;
        this.length = length;

        this.p1 = p1;
        // Calculate the endpoint of the segment
        this.p2 = calculateP2();
    }

    private XYPoint calculateP2() {
        int x = 0;
        int y = 0;
        switch (directionURDL) {
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

    public DirectionURDL getDirectionURDL() {
        return directionURDL;
    }

    public Integer getLength() {
        return length;
    }

    public XYPoint getP1() {
        return p1;
    }

    public XYPoint getP2() {
        return p2;
    }
}
