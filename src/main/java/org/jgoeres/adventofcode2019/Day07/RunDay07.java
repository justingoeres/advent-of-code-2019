package org.jgoeres.adventofcode2019.Day07;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public abstract class RunDay07 {
    static String pathToInputs = "data/day07/input.txt";
    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService();


    public static int problem7A() {
        // Day 7A
        System.out.println("=== DAY 7A ===");

        // Set up the processor in the air conditioner test configuration
        intCodeProcessorService.setCpuInputValue(1);
        // Run it
        intCodeProcessorService.runToCompletion();

        int result = intCodeProcessorService.getProgramOutput();
        System.out.println("Day 7A: Program Output = " + result);
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





