package org.jgoeres.adventofcode2019.Day05;

public enum OpCode {
    ADD(1),
    MULTIPLY(2),
    INPUT(3),
    OUTPUT(4),
    HALT(99);

    private int value;

    OpCode(int value) {
        this.value = value;
    }

    public static OpCode fromInt(int x) {
        switch (x) {
            case 1:
                return ADD;
            case 2:
                return MULTIPLY;
            case 3:
                return INPUT;
            case 4:
                return OUTPUT;
            case 99:
                return HALT;
        }
        return null;
    }
}
