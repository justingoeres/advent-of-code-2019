package org.jgoeres.adventofcode2019.Day07;

import org.jgoeres.adventofcode2019.common.intcode.CPU;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class IntCodeProcessorDay07 extends IntCodeProcessorService {
    // Extends the IntCodeProcessor for Day 07 by adding support for five-stage amplifier processing
    private ArrayList<CPU> allAmplifiers = new ArrayList<>();
    private HashMap<CPU, CPU> ioWiring = new HashMap<>();

    public IntCodeProcessorDay07() {
        super("data/day07/input.txt");
    }

    public IntCodeProcessorDay07(String pathToFile) {
        super(pathToFile);
    }

    public Integer runAmplifierStages(ArrayList<Integer> phases) {

        Integer inputValue = 0; // input to Stage 1 is zero.
        for (Integer phase : phases) {
            // Run each stage with its corresponding phase setting
            // reset before executing so the program is in the original state
            this.reset();
            // First input value is phase
            this.setCpuInputValue(phase.longValue());
            // Second input value is input signal
            this.setCpuInputValue(inputValue.longValue());
            // Then run
            this.runToCompletion();
            // Output of this stage is the input to the next
            inputValue = this.getProgramOutput().intValue();
        }
        // When we're done, return the final stage output value
        return inputValue;
    }

    public void initParallelAmplifierStages() {
        final int NUM_STAGES = 5;
        allAmplifiers.clear();

        for (int i = 0; i < NUM_STAGES; i++) {
            // Create a new CPU for each stage
            allAmplifiers.add(loadInputs());
        }

        //      INPUT OF ----> COMES FROM
        ioWiring.clear();
        ioWiring.put(allAmplifiers.get(1), allAmplifiers.get(0));
        ioWiring.put(allAmplifiers.get(2), allAmplifiers.get(1));
        ioWiring.put(allAmplifiers.get(3), allAmplifiers.get(2));
        ioWiring.put(allAmplifiers.get(4), allAmplifiers.get(3));
        ioWiring.put(allAmplifiers.get(0), allAmplifiers.get(4));
    }

    public Long runParallelAmplifierStages(Queue<Integer> phaseSettings) {
        // Reset all processors and setup their phases
        for (CPU amplifier : allAmplifiers) {
            amplifier.reset();
            amplifier.addToInputQueue(phaseSettings.poll().longValue());
        }

        // Set amplifier A's input to zero for the very first iteration only.
        allAmplifiers.get(0).addToInputQueue(0L);

        while (!allAmplifiers.get(4).isHalted()) {    // Keep going until the last amplifier stops
            // Execute the next tick for all processors.
            // If any are waiting for input, get that input from the processor they're wired to.
            for (CPU amplifier : allAmplifiers) {
                if (!amplifier.isHalted()) {
                    if (amplifier.isWaitingForInput()) {
                        // If this amp is waiting for input
                        // Get the amp that will provide it
                        CPU inputSource = ioWiring.get(amplifier);
                        if (inputSource.isOutputReady()) {
                            // If the source amp has an output ready
                            // Send it to the input of this amp
                            amplifier.addToInputQueue(inputSource.getLastOutput());
                        }
                    }
                    amplifier.executeNext();
                }
            }
        }
        Long result = allAmplifiers.get(4).getLastOutput();
        return result;
    }

}
