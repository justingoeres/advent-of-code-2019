package org.jgoeres.adventofcode2019.Day07;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.ArrayList;

public class IntCodeProcessorDay07 extends IntCodeProcessorService {
    // Extends the IntCodeProcessor for Day 07 by adding support for five-stage amplifier processing


    public IntCodeProcessorDay07() {
        super("data/day07/input.txt");
    }

    public IntCodeProcessorDay07(String pathToFile) {
        super(pathToFile);
    }

    public int runAmplifierStages(ArrayList<Integer> phases) {

        int inputValue = 0; // input to Stage 1 is zero.
        for (int phase : phases) {
            // Run each stage with its corresponding phase setting
            // reset before executing so the program is in the original state
            this.reset();
            // First input value is phase
            this.setCpuInputValue(phase);
            // Second input value is input signal
            this.setCpuInputValue(inputValue);
            // Then run
            this.runToCompletion();
            // Output of this stage is the input to the next
            inputValue = this.getProgramOutput();
        }
        // When we're done, return the final stage output value
        return inputValue;
    }
}
