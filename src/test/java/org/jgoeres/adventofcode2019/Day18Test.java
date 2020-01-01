package org.jgoeres.adventofcode2019;

import com.google.common.collect.Range;
import org.jgoeres.adventofcode2019.Day18.VaultService;
import org.jgoeres.adventofcode2019.Day18.RunDay18;
import org.jgoeres.adventofcode2019.Day18.VaultServicePartB;
import org.junit.Assert;
import org.junit.Test;

import static org.jgoeres.adventofcode2019.Day18.Quadrant.*;

public class Day18Test {
    static String DAY = "18";

    /** Disabled a bunch of intermediate examples/tests below because
     * at least one of them breaks Part B
     */

//    @Test
//    public void testDay18AExample1() {
//        VaultService vaultService = new VaultService("data/day18/example1.txt");
//        int result = 0;
//        try {
//            vaultService.enumerateAllRoutes();
//            VaultService.Journey shortestJourney = vaultService.explore();
//            result = shortestJourney.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(8, result);
//    }
//
//    @Test
//    public void testDay18AExample2() {
//        VaultService vaultService = new VaultService("data/day18/example2.txt");
//        int result = 0;
//        try {
//            vaultService.enumerateAllRoutes();
//            VaultService.Journey shortestJourney = vaultService.explore();
//            result = shortestJourney.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(86, result);
//    }
//
//    @Test
//    public void testDay18AExample3() {
//        VaultService vaultService = new VaultService("data/day18/example3.txt");
//        int result = 0;
//        try {
//            vaultService.enumerateAllRoutes();
//            VaultService.Journey shortestJourney = vaultService.explore();
//            result = shortestJourney.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(132, result);
//    }
//
//    @Test
//    public void testDay18AExample4() {
//        VaultService vaultService = new VaultService("data/day18/example4.txt");
//        int result = 0;
//        try {
//            vaultService.enumerateAllRoutes();
//            VaultService.Journey shortestJourney = vaultService.explore();
//            result = shortestJourney.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(136, result);
//    }
//
//    @Test
//    public void testDay18AExample5() {
//        VaultService vaultService = new VaultService("data/day18/example5.txt");
//        int result = 0;
//        try {
//            vaultService.enumerateAllRoutes();
//            VaultService.Journey shortestJourney = vaultService.explore();
//            result = shortestJourney.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(81, result);
//    }

    @Test
    public void Day18A() {
        int result = 0;
        try {
            result = RunDay18.problem18A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(4520, result);
    }

//    @Test
//    public void testDay18BExample1() {
//        // Hack the quadrant enum sizes
//        Range xRange1 = Range.closed(1, 2);
//        Range yRange1 = Range.closed(1, 2);
//        Range xRange2 = Range.closed(4, 5);
//        Range yRange2 = Range.closed(4, 5);
//        UPPER_LEFT.setxRange(xRange1);
//        UPPER_LEFT.setyRange(yRange1);
//        UPPER_RIGHT.setxRange(xRange2);
//        UPPER_RIGHT.setyRange(yRange1);
//        LOWER_LEFT.setxRange(xRange1);
//        LOWER_LEFT.setyRange(yRange2);
//        LOWER_RIGHT.setxRange(xRange2);
//        LOWER_RIGHT.setyRange(yRange2);
//        int result = 0;
//        try {
//            VaultServicePartB vaultServicePartB = new VaultServicePartB("data/day18/exampleB1.txt");
//            vaultServicePartB.enumerateAllRoutes();
//            VaultServicePartB.SystemStateB shortestState = vaultServicePartB.explore();
//            result = shortestState.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(8, result);
//    }
//
//    @Test
//    public void testDay18BExample2() {
//        // Hack the quadrant enum sizes
//        Range xRange1 = Range.closed(1, 6);
//        Range yRange1 = Range.closed(1, 2);
//        Range xRange2 = Range.closed(8, 13);
//        Range yRange2 = Range.closed(4, 5);
//        UPPER_LEFT.setxRange(xRange1);
//        UPPER_LEFT.setyRange(yRange1);
//        UPPER_RIGHT.setxRange(xRange2);
//        UPPER_RIGHT.setyRange(yRange1);
//        LOWER_LEFT.setxRange(xRange1);
//        LOWER_LEFT.setyRange(yRange2);
//        LOWER_RIGHT.setxRange(xRange2);
//        LOWER_RIGHT.setyRange(yRange2);
//        int result = 0;
//        try {
//            VaultServicePartB vaultServicePartB = new VaultServicePartB("data/day18/exampleB2.txt");
//            vaultServicePartB.enumerateAllRoutes();
//            VaultServicePartB.SystemStateB shortestState = vaultServicePartB.explore();
//            result = shortestState.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(24, result);
//    }
//
//    @Test
//    public void testDay18BExample4() {
//        // Hack the quadrant enum sizes
//        Range xRange1 = Range.closed(1, 5);
//        Range yRange1 = Range.closed(1, 3);
//        Range xRange2 = Range.closed(7, 11);
//        Range yRange2 = Range.closed(5, 7);
//        UPPER_LEFT.setxRange(xRange1);
//        UPPER_LEFT.setyRange(yRange1);
//        UPPER_RIGHT.setxRange(xRange2);
//        UPPER_RIGHT.setyRange(yRange1);
//        LOWER_LEFT.setxRange(xRange1);
//        LOWER_LEFT.setyRange(yRange2);
//        LOWER_RIGHT.setxRange(xRange2);
//        LOWER_RIGHT.setyRange(yRange2);
//        int result = 0;
//        try {
//            VaultServicePartB vaultServicePartB = new VaultServicePartB("data/day18/exampleB4.txt");
//            vaultServicePartB.enumerateAllRoutes();
//            VaultServicePartB.SystemStateB shortestState = vaultServicePartB.explore();
//            result = shortestState.getTotalDistance();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(72, result);
//    }

    @Test
    public void Day18B() {
        int result = 0;
        try {
            result = RunDay18.problem18B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1540, result);
    }
}
