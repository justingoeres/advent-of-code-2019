package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day04.RunDay04;
import org.junit.Assert;
import org.junit.Test;

public class Day04Test {
    static String XX = "04";

    @Test
    public void Day4A() {
        try {
            int result = RunDay04.problem4A();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day4B() {
        try {
            int result = RunDay04.problem4B();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
