package org.jgoeres.adventofcode2019.Day20;

public abstract class RunDay20 {
    static String DAY = "20";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static DonutMazeService donutMazeService = new DonutMazeService();

    public static int problem20A() {
        // Day 20A
        System.out.println("=== DAY " + DAY + "A ===");

        int result = donutMazeService.explore();

        System.out.println("Day " + DAY + "A: Steps from AA to ZZ = " + result);

//        Day 20A: Steps from AA to ZZ = 602
//        Time elapsed:	136 ms

        return result;
    }

    public static int problem20B() {
        // Day 20B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = donutMazeService.explore3d();

        System.out.println("Day " + DAY + "B: Steps from AA to ZZ = " + result);

//        Day 20B: Steps from AA to ZZ = 6986
//        Time elapsed:	2462 ms

        return result;
    }

}
