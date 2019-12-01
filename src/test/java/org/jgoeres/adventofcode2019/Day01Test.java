package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day01.FuelCalculationService;
import org.junit.Assert;
import org.junit.Test;

public class Day01Test {
    FuelCalculationService fuelCalculationService = new FuelCalculationService();

    @Test
    public void Example1()
    {
        Assert.assertEquals(2, fuelCalculationService.calculationModuleFuel(12));
    }

    @Test
    public void Example2()
    {
        Assert.assertEquals(2, fuelCalculationService.calculationModuleFuel(14));
    }

    @Test
    public void Example3()
    {
        Assert.assertEquals(654, fuelCalculationService.calculationModuleFuel(1969));
    }

    @Test
    public void Example4()
    {
        Assert.assertEquals(33583, fuelCalculationService.calculationModuleFuel(100756));
    }
}
