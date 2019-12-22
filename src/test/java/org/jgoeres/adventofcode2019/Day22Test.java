package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day22.RunDay22;
import org.jgoeres.adventofcode2019.Day22.SpaceCardService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Day22Test {
    static String DAY = "22";

    @Test
    public void Day22AExample1() {
        SpaceCardService spaceCardService = new SpaceCardService("data/day22/example1.txt", 10);
        int result = 0;
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
        int result = 0;
        try {
            result = RunDay22.problem22A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(4086, result);
    }

    @Test
    public void Day22B() {
        int result = 0;
        try {
            result = RunDay22.problem22B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
