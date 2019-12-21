package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day21.RunDay21;
import org.junit.Assert;
import org.junit.Test;

public class Day21Test {
    static String DAY = "21";

//    @Test
//    public void Day21ASimpleProgram() {
//        SpringdroidService springdroidService = new SpringdroidService();
//        int result = 0;
//        try {
//            springdroidService.simpleWalkProgram();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Assert.assertEquals(0, result);
//    }

    @Test
    public void Day21A() {
        int result = 0;
        try {
            result = RunDay21.problem21A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(19353692, result);
    }

    @Test
    public void Day21B() {
        int result = 0;
        try {
            result = RunDay21.problem21B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(0, result);
    }
}
