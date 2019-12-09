package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.jgoeres.adventofcode2019.Day02.RunDay02;
import org.junit.Assert;
import org.junit.Test;

public class Day02Test {
    IntCodeProcessorService intCodeProcessorService;

    @Test
    public void Day2AExample1() {
        intCodeProcessorService = new IntCodeProcessorService("data/day02/example1.txt");
        intCodeProcessorService.runToCompletion();
        long result = intCodeProcessorService.getValueAtPosition(0L);
        Assert.assertEquals(3500, result);
    }

    @Test
    public void Day2A() {
        long result = 0;
        try {
            result = RunDay02.problem2A();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(4138687L, result);
    }

    @Test
    public void Day2B() {
        long result = 0;
        try {
            result = RunDay02.problem2B();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(6635, result);
    }
}
