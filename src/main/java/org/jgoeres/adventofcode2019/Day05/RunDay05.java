package org.jgoeres.adventofcode2019.Day05;

public abstract class RunDay05 {
    static String pathToInputs = "data/day05/input.txt";
    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService();


    public static int problem5A() {
        // Day 5A
        System.out.println("=== DAY 5A ===");

        // Set up the processor in the 1202 alarm configuration
        intCodeProcessorService.setCpuInputValue(1);
        // Run it
        intCodeProcessorService.runToCompletion();

        int result = intCodeProcessorService.getProgramOutput();
        System.out.println("Day 5A: Program Output = " + result);
//        Day 5A: Program Output = 9775037
//        Time elapsed:	17 ms
        return result;
    }

    public static int problem5B() {
        // Day 5B
        System.out.println("=== DAY 5B ===");

        // Once the program has halted, its output is available at address 0,
        // also just like before. Each time you try a pair of inputs, make sure
        // you first reset the computer's memory to the values in the program
        // (your puzzle input) - in other words, don't reuse memory from a
        // previous attempt.

        int noun, result;
        int verb = 0;
        int target = 19690720;

        nounloop:
        for (noun = 0; noun <= 99; noun++) {
            for (verb = 0; verb < 99; verb++) {
                intCodeProcessorService.reset();
                setIntCodeNounAndVerb(noun, verb);
                intCodeProcessorService.runToCompletion();

                result = intCodeProcessorService.getValueAtPosition(0);
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
        System.out.println("Day 5B: Final result = " + result);
//        Target value 19690720 found for noun = 66, verb = 35
//        Day 5B: Final result = 6635
//        Time elapsed:	21 ms

        return result;
    }

    private static void setIntCodeNounAndVerb(int noun, int verb) {
        intCodeProcessorService.setValueAtPosition(1, noun);
        intCodeProcessorService.setValueAtPosition(2, verb);
    }

}





