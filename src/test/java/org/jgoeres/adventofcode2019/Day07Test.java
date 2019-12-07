package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day07.RunDay07;
import org.junit.Assert;
import org.junit.Test;

public class Day07Test {
    static String XX = "07";

    @Test
    public void Day07A() {
        try {
            int result = RunDay07.problem7A();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day07B() {
        try {
            int result = RunDay07.problem7B();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
