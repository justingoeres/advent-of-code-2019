package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day24.BugService;
import org.jgoeres.adventofcode2019.Day24.RunDay24;
import org.junit.Assert;
import org.junit.Test;

public class Day24Test {
    static String DAY = "24";

    @Test
    public void Day24AExample1() {
        BugService bugService = new BugService("data/day24/example1.txt");
        int result = 0;
        try {
//            bugService.runNGenerations(4);
           result = bugService.runUntilDuplicate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(2129920, result);
    }

    @Test
    public void Day24A() {
        int result = 0;
        try {
            result = RunDay24.problem24A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(32506911, result);
    }

    @Test
    public void Day24BExample1() {
        BugService bugService = new BugService("data/day24/example1PartB.txt");
        int result = 0;
        try {
//            bugService.runNGenerations(4);
            bugService.runNGenerations(11);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(2129920, result);
    }

    @Test
    public void Day24BExample2() {
        BugService bugService = new BugService("data/day24/example2PartB.txt");
        int result = 0;
        try {
//            bugService.runNGenerations(4);
            bugService.runNGenerations(11);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(2129920, result);
    }

    @Test
    public void Day24B() {
        int result = 0;
        try {
            result = RunDay24.problem24B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
