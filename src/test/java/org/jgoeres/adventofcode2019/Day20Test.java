package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day20.DonutMazeService;
import org.jgoeres.adventofcode2019.Day20.RunDay20;
import org.junit.Assert;
import org.junit.Test;

public class Day20Test {
    static String DAY = "20";

    @Test
    public void Day20AExample1() {
        DonutMazeService donutMazeService = new DonutMazeService("data/day20/example1.txt");
        int result = 0;
        try {
            result = donutMazeService.explore();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(23, result);
    }

    @Test
    public void Day20AExample2() {
        DonutMazeService donutMazeService = new DonutMazeService("data/day20/example2.txt");
        int result = 0;
        try {
            result = donutMazeService.explore();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(58, result);
    }


    @Test
    public void Day20A() {
        int result = 0;
        try {
            result = RunDay20.problem20A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(602, result);
    }

    @Test
    public void Day20BExample1() {
        DonutMazeService donutMazeService = new DonutMazeService("data/day20/example1.txt");
        int result = 0;
        try {
            result = donutMazeService.explore3d();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(26, result);
    }

    @Test
    public void Day20BExample3() {
        DonutMazeService donutMazeService = new DonutMazeService("data/day20/example3.txt");
        int result = 0;
        try {
            result = donutMazeService.explore3d();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(396, result);
    }

    @Test
    public void Day20B() {
        int result = 0;
        try {
            result = RunDay20.problem20B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(6986, result);
    }
}
