package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day06.Day06Service;
import org.jgoeres.adventofcode2019.Day06.RunDay06;
import org.junit.Assert;
import org.junit.Test;

public class Day06Test {
    static String XX = "06";


    @Test
    public void Day06AExample1() {
        try {
            Day06Service day06Service = new Day06Service("data/day06/example1.txt");

            int result = 0;
            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void Day06A() {
        try {
            int result = RunDay06.problem6A();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day06B() {
        try {
            int result = RunDay06.problem6B();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
