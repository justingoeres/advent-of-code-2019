package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day09.RunDay09;
import org.junit.Assert;
import org.junit.Test;

public class Day09Test {
    static String XX = "09";

    @Test
    public void Day9A() {
        int result = 0;
        try {
            result = RunDay09.problem9A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1560, result);
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
        Assert.assertEquals(0, result);
    }
}
