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
        bugService = new BugService("data/day24/inputPartB.txt");

        // Day 24B
        System.out.println("=== DAY " + DAY + "B ===");

        bugService.runNGenerations(200);
        int result = bugService.countBugs();

        System.out.println("Day " + DAY + "B: Number of bugs after 200 minutes = " + result);

//        Day 24B: Number of bugs after 200 minutes = 2025
//        Time elapsed:	933 ms

        return result;
    }

}
