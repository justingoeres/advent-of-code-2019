package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day03.XYPoint;
import org.jgoeres.adventofcode2019.Day10.AsteroidAngle;
import org.jgoeres.adventofcode2019.Day10.AsteroidMonitorService;
import org.jgoeres.adventofcode2019.Day10.RunDay10;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeSet;

public class Day10Test {
    static String XX = "10";



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
    public void Day10BExample6() {
//        .#....#####...#..
//        ##...##.#####..##
//        ##...#...#.#####.
//        ..#.....X...###..
//        ..#.#.....#....##

//        ......234.....6..
//        ......1...5.....7
//        .................
//        ........X....89..     << final asteroid is #9 here (14,3)
//        .................


        AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example6.txt");
        XYPoint c = new XYPoint(8, 3);
        XYPoint lastDestroyed = asteroidMonitorService.vaporizeAsteroidsFromPoint(c);
        int result = asteroidMonitorService.calculateDay10BAnswerValue(lastDestroyed);
        Assert.assertEquals(1403, result);
    }

    @Test
    public void Day10BExample5() {
        // 01234567890123456789
//     0   .#..##.###...####### 0
//     1   ##.############..##. 1
//     2   .#.######.########.# 2
//     3   .###.#######.####.#. 3
//     4   #####.##.#.##.###.## 4
//     5   ..#####..#.######### 5
//     6   #################### 6
//     7   #.####....###.#.#.## 7
//     8   ##.################# 8
//     9   #####.##.###..####.. 9
//     0   ..######..##.####### 0
//     1   ####.##.####...##..# 1
//     2   .#####..#.######.### 2
//     3   ##...#.####X#####... 3
//     4   #.##########.####### 4
//     5   .####.#.###.###.#.## 5
//     6   ....##.##.###..##### 6
//     7   .#.#.###########.### 7
//     8   #.#.#.#####.####.### 8
//     9   ###.##.####.##.#..## 9
        // 01234567890123456789

        AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example5.txt");
        XYPoint c = new XYPoint(11, 13);
        XYPoint lastDestroyed = asteroidMonitorService.vaporizeAsteroidsFromPoint(c);
        int result = asteroidMonitorService.calculateDay10BAnswerValue(lastDestroyed);
        Assert.assertEquals(1101, result);
    }

    @Test
    public void Day10BExample5WithLimit() {
        AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService("data/day10/example5.txt");
        XYPoint c = new XYPoint(11, 13);
        XYPoint lastDestroyed = asteroidMonitorService.vaporizeAsteroidsFromPoint(c,199);
        int result = asteroidMonitorService.calculateDay10BAnswerValue(lastDestroyed);
        Assert.assertEquals(906, result);
    }

    @Test
    public void Day10B() {
        int result = 0;
        try {
            result = RunDay10.problem10B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1707, result);
    }
}
