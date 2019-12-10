package org.jgoeres.adventofcode2019.Day10;

public abstract class Quadrant {
    public static QuadrantEnum quadrantFromXY(int x, int y) {
        if (x > 0 && y >= 0) return QuadrantEnum.I;
        if (x <= 0 && y > 0) return QuadrantEnum.II;
        if (x < 0 && y <= 0) return QuadrantEnum.III;
        if (x >= 0 && y < 0) return QuadrantEnum.IV;
        return null;
    }
}

enum QuadrantEnum {
    I(1),
    II(2),
    III(3),
    IV(4);

    int numValue;

    QuadrantEnum(int numValue) {
        this.numValue = numValue;
    }
}


