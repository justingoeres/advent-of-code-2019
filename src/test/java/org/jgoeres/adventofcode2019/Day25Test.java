package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day25.RunDay25;
import org.junit.Assert;
import org.junit.Test;

public class Day25Test {
    static String DAY = "25";

    @Test
    public void Day25A() {
        int result = 0;
        try {
            result = RunDay25.problem25A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }

    // There is no Day 25B
}
