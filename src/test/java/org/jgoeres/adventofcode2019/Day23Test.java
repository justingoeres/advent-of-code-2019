package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day23.RunDay23;
import org.junit.Assert;
import org.junit.Test;

public class Day23Test {
    static String DAY = "23";

    @Test
    public void Day23A() {
        int result = 0;
        try {
            result = RunDay23.problem23A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day23B() {
        int result = 0;
        try {
            result = RunDay23.problem23B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
