package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day08.RunDay08;
import org.junit.Assert;
import org.junit.Test;

public class Day08Test {
    static String XX = "08";

    @Test
    public void Day8A() {
        int result = 0;
        try {
            result = RunDay08.problem8A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1560, result);
    }

    @Test
    public void Day8B() {
        int result = 0;
        try {
            result = RunDay08.problem8B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
