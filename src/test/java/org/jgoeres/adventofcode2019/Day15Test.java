package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day15.RunDay15;
import org.junit.Assert;
import org.junit.Test;

public class Day15Test {
    static String DAY = "15";

    @Test
    public void Day15A() {
        int result = 0;
        try {
            result = RunDay15.problem15A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day15B() {
        int result = 0;
        try {
            result = RunDay15.problem15B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
