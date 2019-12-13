package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day13.RunDay13;
import org.junit.Assert;
import org.junit.Test;

public class Day13Test {
    static String DAY = "13";

    @Test
    public void Day13A() {
        int result = 0;
        try {
            result = RunDay13.problem13A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(213, result);
    }

    @Test
    public void Day13B() {
        int result = 0;
        try {
            result = RunDay13.problem13B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
