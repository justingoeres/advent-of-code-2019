package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day11.RunDay11;
import org.jgoeres.adventofcode2019.DayXX.RunDayXX;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.junit.Assert;
import org.junit.Test;

public class Day11Test {
    static String XX = "11";

    IntCodeProcessorService intCodeProcessorService = new IntCodeProcessorService("data/day11/input.txt");

    @Test
    public void Day11A() {
        int result = 0;
        try {
            result = RunDay11.problem11A();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(1894, result);
    }

    @Test
    public void Day11B() {
        int result = 0;
        try {
            result = RunDay11.problem11B();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // No pass/fail criteria for this, it just prints
        //   ## #  # #### #    ####   ## ###  #  #
        //    # # #     # #       #    # #  # #  #
        //    # ##     #  #      #     # ###  ####
        //    # # #   #   #     #      # #  # #  #
        // #  # # #  #    #    #    #  # #  # #  # #
        //  ##  #  # #### #### ####  ##  ###  #  #
    }
}
