package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day17.RunDay17;
import org.jgoeres.adventofcode2019.DayXX.RunDayXX;
import org.junit.Assert;
import org.junit.Test;

public class Day17Test {
    static String DAY = "17";

    @Test
    public void Day17A() {
        int result = 0;
        try {
            result = RunDay17.problem17A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(5948, result);
    }

    @Test
    public void Day17B() {
        Long result = 0L;
        try {
            result = RunDay17.problem17B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(997790L, (long) result);
    }
}
