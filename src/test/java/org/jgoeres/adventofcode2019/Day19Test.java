package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day19.RunDay19;
import org.junit.Assert;
import org.junit.Test;

public class Day19Test {
    static String DAY = "19";

    @Test
    public void Day19A() {
        int result = 0;
        try {
            result = RunDay19.problem19A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(118, result);
    }

    @Test
    public void Day19B() {
        int result = 0;
        try {
            result = RunDay19.problem19B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(18651593, result);
    }
}
