package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day05.IntCodeProcessorService;
import org.jgoeres.adventofcode2019.Day05.RunDay05;
import org.junit.Assert;
import org.junit.Test;

public class Day05Test {
    IntCodeProcessorService intCodeProcessorService;

    @Test
    public void Day5AWithDay2Example1()
    {
        intCodeProcessorService = new IntCodeProcessorService("data/day05/day2example1.txt");
        intCodeProcessorService.runToCompletion();
        Assert.assertEquals(3500, intCodeProcessorService.getValueAtPosition(0));
    }


    @Test
    public void Day5AWithDay2Input() {
        intCodeProcessorService = new IntCodeProcessorService("data/day05/day2input.txt");

        // Set up the processor in the 1202 alarm configuration
        intCodeProcessorService.setValueAtPosition(1, 12);
        intCodeProcessorService.setValueAtPosition(2, 2);

        intCodeProcessorService.runToCompletion();
        Assert.assertEquals(4138687, intCodeProcessorService.getValueAtPosition(0));
    }

    @Test
    public void Day5AExample1()
    {
        intCodeProcessorService = new IntCodeProcessorService("data/day05/example1.txt");
        intCodeProcessorService.runToCompletion();
        Assert.assertEquals(3500, intCodeProcessorService.getValueAtPosition(0));
    }

    @Test
    public void Day5A() {
        try {
            int result = RunDay05.problem5A();

            Assert.assertEquals(4138687, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day5B() {
        try {
            int result = RunDay05.problem5B();

            Assert.assertEquals(6635, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}