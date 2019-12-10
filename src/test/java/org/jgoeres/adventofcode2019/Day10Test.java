package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day10.AsteroidMonitorService;
import org.jgoeres.adventofcode2019.Day10.RunDay10;
import org.junit.Assert;
import org.junit.Test;

public class Day10Test {
    static String XX = "10";

    @Test
    public void Day10AExample1() {
        int result = 0;
        try {
            AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example1.txt");
            System.out.println(asteroidMonitorService);
            result = RunDay10.problem10A();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day10A() {
        int result = 0;
        try {
            result = RunDay10.problem10A();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day10B() {
        int result = 0;
        try {
            result = RunDay10.problem10B();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
