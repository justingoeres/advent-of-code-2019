package org.jgoeres.adventofcode2019.Day12;

public abstract class RunDay12 {
    static String XX = "#";
    static String YY = "0" + XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static NBodyService NBodyService = new NBodyService();

    public static int problem12A() {
        // Day 12A
        System.out.println("=== DAY " + XX + "A ===");

        int numTicks = 1000;
        NBodyService.simulate(numTicks);
        int result = NBodyService.calculateTotalSystemEnergy();

        System.out.println("Day " + XX + "A: Total system energy after " + numTicks + " steps = " + result);

//        Day #A: Total system energy after 1000 steps = 11384
//        Time elapsed:	75 ms

        return result;
    }

    public static int problem12B() {
        // Day 12B
        System.out.println("=== DAY " + XX + "B ===");

        int result = 0;

        System.out.println("Day " + XX + "B: Answer = " + result);

//        Day 12B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
