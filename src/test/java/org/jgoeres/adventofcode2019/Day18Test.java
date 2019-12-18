package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day18.RunDay18;
import org.junit.Assert;
import org.junit.Test;

public class Day18Test {
    static String DAY = "18";

    @Test
    public void Day18A() {
        int result = 0;
        try {
            result = RunDay18.problem18A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day18B() {
        int result = 0;
        try {
            result = RunDay18.problem18B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
