package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day16.FFTService;
import org.jgoeres.adventofcode2019.Day16.RunDay16;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Day16Test {
    static String DAY = "16";

    @Test
    public void testCalculateElement() {
        FFTService fftService = new FFTService();

        ArrayList<Integer> inputList = new ArrayList<>();
        inputList.add(9);
        inputList.add(8);
        inputList.add(7);
        inputList.add(6);
        inputList.add(5);
        fftService.calculateAllElements(inputList);

    }

    @Test
    public void testDay16AExample1() {
        FFTService fftService = new FFTService("data/day16/example1.txt");
        int result = 0;
        try {
            result = fftService.calculateFFT(4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1029498, result);
    }

    @Test
    public void testDay16AExample2() {
        FFTService fftService = new FFTService("data/day16/example2.txt");
        int result = 0;
        try {
            result = fftService.calculateFFT(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(24176176, result);
    }

    @Test
    public void testDay16AExample3() {
        FFTService fftService = new FFTService("data/day16/example3.txt");
        int result = 0;
        try {
            result = fftService.calculateFFT(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(73745418, result);
    }

    @Test
    public void testDay16AExample4() {
        FFTService fftService = new FFTService("data/day16/example4.txt");
        int result = 0;
        try {
            result = fftService.calculateFFT(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(52432133, result);
    }

    @Test
    public void Day16A() {
        int result = 0;
        try {
            result = RunDay16.problem16A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(94960436, result);
    }

    @Test
    public void Day16B() {
        int result = 0;
        try {
            result = RunDay16.problem16B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(410, result);
    }
}
