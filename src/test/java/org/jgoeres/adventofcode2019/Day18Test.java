package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day18.Day18Service;
import org.jgoeres.adventofcode2019.Day18.RunDay18;
import org.junit.Assert;
import org.junit.Test;

public class Day18Test {
    static String DAY = "18";

    @Test
    public void testDay18AExample1() {
        Day18Service day18Service = new Day18Service("data/day18/example1.txt");
        int result = 0;
        try {
//            result = day18Service();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void testDay18AExample2() {
        Day18Service day18Service = new Day18Service("data/day18/example2.txt");
        int result = 0;
        try {
//            result = day18Service();
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
