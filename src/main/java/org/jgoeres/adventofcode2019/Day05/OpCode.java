package org.jgoeres.adventofcode2019.Day05;

public enum OpCode {
    ADD(1,3),
    MULTIPLY(2,3),
    INPUT(3,1),
    OUTPUT(4,1),
    HALT(99,0);

    private int value;
    private int numArgs;

    OpCode(int value, int numArgs) {
        this.value = value;
        this.numArgs = numArgs;
    }

    public int getValue(){
        return value;
    }

    public int getNumArgs(){
        return numArgs;
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
