package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day03.RunDay03;
import org.jgoeres.adventofcode2019.DayXX.RunDayXX;
import org.junit.Assert;
import org.junit.Test;

public class Day03Test {
    static String XX = "XX";

    @Test
    public void Day3A() {
        try {
            int result = RunDay03.problem3A();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day3B() {
        try {
            int result = RunDay03.problem3B();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
