package org.jgoeres.adventofcode2019.Day09;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public abstract class RunDay09 {
    static String pathToInputs = "data/day09/input.txt";
    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService(pathToInputs);


    public static int problem9A() {
        // Day 9A
        System.out.println("=== DAY 9A ===");

        // Set up the processor in the air conditioner test configuration
        intCodeProcessorService.setCpuInputValue(1);
        // Run it
        intCodeProcessorService.runToCompletion();

        int result = intCodeProcessorService.getProgramOutput();
        System.out.println("Day 9A: Program Output = " + result);
//        Day 9A: Program Output = 9775037
//        Time elapsed:	17 ms
        return result;
    }

    public static int problem9B() {
        // Day 9B
        System.out.println("=== DAY 9B ===");

        // Set the processor up for the thermal radiator controller test
        intCodeProcessorService.reset();
        intCodeProcessorService.setCpuInputValue(5);
        intCodeProcessorService.runToCompletion();

        int result = intCodeProcessorService.getProgramOutput();
//        System.out.println("Day 9B: Program Output = " + result);
//        Day 9B: Program Output = 15586959
//        Time elapsed:	1 ms

        return result;
    }

}





