package org.jgoeres.adventofcode2019.Day13;

public abstract class RunDay13 {
    static String DAY = "13  ";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static ArcadeService arcadeService = new ArcadeService();

    public static int problem13A() {
        // Day 13A
        System.out.println("=== DAY " + DAY + "A ===");

        int result = arcadeService.playTheGame();

        System.out.println("Day " + DAY + "A: Number of Blocks on screen = " + result);

//        Day 13  A: Number of Blocks on screen = 213
//        Time elapsed:	104 ms

        return result;
    }

    public static int problem13B() {
        // Day 13B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = 0;

        // Reset the game to play again!
        arcadeService.reset();
        arcadeService.setFreePlay(true);
        arcadeService.playTheGamePartB();

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 13B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
