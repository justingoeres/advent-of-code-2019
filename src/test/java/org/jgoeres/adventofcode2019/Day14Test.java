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

        int result = 0;
        try {
//            result = reactionService.calculateRequirements("ORE","FUEL");
            reactionService.manufacture("FUEL",1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(31, result);
    }

    @Test
    public void Day14AExample2() {
        ReactionService reactionService = new ReactionService("data/day14/example2.txt");

        int result = 0;
        try {
//            result = reactionService.calculateRequirements("ORE","FUEL");
            reactionService.manufacture("FUEL",1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(165, result);
    }

    @Test
    public void Day14AExample3() {
        ReactionService reactionService = new ReactionService("data/day14/example3.txt");

        int result = 0;
        try {
//            result = reactionService.calculateRequirements("ORE","FUEL");
            reactionService.manufacture("FUEL",1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(13312, result);
    }

    @Test
    public void Day14AExample4() {
        ReactionService reactionService = new ReactionService("data/day14/example4.txt");

        int result = 0;
        try {
//            result = reactionService.calculateRequirements("ORE","FUEL");
            reactionService.manufacture("FUEL",1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(180697, result);
    }

    @Test
    public void Day14AExample5() {
        ReactionService reactionService = new ReactionService("data/day14/example5.txt");

        int result = 0;
        try {
//            result = reactionService.calculateRequirements("ORE","FUEL");
            reactionService.manufacture("FUEL",1);
            result = reactionService.getOreCreated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(2210736, result);
    }

    @Test
    public void Day14A() {
        int result = 0;
        try {
            result = RunDay14.problem14A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(143173, result);
    }

    @Test
    public void Day14B() {
        int result = 0;
        try {
            result = RunDay14.problem14B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
