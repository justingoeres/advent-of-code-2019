package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day10.RunDay10;
import org.junit.Assert;
import org.junit.Test;

public class Day10Test {
    static String XX = "10";

    @Test
    public void Day10A() {
        try {
            int result = RunDay10.problem10A();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day10B() {
        try {
            int result = RunDay10.problem10B();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
