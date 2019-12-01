package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day01.FuelCalculationService;
import org.junit.Assert;
import org.junit.Test;

public class Day01Test {
    FuelCalculationService fuelCalculationService = new FuelCalculationService();

    @Test
    public void Day1AExample1()
    {
        Assert.assertEquals(2, fuelCalculationService.calculateModuleFuelSimple(12));
    }

    @Test
    public void Day1AExample2()
    {
        Assert.assertEquals(2, fuelCalculationService.calculateModuleFuelSimple(14));
    }

    @Test
    public void Day1AExample3()
    {
        Assert.assertEquals(654, fuelCalculationService.calculateModuleFuelSimple(1969));
    }

    @Test
    public void Day1AExample4()
    {
        Assert.assertEquals(33583, fuelCalculationService.calculateModuleFuelSimple(100756));
    }

    @Test
    public void Day1BExample1()
    {
//        At first, a module of mass 1969 requires 654 fuel.
//        Then, this fuel requires 216 more fuel (654 / 3 - 2).
//        216 then requires 70 more fuel, which requires 21 fuel, which requires 5 fuel,
//        which requires no further fuel.
//        So, the total fuel required for a module of mass 1969 is
//        654 + 216 + 70 + 21 + 5 = 966.
        Assert.assertEquals(966, fuelCalculationService.calculateModuleFuelExpert(1969));
    }

    @Test
    public void Day1BExample2()
    {
//        The fuel required by a module of mass 100756 and its fuel is:
//        33583 + 11192 + 3728 + 1240 + 411 + 135 + 43 + 12 + 2 = 50346.
        Assert.assertEquals(50346, fuelCalculationService.calculateModuleFuelExpert(100756));
    }



}
