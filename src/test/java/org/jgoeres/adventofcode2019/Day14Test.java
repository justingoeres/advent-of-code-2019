package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day14.ReactionService;
import org.jgoeres.adventofcode2019.Day14.RunDay14;
import org.junit.Assert;
import org.junit.Test;

public class Day14Test {
    static String DAY = "14";

    @Test
    public void Day14AExample1() {
        ReactionService reactionService = new ReactionService("data/day14/example1.txt");

        Long result = 0L;
        try {
            reactionService.manufacture("FUEL", 1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(31L, (long) result);
    }

    @Test
    public void Day14AExample2() {
        ReactionService reactionService = new ReactionService("data/day14/example2.txt");

        Long result = 0L;
        try {
            reactionService.manufacture("FUEL", 1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(165L, (long) result);
    }

    @Test
    public void Day14AExample3() {
        ReactionService reactionService = new ReactionService("data/day14/example3.txt");

        Long result = 0L;
        try {
            reactionService.manufacture("FUEL", 1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(13312L, (long) result);
    }

    @Test
    public void Day14AExample4() {
        ReactionService reactionService = new ReactionService("data/day14/example4.txt");

        Long result = 0L;
        try {
            reactionService.manufacture("FUEL", 1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(180697L, (long) result);
    }

    @Test
    public void Day14AExample5() {
        ReactionService reactionService = new ReactionService("data/day14/example5.txt");

        Long result = 0L;
        try {
            reactionService.manufacture("FUEL", 1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(2210736L, (long) result);
    }

    @Test
    public void Day14A() {
        Long result = 0L;
        try {
            result = RunDay14.problem14A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(143173L, (long) result);
    }

    @Test
    public void Day14BExample3() {
        ReactionService reactionService = new ReactionService("data/day14/example3.txt");
        final Long ONE_TRILLION = 1000000000000L;
        Long result = 0L;
        try {
            result = reactionService.findMaxFuelForOre(ONE_TRILLION);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(82892753L, (long) result);
    }

    @Test
    public void Day14BExample4() {
        ReactionService reactionService = new ReactionService("data/day14/example4.txt");
        final Long ONE_TRILLION = 1000000000000L;
        Long result = 0L;
        try {
            result = reactionService.findMaxFuelForOre(ONE_TRILLION);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(5586022L, (long) result);
    }

    @Test
    public void Day14BExample5() {
        ReactionService reactionService = new ReactionService("data/day14/example5.txt");
        final Long ONE_TRILLION = 1000000000000L;
        Long result = 0L;
        try {
            result = reactionService.findMaxFuelForOre(ONE_TRILLION);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(460664L, (long) result);
    }

    @Test
    public void Day14B() {
        long result = 0;
        try {
            result = RunDay14.problem14B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(8845261L, result);
    }
}
