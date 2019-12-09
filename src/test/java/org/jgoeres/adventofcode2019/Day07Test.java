package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day07.IntCodeProcessorDay07;
import org.jgoeres.adventofcode2019.Day07.RunDay07;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Day07Test {
    static String XX = "07";
    IntCodeProcessorDay07 intCodeProcessorService;

    @Test
    public void Day7AExample1() {
//        Max thruster signal 43210 (from phase setting sequence 4,3,2,1,0):
//        3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0

        intCodeProcessorService = new IntCodeProcessorDay07("data/day07/example1.txt");

        ArrayList<Integer> phaseSettings = new ArrayList<>();
        phaseSettings.add(4);
        phaseSettings.add(3);
        phaseSettings.add(2);
        phaseSettings.add(1);
        phaseSettings.add(0);

        int result = intCodeProcessorService.runAmplifierStages(phaseSettings);
        Assert.assertEquals(43210, result);
    }

    @Test
    public void Day7AExample2() {
//        Max thruster signal 54321 (from phase setting sequence 0,1,2,3,4):
//
//        3,23,3,24,1002,24,10,24,1002,23,-1,23,
//                101,5,23,23,1,24,23,23,4,23,99,0,0

        intCodeProcessorService = new IntCodeProcessorDay07("data/day07/example2.txt");
        ArrayList<Integer> phaseSettings = new ArrayList<Integer>();
        phaseSettings.add(0);
        phaseSettings.add(1);
        phaseSettings.add(2);
        phaseSettings.add(3);
        phaseSettings.add(4);

        int result = intCodeProcessorService.runAmplifierStages(phaseSettings);
        Assert.assertEquals(54321, result);
    }

    @Test
    public void Day7AExample3() {
//        Max thruster signal 65210 (from phase setting sequence 1,0,4,3,2):
//
//        3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
//                1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0

        intCodeProcessorService = new IntCodeProcessorDay07("data/day07/example3.txt");
        ArrayList<Integer> phaseSettings = new ArrayList<>();
        phaseSettings.add(1);
        phaseSettings.add(0);
        phaseSettings.add(4);
        phaseSettings.add(3);
        phaseSettings.add(2);

        int result = intCodeProcessorService.runAmplifierStages(phaseSettings);
        Assert.assertEquals(65210, result);
    }

    @Test
    public void Day07A() {
        int result = 0;
        try {
            result = RunDay07.problem7A();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(914828, result);
    }

    @Test
    public void Day7BExample4() {
//        Max thruster signal 139629729 (from phase setting sequence 9,8,7,6,5):
//
//        3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
//        27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5
        intCodeProcessorService = new IntCodeProcessorDay07("data/day07/example4.txt");

        Queue<Integer> phaseSettings = new LinkedList<>();
        phaseSettings.add(9);
        phaseSettings.add(8);
        phaseSettings.add(7);
        phaseSettings.add(6);
        phaseSettings.add(5);

        intCodeProcessorService.initParallelAmplifierStages();
        int result = intCodeProcessorService.runParallelAmplifierStages(phaseSettings).intValue();

        Assert.assertEquals(139629729, result);
    }

    @Test
    public void Day7BExample5() {
//        Max thruster signal 18216 (from phase setting sequence 9,7,8,5,6):
//
//        3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
//                -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
//                53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10
        intCodeProcessorService = new IntCodeProcessorDay07("data/day07/example5.txt");

        Queue<Integer> phaseSettings = new LinkedList<>();
        phaseSettings.add(9);
        phaseSettings.add(7);
        phaseSettings.add(8);
        phaseSettings.add(5);
        phaseSettings.add(6);

        intCodeProcessorService.initParallelAmplifierStages();
        int result = intCodeProcessorService.runParallelAmplifierStages(phaseSettings).intValue();

        Assert.assertEquals(18216, result);
    }

    @Test
    public void Day07B() {
        int result = 0;
        try {
            result = RunDay07.problem7B().intValue();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(17956613, result);
    }
}
