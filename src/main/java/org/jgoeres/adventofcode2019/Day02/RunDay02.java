package org.jgoeres.adventofcode2019.Day02;


public abstract class RunDay02 {
    static String pathToInputs = "data/day02/input.txt";
    //    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService(pathToInputs);
    static IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService();


    public static void problem2A() {
        // Day 2A
        System.out.println("=== DAY 2A ===");

        // Set up the processor in the 1202 alarm configuration
        intCodeProcessorService.setValueAtPosition(1, 12);
        intCodeProcessorService.setValueAtPosition(2, 2);

        // Run it
        intCodeProcessorService.runToCompletion();

        System.out.println("Day 2A: Position 0 Value at Halt = " + intCodeProcessorService.getValueAtPosition(0));
//        Day 2A: Position 0 Value at Halt = 4138687
//        Time elapsed:	2 ms
    }

    public static void problem2B() {
        // Day 2B
        System.out.println("=== DAY 2B ===");

//        try {
//            totalFuelRequired = intCodeProcessorService.calculateTotalFuelExpert();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("Day 2B: TBD = " + totalFuelRequired);

        // Answer: 655 after 138 passes
    }
}





