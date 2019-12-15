package org.jgoeres.adventofcode2019.Day14;

public abstract class RunDay14 {
    static String DAY = "14";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static ReactionService reactionService = new ReactionService();

    public static Long problem14A() {
        // Day 14A
        System.out.println("=== DAY " + DAY + "A ===");

        reactionService.manufacture("FUEL",1);
        Long result = reactionService.getOreCreated();

        System.out.println("Day " + DAY + "A: Minimum ore to produce 1 fuel = " + result);

//        Day 14A: Minimum ore to produce 1 fuel = 143173
//        Time elapsed:	152 ms

        return result;
    }

    public static long problem14B() {
        // Day 14B
        System.out.println("=== DAY " + DAY + "B ===");
        final Long ONE_TRILLION = 1000000000000L;

        long result = reactionService.findMaxFuelForOre(ONE_TRILLION);

        System.out.println("Day " + DAY + "B: One trillion ore could produce " + result + " fuel.");

//        Day 14B: One trillion ore could produce 8845260 fuel.
//                Time elapsed:	83 ms

        return result;
    }

}
