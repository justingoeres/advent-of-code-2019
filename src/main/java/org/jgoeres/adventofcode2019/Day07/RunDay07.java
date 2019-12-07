package org.jgoeres.adventofcode2019.Day07;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class RunDay07 {
    static String pathToInputs = "data/day07/input.txt";
    static IntCodeProcessorDay07 intCodeProcessorService = new IntCodeProcessorDay07();

    public static int problem7A() {
        // Day 7A
        System.out.println("=== DAY 7A ===");

        int maxThrustOutput = Integer.MIN_VALUE;
        int PHASE_MAX = 4;

        // Iterate through all possible phase settings of the amplifiers
        ArrayList<Integer> phaseSettings = new ArrayList<>();
        for (int i = 0; i <= PHASE_MAX; i++) {
            for (int j = 0; j <= PHASE_MAX; j++) {
                for (int k = 0; k <= PHASE_MAX; k++) {
                    for (int l = 0; l <= PHASE_MAX; l++) {
                        for (int m = 0; m <= PHASE_MAX; m++) {
                            phaseSettings.clear();
                            phaseSettings.add(i);
                            phaseSettings.add(j);
                            phaseSettings.add(k);
                            phaseSettings.add(l);
                            phaseSettings.add(m);

                            // Use only unique phase setting combinations – no duplicates
                            HashSet<Integer> set = new HashSet<>(phaseSettings);
                            if (phaseSettings.size() != set.size()){
                                // If the set & array sizes are NOT equal, then there
                                // is at least one dupe in the array. Skip it.
                                continue;
                            }

                            int thrustOutput = intCodeProcessorService.runAmplifierStages(phaseSettings);

//                            System.out.println("Thrust output\t" + thrustOutput
//                                            + "\tfor phase settings "
//                                            + phaseSettings.get(0) + ", "
//                                            + phaseSettings.get(1) + ", "
//                                            + phaseSettings.get(2) + ", "
//                                            + phaseSettings.get(3) + ", "
//                                            + phaseSettings.get(4));

                                    // If this output exceeds the max we've found so far, store it
                            if (thrustOutput > maxThrustOutput) {
                                maxThrustOutput = thrustOutput;

//                                System.out.println("Max thrust\t" + maxThrustOutput
//                                        + "\tfor phase settings "
//                                        + phaseSettings.get(0) + ", "
//                                        + phaseSettings.get(1) + ", "
//                                        + phaseSettings.get(2) + ", "
//                                        + phaseSettings.get(3) + ", "
//                                        + phaseSettings.get(4)
//                                );
                            }
                        }
                    }
                }
            }
        }


        int result = maxThrustOutput;
        System.out.println("Day 7A: Max thrust output = " + result);
// 73517948 too high
//        Day 7A: Program Output =
//        Time elapsed:	17 ms
        return result;
    }

    public static int problem7B() {
        // Day 7B
        System.out.println("=== DAY 7B ===");

        // Set the processor up for the thermal radiator controller test
        intCodeProcessorService.reset();
        intCodeProcessorService.setCpuInputValue(5);
        intCodeProcessorService.runToCompletion();

        int result = intCodeProcessorService.getProgramOutput();
//        System.out.println("Day 7B: Program Output = " + result);
//        Day 7B: Program Output =
//        Time elapsed:	1 ms

        return result;
    }

}





