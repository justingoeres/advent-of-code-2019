package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day22.RunDay22;
import org.jgoeres.adventofcode2019.Day22.SpaceCardService;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static java.math.BigInteger.ZERO;

public class Day22Test {
    static String DAY = "22";

    @Test
    public void Day22AExample1() {
        SpaceCardService spaceCardService = new SpaceCardService("data/day22/example1.txt", 10);
        long result = 0;
        int[] expectedCardPositions = {9, 2, 5, 8, 1, 4, 7, 0, 3, 6};
        int deckSize = expectedCardPositions.length;
        ArrayList<Integer> expectedCardPositionsList = new ArrayList<>();
        for (int i = 0; i < deckSize; i++) {
            expectedCardPositionsList.add(expectedCardPositions[i]);
        }
        try {
            for (int i = 0; i < deckSize; i++) {
                result = spaceCardService.doAllShuffles(i);
//                System.out.println("Final position of card " + i + ":\t" + result);
                Assert.assertEquals(expectedCardPositionsList.indexOf(i), result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day22A() {
        long result = 0;
        try {
            result = RunDay22.problem22A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(4086, result);
    }

    @Test
    public void Day22BExample1() {
        SpaceCardService spaceCardService = new SpaceCardService("data/day22/example1.txt", 10);
        int[] finalCardPositions = {9, 2, 5, 8, 1, 4, 7, 0, 3, 6};
        int deckSize = finalCardPositions.length;
        try {
            for (int finalCardPosition = 0; finalCardPosition < deckSize; finalCardPosition++) {
                long originalCardPosition = spaceCardService.undoAllShuffles(finalCardPosition);
//                System.out.println("Final position of card " + i + ":\t" + result);
                Assert.assertEquals(finalCardPositions[finalCardPosition], originalCardPosition);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day22B1Shuffle() {
        long NUM_OF_SHUFFLES = 1;
        SpaceCardService spaceCardServicePartB = new SpaceCardService();

        long result = 0;
        long originalCardPosition = 0L;
        long finalCardPosition = 4086;      // The final card position from part A
        for (int i = 0; i < NUM_OF_SHUFFLES; i++) {
            // Find the original position of this card before this iteration of all the shuffles
            originalCardPosition = spaceCardServicePartB.undoAllShuffles(finalCardPosition);
            // Set it to the new "final" position so we can continue going back in time
            System.out.println(originalCardPosition);
            finalCardPosition = originalCardPosition;
        }
        Assert.assertEquals(2019, originalCardPosition);
    }

    @Test
    public void Day22BFromReddit() {
        long DECK_SIZE = 119315717514047L;
        long NUM_OF_SHUFFLES = 101741582076661L;

        BigInteger result = ZERO;
        SpaceCardService spaceCardService = new SpaceCardService(DECK_SIZE);
        try {
            result = spaceCardService.redditSolution(2020L, NUM_OF_SHUFFLES);    // How long does it take card 0 to get back to position 0?
//            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1041334417227L, result.longValue());
    }

    @Test
    public void Day22B() {
        BigInteger result = ZERO;
        try {
            result = RunDay22.problem22B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1041334417227L, result.longValue());
    }
}
