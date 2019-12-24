package org.jgoeres.adventofcode2019.Day24;

public abstract class RunDay24 {
    static String DAY = "24";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static BugService bugService = new BugService();

    public static int problem24A() {
        // Day 24A
        System.out.println("=== DAY " + DAY + "A ===");

        int result = bugService.runUntilDuplicate();

        System.out.println("Day " + DAY + "A: Biodiversity of first duplicate layout = " + result);

//        Day 24A: Biodiversity of first duplicate layout = 32506911
//        Time elapsed:	122 ms

        return result;
    }

    public static int problem24B() {
        // Day 24B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = 0;

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 24B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
