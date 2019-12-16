package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day16.RunDay16;
import org.junit.Assert;
import org.junit.Test;

public class Day16Test {
    static String DAY = "16";

    @Test
    public void Day16A() {
        int result = 0;
        try {
            result = RunDay16.problem16A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(380, result);
    }

    @Test
    public void Day16B() {
        int result = 0;
        try {
            result = RunDay16.problem16B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(410, result);
    }
}
