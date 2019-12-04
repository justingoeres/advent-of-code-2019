package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day04.RunDay04;
import org.jgoeres.adventofcode2019.Day04.SecureContainerService;
import org.junit.Assert;
import org.junit.Test;

public class Day04Test {
    static String XX = "04";

    SecureContainerService secureContainerService = new SecureContainerService();

    @Test
    public void TestPasswordIsValid() {
        final int[] testPasswords = {
                223456,
                335566,
                123477,
                455678,
                156889,
                888888
        };
        for (int testPassword : testPasswords) {
            boolean result = secureContainerService.isValid(testPassword);
            Assert.assertTrue(result);
        }
    }

    @Test
    public void TestPasswordIsNotValid() {
        final int[] testPasswords = {
                123456, //  no double
                345678, //  no double
                456789, //  no double
                445502, //  decreasing digit
                999998, //  decreasing digit
                512333, //  decreasing digit
                231572, //  no double, decreasing digit
        };
        for (int testPassword : testPasswords) {
            boolean result = secureContainerService.isValid(testPassword);
            Assert.assertFalse(result);
        }
    }


    @Test
    public void Day4A() {
        try {
            int result = RunDay04.problem4A();

            Assert.assertEquals(511, result);
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
