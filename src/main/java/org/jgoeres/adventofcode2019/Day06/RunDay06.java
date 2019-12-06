package org.jgoeres.adventofcode2019.Day06;

public abstract class RunDay06 {
    static String XX = "6";
    static String YY = "0" + XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static OrbiterService orbiterService = new OrbiterService();

    public static int problem6A() {
        // Day 6A
        System.out.println("=== DAY " + XX + "A ===");

        int result = orbiterService.calculateAllOrbits();

        System.out.println("Day " + XX + "A: Answer = " + result);
//        Day 6A: Answer = 333679
//        Time elapsed:	27 ms

        return result;
    }

    public static int problem6B() {
        // Day 6B
        System.out.println("=== DAY " + XX + "B ===");

        int result = orbiterService.calculateTransfers("YOU", "SAN");

        System.out.println("Day " + XX + "B: Answer = " + result);
//        Day 6B: Answer = 370
//        Time elapsed:	3 ms

        return result;
    }

}
