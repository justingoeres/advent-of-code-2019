package org.jgoeres.adventofcode2019.Day05;

public enum ParamMode {
    POSITION(0),
    IMMEDIATE(1);

    private int modeInt;

    ParamMode(int modeInt) {
        this.modeInt = modeInt;
    }

    public static ParamMode fromInt(int x) {
        switch (x) {
            case 0:
                return POSITION;
            case 1:
                return IMMEDIATE;
        }
        return null;
    }
}
