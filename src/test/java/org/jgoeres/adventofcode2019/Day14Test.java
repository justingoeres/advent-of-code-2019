package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day14.RunDay14;
import org.junit.Assert;
import org.junit.Test;

public class Day14Test {
    static String DAY = "14";

    @Test
    public void Day14A() {
        int result = 0;
        try {
            result = RunDay14.problem14A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day14B() {
        int result = 0;
        try {
            result = RunDay14.problem14B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
