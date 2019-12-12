package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day12.NBodyService;
import org.jgoeres.adventofcode2019.Day12.RunDay12;
import org.junit.Assert;
import org.junit.Test;

public class Day12Test {
    static String XX = "12";


    @Test
    public void Day12AExample1() {
         NBodyService NBodyService = new NBodyService("data/day12/example1.txt");

        int finalEnergy = 0;
        try {
            NBodyService.simulate(10);
             finalEnergy = NBodyService.calculateTotalSystemEnergy();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(179, finalEnergy);
    }

    @Test
    public void Day12AExample2() {
        NBodyService NBodyService = new NBodyService("data/day12/example2.txt");

        int finalEnergy = 0;
        try {
            NBodyService.simulate(100);
            finalEnergy = NBodyService.calculateTotalSystemEnergy();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1940, finalEnergy);
    }

    @Test
    public void Day12A() {
        int result = 0;
        try {
            result = RunDay12.problem12A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(11384, result);
    }

    @Test
    public void Day12B() {
        int result = 0;
        try {
            result = RunDay12.problem12B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
