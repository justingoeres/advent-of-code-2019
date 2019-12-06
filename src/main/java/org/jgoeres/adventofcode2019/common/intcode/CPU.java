package org.jgoeres.adventofcode2019.common.intcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.jgoeres.adventofcode2019.common.intcode.ParamMode.IMMEDIATE;

public class CPU {
    private static final Map<OpCode, Runnable> commands = new HashMap<>();

    int pc;
    int inputValue = 0;
    ArrayList<Integer> programCode;
    ArrayList<Integer> programCodeOriginal;
    private int lastOutput = 0;

    public CPU(ArrayList<Integer> programCode) {
        this.programCodeOriginal = programCode;
        reset();
    }

    public void reset() {
        pc = 0;
        lastOutput = 0;
        programCode = (ArrayList<Integer>) programCodeOriginal.clone();
    }

    // Create a map of OpCodes to functors that implement them
    private HashMap<OpCode, IOpCode> opCodeFunctorMap() {
        HashMap<OpCode, IOpCode> map = new HashMap<>();

        // Day 2
        map.put(OpCode.ADD, (instruction) -> add(instruction));
        map.put(OpCode.MULTIPLY, (instruction) -> multiply(instruction));
        map.put(OpCode.HALT, (instruction) -> halt(instruction));
        // Day 5A
        map.put(OpCode.INPUT, (instruction) -> input(instruction));
        map.put(OpCode.OUTPUT, (instruction) -> output(instruction));
        // Day 5B
        map.put(OpCode.JUMP_IF_TRUE, (instruction) -> jumpIfTrue(instruction));
        map.put(OpCode.JUMP_IF_FALSE, (instruction) -> jumpIfFalse(instruction));
        map.put(OpCode.LESS_THAN, (instruction) -> lessThan(instruction));
        map.put(OpCode.EQUALS, (instruction) -> equals(instruction));

        return map;
    }

    public boolean executeNext() {
        // Use the program counter to read the current OpCode and execute it.
        // Return true to continue, false to halt.
        Instruction nextInstruction = decodeInstruction();
        boolean keepGoing = opCodeFunctorMap().get(nextInstruction.getOpCode()).execute(nextInstruction);
        return keepGoing;
    }

    public void setValueAtPosition(int position, int value) {
        programCode.set(position, value);
    }

    private int getValueAtPCAndAdvance() {
        int value = getValueAtPosition(pc);
        pc++;
        return value;
    }

    public int getValueAtPosition(int position) {
        return programCode.get(position);
    }

    public Instruction decodeInstruction() {
        //  1002,4,3,4,33
        //
        //ABCDE
        // 1002
        //
        //DE - two-digit opcode,      02 == opcode 2
        // C - mode of 1st parameter,  0 == position mode
        // B - mode of 2nd parameter,  1 == immediate mode
        // A - mode of 3rd parameter,  0 == position mode,
        //                                  omitted due to being a leading zero
        //This instruction multiplies its first two parameters. The first parameter,
        // 4 in position mode, works like it did before - its value is the value
        // stored at address 4 (33). The second parameter, 3 in immediate mode,
        // simply has value 3. The result of this operation, 33 * 3 = 99, is
        // written according to the third parameter, 4 in position mode, which
        // also works like it did before - 99 is written to address 4.

        // Get the raw instruction value from the pc.
        int instr = getValueAtPCAndAdvance();

        // Decode it into opcode & parameter modes
        int opCodeInt = instr % 100;    // last two digits are opcode
        OpCode opCode = OpCode.fromInt(opCodeInt);
        instr /= 100;

        int numArgs = opCode.getNumArgs();

        ArrayList<Parameter> params = new ArrayList<>();
        // Read in the parameters for this opCode
        for (int i = 0; i < numArgs; i++) {
            ParamMode paramMode = ParamMode.fromInt(instr % 10);
            instr /= 10;
            int paramValue = getValueAtPCAndAdvance();

            Parameter param = new Parameter(paramMode, paramValue);
            params.add(param);
        }

        Instruction nextInstruction = new Instruction(opCode, params);
        return nextInstruction;
    }

    public void setInputValue(int inputValue) {
        this.inputValue = inputValue;
    }

    /*********** OpCode Implementations ***********/
    private boolean add(Instruction instruction) {
        // ADD
        // Adds together numbers read from two positions and
        // stores the result in a third position. The three integers
        // immediately after the opcode tell you these three positions
        // - the first two indicate the positions from which you should
        // read the input values, and the third indicates the position
        // at which the output should be stored.

        // Get the arguments
        int val1 = getArgValue(instruction, 0);
        int val2 = getArgValue(instruction, 1);
        int val3 = instruction.getParam(2).getValue();  // instructions that write out always use the value of the raw parameter

        programCode.set(val3, val1 + val2);
        return true;
    }

    private boolean multiply(Instruction instruction) {
        // MULTIPLY
        // Works exactly like ADD, except it multiplies the
        // two inputs instead of adding them. Again, the three integers after
        // the opcode indicate where the inputs and outputs are, not their values.

        // Get the arguments
        int val1 = getArgValue(instruction, 0);
        int val2 = getArgValue(instruction, 1);
        int val3 = instruction.getParam(2).getValue();  // instructions that write out always use the value of the raw parameter

        programCode.set(val3, val1 * val2);
        return true;
    }

    private boolean input(Instruction instruction) {
        // INPUT
        // Opcode 3 takes a single integer as input and saves
        // it to the position given by its only parameter.
        // For example, the instruction 3,50 would take an
        // input value and store it at address 50.
        // Get the arguments
        int val1 = instruction.getParam(0).getValue();  // instructions that write out always use the value of the raw parameter

        programCode.set(val1, inputValue);
        return true;
    }

    private boolean output(Instruction instruction) {
        // OUTPUT
        // Opcode 4 outputs the value of its only parameter.
        // For example, the instruction 4,50 would output the
        // value at address 50.
        // Get the arguments
//        int val1 = instruction.getParam(0).getValue();  // instructions that write out always use the value of the raw parameter
        int val1 = getArgValue(instruction, 0);
        lastOutput = val1;
        System.out.println(val1);
        return true;
    }

    private boolean jumpIfTrue(Instruction instruction) {
        // JUMP_IF_TRUE
        // jump-if-true: if the first parameter is *non-zero*,
        // it sets the instruction pointer to the value from the second parameter.
        // Otherwise, it does nothing.

        // Get the arguments
        int val1 = getArgValue(instruction, 0);
//        int val2 = instruction.getParam(1).getValue();  // instructions that write out always use the value of the raw parameter
        int val2 = getArgValue(instruction, 1);

        if (val1 != 0) pc = val2;
        return true;
    }

    private boolean jumpIfFalse(Instruction instruction) {
        // JUMP_IF_FALSE
        // jump-if-true: if the first parameter is *zero*,
        // it sets the instruction pointer to the value from the second parameter.
        // Otherwise, it does nothing.

        // Get the arguments
        int val1 = getArgValue(instruction, 0);
//        int val2 = instruction.getParam(1).getValue();  // instructions that write out always use the value of the raw parameter
        int val2 = getArgValue(instruction, 1);

        if (val1 == 0) pc = val2;
        return true;
    }

    private boolean lessThan(Instruction instruction) {
        // LESS_THAN
        // if the first parameter is less than the second parameter,
        // it stores 1 in the position given by the third parameter.
        // Otherwise, it stores 0.

        // Get the arguments
        int val1 = getArgValue(instruction, 0);
        int val2 = getArgValue(instruction, 1);
        int val3 = instruction.getParam(2).getValue();  // instructions that write out always use the value of the raw parameter

        int lessThan = (val1 < val2) ? 1 : 0;
        programCode.set(val3, lessThan);
        return true;
    }

    private boolean equals(Instruction instruction) {
        // EQUALS
        // if the first parameter is equal to the second parameter,
        // it stores 1 in the position given by the third parameter.
        // Otherwise, it stores 0.

        // Get the arguments
        int val1 = getArgValue(instruction, 0);
        int val2 = getArgValue(instruction, 1);
        int val3 = instruction.getParam(2).getValue();  // instructions that write out always use the value of the raw parameter

        int lessThan = (val1 == val2) ? 1 : 0;
        programCode.set(val3, lessThan);
        return true;
    }

    private boolean halt(Instruction instruction) {
        // HALT
        // Stop execution by returning false.
        return false;
    }

    private int getArgValue(Instruction instruction, int index) {
        ParamMode mode = instruction.getParam(index).getMode();
        int arg = instruction.getParam(index).getValue();
        int value = (mode == IMMEDIATE) ? arg : getValueAtPosition(arg);
        return value;
    }

    public int getLastOutput() {
        return lastOutput;
    }
}
