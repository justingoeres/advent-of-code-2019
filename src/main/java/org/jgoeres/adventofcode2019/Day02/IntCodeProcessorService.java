package org.jgoeres.adventofcode2019.Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class IntCodeProcessorService {

    private final String DEFAULT_INPUTS_PATH = "data/day02/input.txt";

    private ArrayList<Integer> programCode = new ArrayList<>();
    private int pc = 0; // program counter

    public IntCodeProcessorService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public IntCodeProcessorService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public void runToCompletion() {
        while (processCurrentOpCode()) ;
    }

    public void setValueAtPosition(int position, int value) {
        programCode.set(position, value);
    }

    public boolean processCurrentOpCode() {
        boolean keepGoing = true;   // Assume we continue after processing this opcode.
        // Get the opCode at the current program counter position
        int opCode = programCode.get(pc);
        int pos1 = programCode.get(pc + 1);
        int pos2 = programCode.get(pc + 2);
        int pos3 = programCode.get(pc + 3);

        if (opCode == 99) {
            // halt
            return false;
        }

        int val1 = programCode.get(pos1);
        int val2 = programCode.get(pos2);

        // process the current opcode
        switch (opCode) {
            case 1:
                // Opcode 1 adds together numbers read from two positions and
                // stores the result in a third position. The three integers
                // immediately after the opcode tell you these three positions
                // - the first two indicate the positions from which you should
                // read the input values, and the third indicates the position
                // at which the output should be stored.
                programCode.set(pos3, val1 + val2);
                break;
            case 2:
                // Opcode 2 works exactly like opcode 1, except it multiplies the
                // two inputs instead of adding them. Again, the three integers after
                // the opcode indicate where the inputs and outputs are, not their values.
                programCode.set(pos3, val1 * val2);
                break;
        }
        // Once you're done processing an opcode, move to the next one by stepping forward 4 positions.
        pc += 4;
        return keepGoing;
    }

    public int getValueAtPosition(int position) {
        return programCode.get(position);
    }

    private void loadInputs(String pathToFile) {
        String line;
        programCode.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Add all the codes from this line to the programCode list
                for (String element : data) {
                    programCode.add(Integer.parseInt(element));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
