package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day18.VaultService;
import org.jgoeres.adventofcode2019.Day18.RunDay18;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Day18Test {
    static String DAY = "18";

    @Test
    public void testDay18AExample1() {
        VaultService vaultService = new VaultService("data/day18/example1.txt");
        int result = 0;
        try {
//            result = day18Service();
            vaultService.enumerateAllRoutes();
            ArrayList<VaultService.Journey> journeys = vaultService.explore();
            result = vaultService.findShortestJourney(journeys).getTotalDistance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(8, result);
    }

    @Test
    public void testDay18AExample2() {
        VaultService vaultService = new VaultService("data/day18/example2.txt");
        int result = 0;
        try {
//            result = day18Service();
            vaultService.enumerateAllRoutes();
            ArrayList<VaultService.Journey> journeys = vaultService.explore();
            result = vaultService.findShortestJourney(journeys).getTotalDistance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(86, result);
    }

    @Test
    public void testDay18AExample3() {
        VaultService vaultService = new VaultService("data/day18/example3.txt");
        int result = 0;
        try {
//            result = day18Service();
            vaultService.enumerateAllRoutes();
            ArrayList<VaultService.Journey> journeys = vaultService.explore();
            result = vaultService.findShortestJourney(journeys).getTotalDistance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(132, result);
    }

    @Test
    public void testDay18AExample4() {
        VaultService vaultService = new VaultService("data/day18/example4.txt");
        int result = 0;
        try {
//            result = day18Service();
            vaultService.enumerateAllRoutes();
            ArrayList<VaultService.Journey> journeys = vaultService.explore();
            result = vaultService.findShortestJourney(journeys).getTotalDistance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(136, result);
    }

    @Test
    public void testDay18AExample5() {
        VaultService vaultService = new VaultService("data/day18/example5.txt");
        int result = 0;
        try {
//            result = day18Service();
            vaultService.enumerateAllRoutes();
            ArrayList<VaultService.Journey> journeys = vaultService.explore();
            result = vaultService.findShortestJourney(journeys).getTotalDistance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(81, result);
    }

    @Test
    public void testDay18AInput() {
        VaultService vaultService = new VaultService("data/day18/input.txt");
        int result = 0;
        try {
//            result = day18Service();
            vaultService.enumerateAllRoutes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day18A() {
        int result = 0;
        try {
            result = RunDay18.problem18A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day18B() {
        int result = 0;
        try {
            result = RunDay18.problem18B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
