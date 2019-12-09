package org.jgoeres.adventofcode2019.Day09;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public abstract class RunDay09 {
    static String pathToInputs = "data/day09/input.txt";
    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService(pathToInputs);


    public static Long problem9A() {
        // Day 9A
        System.out.println("=== DAY 9A ===");

        // Set up the BOOST program in test mode
        intCodeProcessorService.setCpuInputValue(1L);
        // Run it
        intCodeProcessorService.runToCompletion();

        Long result = intCodeProcessorService.getProgramOutput();
        System.out.println("Day 9A: Program Output = " + result);
//        Day 9A: Program Output = 4080871669
//        Time elapsed:	73 ms
        return result;
    }

    public static Long problem9B() {
        // Day 9B
        System.out.println("=== DAY 9B ===");

        intCodeProcessorService.reset();
        // Set up the BOOST program in sensor boost mode
        intCodeProcessorService.setCpuInputValue(2L);
        // Run it
        intCodeProcessorService.runToCompletion();

        Long result = intCodeProcessorService.getProgramOutput();
        System.out.println("Day 9B: Program Output = " + result);
//        Day 9B: Program Output = 75202
//        Time elapsed:	251 ms

        return result;
    }

}





