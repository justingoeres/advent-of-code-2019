package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day01.FuelCalculationService;
import org.jgoeres.adventofcode2019.Day02.IntCodeProcessorService;
import org.junit.Assert;
import org.junit.Test;

public class Day02Test {
    IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService("data/day02/example1.txt");

    @Test
    public void Day2AExample1()
    {
        intCodeProcessorService.runToCompletion();
        Assert.assertEquals(3500, intCodeProcessorService.getValueAtPosition(0));
    }


//    @Test
//    public void Day1BExample1()
//    {
////        At first, a module of mass 1969 requires 654 fuel.
////        Then, this fuel requires 216 more fuel (654 / 3 - 2).
////        216 then requires 70 more fuel, which requires 21 fuel, which requires 5 fuel,
////        which requires no further fuel.
////        So, the total fuel required for a module of mass 1969 is
////        654 + 216 + 70 + 21 + 5 = 966.
//        Assert.assertEquals(966, intCodeProcessorService.calculateModuleFuel(1969));
//    }
//
//    @Test
//    public void Day1BExample2()
//    {
////        The fuel required by a module of mass 100756 and its fuel is:
////        33583 + 11192 + 3728 + 1240 + 411 + 135 + 43 + 12 + 2 = 50346.
//        Assert.assertEquals(50346, intCodeProcessorService.calculateModuleFuel(100756));
//    }



}
