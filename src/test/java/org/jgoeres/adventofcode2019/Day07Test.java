package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day07.IntCodeProcessorDay07;
import org.jgoeres.adventofcode2019.Day07.RunDay07;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Day07Test {
    static String XX = "07";
    IntCodeProcessorDay07 intCodeProcessorService;

    @Test
    public void Day7AExample1() {
//        Max thruster signal 43210 (from phase setting sequence 4,3,2,1,0):
//        3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0

        intCodeProcessorService = new IntCodeProcessorDay07("data/day07/example1.txt");
//        /**** STAGE 1 ****/
//        // First amplifier's phase setting is 4
//        intCodeProcessorService.setCpuInputValue(4);
//        // First amplifier's input value is 0
//        intCodeProcessorService.setCpuInputValue(0);
//        intCodeProcessorService.runToCompletion();
//        stageOutput = intCodeProcessorService.getProgramOutput();
//
//        /**** STAGE 2 ****/
//        intCodeProcessorService.reset();
//        // Next amplifier's phase setting is 3
//        intCodeProcessorService.setCpuInputValue(3);
//        // Next amplifier's input value is the output of the previous stage
//        intCodeProcessorService.setCpuInputValue(stageOutput);
//        intCodeProcessorService.runToCompletion();
//        stageOutput = intCodeProcessorService.getProgramOutput();
//
//        /**** STAGE 3 ****/
//        intCodeProcessorService.reset();
//        // Next amplifier's phase setting is 2
//        intCodeProcessorService.setCpuInputValue(2);
//        // Next amplifier's input value is the output of the previous stage
//        intCodeProcessorService.setCpuInputValue(stageOutput);
//        intCodeProcessorService.runToCompletion();
//        stageOutput = intCodeProcessorService.getProgramOutput();
//
//        /**** STAGE 4 ****/
//        intCodeProcessorService.reset();
//        // Next amplifier's phase setting is 1
//        intCodeProcessorService.setCpuInputValue(1);
//        // Next amplifier's input value is the output of the previous stage
//        intCodeProcessorService.setCpuInputValue(stageOutput);
//        intCodeProcessorService.runToCompletion();
//        stageOutput = intCodeProcessorService.getProgramOutput();
//
//        /**** STAGE 5 ****/
//        intCodeProcessorService.reset();
//        // Next amplifier's phase setting is 0
//        intCodeProcessorService.setCpuInputValue(0);
//        // Next amplifier's input value is the output of the previous stage
//        intCodeProcessorService.setCpuInputValue(stageOutput);
//        intCodeProcessorService.runToCompletion();
//        int result = intCodeProcessorService.getProgramOutput();

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
        ArrayList<Integer> phaseSettings = new ArrayList<>();
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
        try {
            int result = RunDay07.problem7A();

            Assert.assertEquals(914828, result);
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
