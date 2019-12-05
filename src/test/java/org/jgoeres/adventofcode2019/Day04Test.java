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
    public void TestFindNextValid() {
        final int[][] testPairs = {
                {359282, 359999},   // input lower bound
                {356000, 356666},
                {377889, 377889}    // valid, should return self
        };
        for (int testPair[] : testPairs) {
            secureContainerService.setCurrentPassword(testPair[0]);
            int result = secureContainerService.findNextValid();
            Assert.assertEquals(testPair[1], result);
        }
    }

    @Test
    public void TestHasGroupOfExactly2() {
        final int[][] testDigits = {
                {3, 7, 7, 8, 8, 9},  // valid, 2 groups of 2
                {1, 1, 1, 1, 2, 2}    // valid, group of 4 and group of 2
        };
        for (int test[] : testDigits) {
            boolean result = secureContainerService.hasGroupOfExactly2(test);

            Assert.assertTrue(result);
        }
    }

    @Test
    public void TestNotHasGroupOfExactly2() {
        final int[][] testDigits = {
                {3, 5, 9, 9, 9, 9},   // invalid, group of 4
                {3, 5, 6, 0, 0, 0}   // invalid, group of 3
        };
        for (int test[] : testDigits) {
            boolean result = secureContainerService.hasGroupOfExactly2(test);

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

            Assert.assertEquals(316, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
