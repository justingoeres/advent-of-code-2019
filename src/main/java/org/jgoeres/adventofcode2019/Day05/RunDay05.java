package org.jgoeres.adventofcode2019.Day05;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public abstract class RunDay05 {
    static String pathToInputs = "data/day05/input.txt";
    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService();


    public static int problem5A() {
        // Day 5A
        System.out.println("=== DAY 5A ===");

        // Set up the processor in the air conditioner test configuration
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

        // Set the processor up for the thermal radiator controller test
        intCodeProcessorService.reset();
        intCodeProcessorService.setCpuInputValue(5);
        intCodeProcessorService.runToCompletion();

        int result = intCodeProcessorService.getProgramOutput();
//        System.out.println("Day 5B: Program Output = " + result);
//        Day 5B: Program Output = 15586959
//        Time elapsed:	1 ms

        return result;
    }

}





