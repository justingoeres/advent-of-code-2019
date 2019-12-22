package org.jgoeres.adventofcode2019.Day22;

public abstract class RunDay22 {
    static String DAY = "22";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static SpaceCardService spaceCardService = new SpaceCardService();

    public static int problem22A() {
        // Day 22A
        System.out.println("=== DAY " + DAY + "A ===");

        int result = spaceCardService.doAllShuffles(2019);

        System.out.println("Day " + DAY + "A: Position of card 2019 after all shuffles = " + result);

//        Day 22A: Position of card 2019 after all shuffles = 4086
//        Time elapsed:	134 ms

        return result;
    }

    public static int problem22B() {
        // Day 22B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = 0;

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 22B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
