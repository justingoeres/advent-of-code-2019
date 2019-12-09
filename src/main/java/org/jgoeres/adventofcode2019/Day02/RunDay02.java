package org.jgoeres.adventofcode2019.Day02;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public abstract class RunDay02 {
    static String pathToInputs = "data/day02/input.txt";
        static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService(pathToInputs);
//    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService();


    public static Long problem2A() {
        // Day 2A
        System.out.println("=== DAY 2A ===");

        // Set up the processor in the 1202 alarm configuration
        intCodeProcessorService.setValueAtPosition(1L, 12L);
        intCodeProcessorService.setValueAtPosition(2L, 2L);

        // Run it
        intCodeProcessorService.runToCompletion();

        Long result = intCodeProcessorService.getValueAtPosition(0L);
        System.out.println("Day 2A: Position 0 Value at Halt = " + result);
//        Day 2A: Position 0 Value at Halt = 4138687
//        Time elapsed:	2 ms

        return result;
    }

    public static Long problem2B() {
        // Day 2B
        System.out.println("=== DAY 2B ===");

        // Once the program has halted, its output is available at address 0,
        // also just like before. Each time you try a pair of inputs, make sure
        // you first reset the computer's memory to the values in the program
        // (your puzzle input) - in other words, don't reuse memory from a
        // previous attempt.

        Long noun, result;
        Long verb = 0L;
        int target = 19690720;

        nounloop:
        for (noun = 0L; noun <= 99; noun++) {
            for (verb = 0L; verb < 99; verb++) {
                intCodeProcessorService.reset();
                setIntCodeNounAndVerb(noun, verb);
                intCodeProcessorService.runToCompletion();

                result = intCodeProcessorService.getValueAtPosition(0L);
//                System.out.println("\tnoun = " + noun + "\tverb = " + verb + "\tresult = " + result);

                if (result == target) {
                    // we found what we're looking for!
                    System.out.println("\tTarget value " + target +
                            " found for noun = " + noun + ", verb = " + verb);
                    break nounloop; // break out of everything
                }
            }
        }

        result = 100 * noun + verb;
        System.out.println("Day 2B: Final result = " + result);
//        Target value 19690720 found for noun = 66, verb = 35
//        Day 2B: Final result = 6635
//        Time elapsed:	21 ms

        return result;
    }

    private static void setIntCodeNounAndVerb(Long noun, Long verb) {
        intCodeProcessorService.setValueAtPosition(1L, noun);
        intCodeProcessorService.setValueAtPosition(2L, verb);
    }

}





