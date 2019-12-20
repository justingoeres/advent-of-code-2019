package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day20.DonutMazeService;
import org.jgoeres.adventofcode2019.Day20.RunDay20;
import org.jgoeres.adventofcode2019.DayXX.RunDayXX;
import org.junit.Assert;
import org.junit.Test;

public class Day20Test {
    static String DAY = "20";

    @Test
    public void Day20AExample1() {
        DonutMazeService donutMazeService = new DonutMazeService("data/day20/example1.txt");
        int result = 0;
        try {
//            result = RunDay20.problem20A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day20A() {
        int result = 0;
        try {
            result = RunDay20.problem20A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day20B() {
        int result = 0;
        try {
            result = RunDay20.problem20B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
