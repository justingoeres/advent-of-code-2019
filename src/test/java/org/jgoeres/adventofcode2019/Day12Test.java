package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day12.RunDay12;
import org.junit.Assert;
import org.junit.Test;

public class Day12Test {
    static String XX = "12";

    @Test
    public void Day12A() {
        int result = 0;
        try {
            result = RunDay12.problem12A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void Day12B() {
        int result = 0;
        try {
            result = RunDay12.problem12B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
