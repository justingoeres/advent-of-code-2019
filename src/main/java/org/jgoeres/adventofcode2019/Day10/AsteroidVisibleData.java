package org.jgoeres.adventofcode2019.Day10;

import org.jgoeres.adventofcode2019.Day03.XYPoint;

public class AsteroidVisibleData {
    XYPoint xyPoint;
    int numVisible;

    public AsteroidVisibleData(XYPoint xyPoint, int numVisible) {
        this.xyPoint = xyPoint;
        this.numVisible = numVisible;
    }

    public XYPoint getXyPoint() {
        return xyPoint;
    }

    public int getNumVisible() {
        return numVisible;
    }
}




