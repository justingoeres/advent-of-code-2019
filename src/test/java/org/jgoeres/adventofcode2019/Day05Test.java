package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.jgoeres.adventofcode2019.Day05.RunDay05;
import org.junit.Assert;
import org.junit.Test;

public class Day05Test {
    IntCodeProcessorService intCodeProcessorService;

    @Test
    public void Day5AWithDay2Example1() {
        intCodeProcessorService = new IntCodeProcessorService("data/day05/day2example1.txt");
        intCodeProcessorService.runToCompletion();
        long result = intCodeProcessorService.getValueAtPosition(0L);
        Assert.assertEquals(3500L, result);
    }


    @Test
    public void Day5AWithDay2Input() {
        intCodeProcessorService = new IntCodeProcessorService("data/day05/day2input.txt");

        // Set up the processor in the 1202 alarm configuration
        intCodeProcessorService.setValueAtPosition(1L, 12L);
        intCodeProcessorService.setValueAtPosition(2L, 2L);

        intCodeProcessorService.runToCompletion();
        long result = intCodeProcessorService.getValueAtPosition(0L);
        Assert.assertEquals(4138687L, result);
    }

    @Test
    public void Day5AExample1() {
//        The program 3,0,4,0,99 outputs whatever it gets as input, then halts.
        intCodeProcessorService = new IntCodeProcessorService("data/day05/example1.txt");
        intCodeProcessorService.setCpuInputValue(555L);
        intCodeProcessorService.runToCompletion();

        long result = intCodeProcessorService.getProgramOutput();
        Assert.assertEquals(555L, result);
    }

    @Test
    public void Day5A() {
        long result = 0L;
        try {
            result = RunDay05.problem5A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//            Your puzzle answer was 9775037.
        Assert.assertEquals(9775037L, result);
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

        final Integer[][] testVectors = {
                {-2, 999},   //        output 999 if the input value is below 8,
                {7, 999},   //        output 999 if the input value is below 8,
                {8, 1000},  //        output 1000 if the input value is equal to 8, or
                {9, 1001},   //        output 1001 if the input value is greater than 8.
                {9999, 1001}   //        output 1001 if the input value is greater than 8.
        };

        for (Integer[] testVector : testVectors) {
            intCodeProcessorService.reset();
            intCodeProcessorService.setCpuInputValue(testVector[0].longValue());
            intCodeProcessorService.runToCompletion();
            int result = intCodeProcessorService.getProgramOutput().intValue();
            Assert.assertEquals(testVector[1].intValue(), result);
        }
    }

    @Test
    public void Day5B() {
        Long result = 0L;
        try {
            result = RunDay05.problem5B();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(15586959L, result.longValue());
    }
}
