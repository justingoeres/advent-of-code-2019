package org.jgoeres.adventofcode2019.Day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CPU {
    private static final Map<OpCode, Runnable> commands = new HashMap<>();

    int pc;
    int inputValue = 0;
    ArrayList<Integer> programCode;
    ArrayList<Integer> programCodeOriginal;

    public CPU(ArrayList<Integer> programCode) {
        this.programCodeOriginal = programCode;
        reset();
    }

    public void reset() {
        pc = 0;
        programCode = (ArrayList<Integer>) programCodeOriginal.clone();
    }

    // Create a map of OpCodes to functors that implement them
    private HashMap<OpCode, IOpCode> opCodeFunctorMap() {
        HashMap<OpCode, IOpCode> map = new HashMap<>();

        map.put(OpCode.ADD, (pc, programCode) -> add());
        map.put(OpCode.MULTIPLY, (pc, programCode) -> multiply());
        map.put(OpCode.HALT, (pc, programCode) -> halt());

        return map;
    }

    public boolean executeNext() {
        // Use the program counter to read the current OpCode and execute it.
        // Return true to continue, false to halt.
        OpCode opCode = OpCode.fromInt(getValueAtPCAndAdvance());
        boolean keepGoing = opCodeFunctorMap().get(opCode).execute(pc, programCode);
        return keepGoing;
    }

    public void setValueAtPosition(int position, int value) {
        programCode.set(position, value);
    }

    public int getValueAtPosition(int position) {
        return programCode.get(position);
    }

    public void setInputValue(int inputValue) {
        this.inputValue = inputValue;
    }

    private boolean add() {
        // ADD
        // Adds together numbers read from two positions and
        // stores the result in a third position. The three integers
        // immediately after the opcode tell you these three positions
        // - the first two indicate the positions from which you should
        // read the input values, and the third indicates the position
        // at which the output should be stored.

        // Get the arguments
        int pos1 = getValueAtPCAndAdvance();
        int pos2 = getValueAtPCAndAdvance();
        int pos3 = getValueAtPCAndAdvance();

        int val1 = programCode.get(pos1);
        int val2 = programCode.get(pos2);

        programCode.set(pos3, val1 + val2);
        return true;
    }

    private boolean multiply() {
        // MULTIPLY
        // Works exactly like ADD, except it multiplies the
        // two inputs instead of adding them. Again, the three integers after
        // the opcode indicate where the inputs and outputs are, not their values.

        // Get the arguments
        int pos1 = getValueAtPCAndAdvance();
        int pos2 = getValueAtPCAndAdvance();
        int pos3 = getValueAtPCAndAdvance();

        int val1 = programCode.get(pos1);
        int val2 = programCode.get(pos2);

        programCode.set(pos3, val1 * val2);
        return true;
    }

    private boolean input() {
        // INPUT
        // Opcode 3 takes a single integer as input and saves
        // it to the position given by its only parameter.
        // For example, the instruction 3,50 would take an
        // input value and store it at address 50.
        int pos1 = getValueAtPCAndAdvance();

        programCode.set(pos1, inputValue);
        return true;
    }

    private boolean output() {
        // OUTPUT
        // Opcode 4 outputs the value of its only parameter.
        // For example, the instruction 4,50 would output the
        // value at address 50.
        int pos1 = getValueAtPCAndAdvance();

        System.out.println(getValueAtPosition(pos1));
        return true;
    }

    private boolean halt() {
        // HALT
        // Stop execution by returning false.
        return false;
    }

    private int getValueAtPCAndAdvance() {
        int value = getValueAtPosition(pc);
        pc++;
        return value;
    }
}
