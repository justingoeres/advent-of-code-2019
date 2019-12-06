package org.jgoeres.adventofcode2019.common.intcode;

public class Parameter {
    private ParamMode mode;
    private int value;

    public Parameter(ParamMode mode, int value) {
        this.mode = mode;
        this.value = value;
    }

    public ParamMode getMode() {
        return mode;
    }

    public void setMode(ParamMode mode) {
        this.mode = mode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
