package org.jgoeres.adventofcode2019.Day14;

public abstract class RunDay14 {
    static String DAY = "14";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static ReactionService reactionService = new ReactionService();

    public static int problem14A() {
        // Day 14A
        System.out.println("=== DAY " + DAY + "A ===");

        reactionService.manufacture("FUEL",1);
        int result = reactionService.getOreCreated();

        System.out.println("Day " + DAY + "A: Answer = " + result);

//        Day 14A: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

    public static int problem14B() {
        // Day 14B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = 0;

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 14B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
