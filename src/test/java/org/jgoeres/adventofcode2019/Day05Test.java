package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day05.IntCodeProcessorService;
import org.jgoeres.adventofcode2019.Day05.RunDay05;
import org.junit.Assert;
import org.junit.Test;

public class Day05Test {
    IntCodeProcessorService intCodeProcessorService;

    @Test
    public void Day5AWithDay2Example1() {
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
    public void Day5AExample1() {
//        The program 3,0,4,0,99 outputs whatever it gets as input, then halts.
        intCodeProcessorService = new IntCodeProcessorService("data/day05/example1.txt");
        intCodeProcessorService.setCpuInputValue(555);
        intCodeProcessorService.runToCompletion();

        int result = intCodeProcessorService.getProgramOutput();
        Assert.assertEquals(555, result);
    }

    @Test
    public void Day5A() {
        try {
            int result = RunDay05.problem5A();

//            Your puzzle answer was 9775037.
            Assert.assertEquals(9775037, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day5BExample2() {
//                3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
//                1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
//                999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99
//        The above example program uses an input instruction to ask for a single number.
//        The program will then
//        output 999 if the input value is below 8,
//        output 1000 if the input value is equal to 8, or
//        output 1001 if the input value is greater than 8.
        intCodeProcessorService = new IntCodeProcessorService("data/day05/example2.txt");

        final int[][] testVectors = {
                {-2, 999},   //        output 999 if the input value is below 8,
                {7, 999},   //        output 999 if the input value is below 8,
                {8, 1000},  //        output 1000 if the input value is equal to 8, or
                {9, 1001},   //        output 1001 if the input value is greater than 8.
                {9999, 1001}   //        output 1001 if the input value is greater than 8.
        };

        for (int testVector[] : testVectors) {
            intCodeProcessorService.reset();
            intCodeProcessorService.setCpuInputValue(testVector[0]);
            intCodeProcessorService.runToCompletion();
            int result = intCodeProcessorService.getProgramOutput();
            Assert.assertEquals(testVector[1], result);
        }
    }

    @Test
    public void Day5B() {
        try {
            int result = RunDay05.problem5B();

            Assert.assertEquals(15586959, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
