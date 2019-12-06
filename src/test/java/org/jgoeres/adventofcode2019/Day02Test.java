package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.jgoeres.adventofcode2019.Day02.RunDay02;
import org.junit.Assert;
import org.junit.Test;

public class Day02Test {
    IntCodeProcessorService intCodeProcessorService;

    @Test
    public void Day2AExample1()
    {
        intCodeProcessorService = new IntCodeProcessorService("data/day02/example1.txt");
        intCodeProcessorService.runToCompletion();
        Assert.assertEquals(3500, intCodeProcessorService.getValueAtPosition(0));
    }

    @Test
    public void Day2A() {
        try {
            int result = RunDay02.problem2A();

            Assert.assertEquals(4138687, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day2B() {
        try {
            int result = RunDay02.problem2B();

            Assert.assertEquals(6635, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
