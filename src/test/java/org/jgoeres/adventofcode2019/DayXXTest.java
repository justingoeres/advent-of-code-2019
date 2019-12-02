package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.DayXX.RunDayXX;
import org.junit.Assert;
import org.junit.Test;

public class DayXXTest {
    static String XX = "XX";

    @Test
    public void DayXXA() {
        try {
            int result = RunDayXX.problemXXA();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void DayXXB() {
        try {
            int result = RunDayXX.problemXXB();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
