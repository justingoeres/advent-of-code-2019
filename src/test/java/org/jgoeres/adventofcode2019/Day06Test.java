package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day06.OrbiterService;
import org.jgoeres.adventofcode2019.Day06.RunDay06;
import org.junit.Assert;
import org.junit.Test;

public class Day06Test {
    static String XX = "06";


    @Test
    public void Day06AExample1() {
        try {
            OrbiterService orbiterService = new OrbiterService("data/day06/example1.txt");

            int result = orbiterService.calculateAllOrbits();
            Assert.assertEquals(42, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void Day06A() {
        try {
            int result = RunDay06.problem6A();

            Assert.assertEquals(333679, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day06AExample2() {
        try {
            OrbiterService orbiterService = new OrbiterService("data/day06/example2.txt");

            int result = orbiterService.calculateTransfers("YOU", "SAN");
            Assert.assertEquals(4, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void Day06B() {
        try {
            int result = RunDay06.problem6B();

            Assert.assertEquals(370, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
