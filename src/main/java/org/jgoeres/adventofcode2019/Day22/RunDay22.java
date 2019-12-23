package org.jgoeres.adventofcode2019.Day22;

import java.math.BigInteger;

import static java.math.BigInteger.ZERO;

public abstract class RunDay22 {
    static String DAY = "22";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static SpaceCardService spaceCardService = new SpaceCardService();

    public static long problem22A() {
        // Day 22A
        System.out.println("=== DAY " + DAY + "A ===");

        long result = spaceCardService.doAllShuffles(2019);

        System.out.println("Day " + DAY + "A: Position of card 2019 after all shuffles = " + result);

//        Day 22A: Position of card 2019 after all shuffles = 4086
//        Time elapsed:	134 ms

        return result;
    }

    public static BigInteger problem22B() {
        // Day 22B
        System.out.println("=== DAY " + DAY + "B ===");
        long DECK_SIZE = 119315717514047L;
        long NUM_OF_SHUFFLES = 101741582076661L;
        long FINAL_CARD_POSITION = 2020L;

        BigInteger result = ZERO;
        SpaceCardService spaceCardService = new SpaceCardService(DECK_SIZE);

        result = spaceCardService.redditSolution(FINAL_CARD_POSITION, NUM_OF_SHUFFLES);    // How long does it take card 0 to get back to position 0?

        System.out.println("Day " + DAY + "B: Original position of card in final position 2020 = " + result);

//        Day 22B: Original position of card in final position 2020 = 1041334417227
//        Time elapsed:	xxx ms

        return result;
    }

}
