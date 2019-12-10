package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day03.XYPoint;
import org.jgoeres.adventofcode2019.Day10.AsteroidMonitorService;
import org.jgoeres.adventofcode2019.Day10.RunDay10;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Day10Test {
    static String XX = "10";

    @Test
    public void listAllAngles() {
        AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example1.txt");
        XYPoint c = new XYPoint(2, 2);
        ArrayList<XYPoint> asteroids = asteroidMonitorService.listVisibleAsteroidsFromPoint(c);
        asteroidMonitorService.listAllAngles(asteroids, c);
    }

    @Test
    public void Day10AExample1() {
        int result = 0;
        try {
            AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example1.txt");
//            int visible = asteroidMonitorService.visibleAsteroidsFromPoint(new XYPoint(0,0));
            result = asteroidMonitorService.findMaxVisibleAsteroids().getNumVisible();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(8, result);
    }

    @Test
    public void Day10AExample2() {
        int result = 0;
        try {
            AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example2.txt");
            result = asteroidMonitorService.findMaxVisibleAsteroids().getNumVisible();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(33, result);
    }

    @Test
    public void Day10AExample3() {
        int result = 0;
        try {
            AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example3.txt");
            result = asteroidMonitorService.findMaxVisibleAsteroids().getNumVisible();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(35, result);
    }

    @Test
    public void Day10AExample4() {
        int result = 0;
        try {
            AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example4.txt");
            result = asteroidMonitorService.findMaxVisibleAsteroids().getNumVisible();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(41, result);
    }

    @Test
    public void Day10AExample5() {
        int result = 0;
        try {
            AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example5.txt");
            result = asteroidMonitorService.findMaxVisibleAsteroids().getNumVisible();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(210, result);
    }

    @Test
    public void Day10A() {
        int result = 0;
        try {
            result = RunDay10.problem10A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(256, result);
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
