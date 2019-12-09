package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day09.RunDay09;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.junit.Assert;
import org.junit.Test;

public class Day09Test {
    static String XX = "09";

    IntCodeProcessorService intCodeProcessorService;

    @Test
    public void Day9AExample1() {
//        109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99
//        takes no input and produces a copy of itself as output.

        intCodeProcessorService = new IntCodeProcessorService("data/day09/example1.txt");

        intCodeProcessorService.runToCompletion();
        long result = intCodeProcessorService.getProgramOutput();
        Assert.assertEquals(99L, result);
    }

    @Test
    public void Day9AExample2() {
//        1102,34915192,34915192,7,4,7,99,0
//        should output a 16-digit number.

        intCodeProcessorService = new IntCodeProcessorService("data/day09/example2.txt");

        intCodeProcessorService.runToCompletion();
        long result = intCodeProcessorService.getProgramOutput();
        Assert.assertEquals(1219070632396864L, result);
    }

    @Test
    public void Day9AExample3() {
//        104,1125899906842624,99
//        should output the large number in the middle.

        intCodeProcessorService = new IntCodeProcessorService("data/day09/example3.txt");

        intCodeProcessorService.runToCompletion();
        long result = intCodeProcessorService.getProgramOutput();
        Assert.assertEquals(1125899906842624L, result);
    }

    @Test
    public void Day9A() {
        long result = 0L;
        try {
            result = RunDay09.problem9A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(4080871669L, result);
    }

    @Test
    public void Day9B() {
        int result = 0;
        try {
            RunDay09.problem9B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // No test for this – just prints result
        Assert.assertEquals(75202L, result);
    }
}
