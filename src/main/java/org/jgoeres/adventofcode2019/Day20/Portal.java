package org.jgoeres.adventofcode2019.Day20;

import org.jgoeres.adventofcode2019.common.XYPoint;

public class Portal extends XYPoint {
    XYPoint exit;

    public Portal() {
    }

    public Portal(XYPoint exit) {
        this.exit = exit;
    }

    public Portal(int x, int y) {
        super(x, y);
    }

    public Portal(int x, int y, int xExit, int yExit) {
        super(x, y);
        this.exit.setX(xExit);
        this.exit.setY(yExit);
    }


}
