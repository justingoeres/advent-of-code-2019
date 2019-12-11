package org.jgoeres.adventofcode2019.Day11;

import static org.jgoeres.adventofcode2019.Day11.Color.WHITE;
import static org.jgoeres.adventofcode2019.common.AoCMath.ORIGIN;

public abstract class RunDay11 {
    static String XX = "11";
    static String YY = XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static HullPaintingService hullPaintingService = new HullPaintingService();

    public static int problem11A() {
        // Day 11A
        System.out.println("=== DAY " + XX + "A ===");
        int result = hullPaintingService.paintTheHull();
        hullPaintingService.printHull();
        System.out.println("Day " + XX + "A: Number of panels painted = " + result);

//        Day 11A: Number of panels painted = 1894
//        Time elapsed:	216 ms
        return result;
    }

    public static int problem11B() {
        // Day 11B
        System.out.println("=== DAY " + XX + "B ===");

        // Reset the robot, program, & hull to the start conditions
        // but make the initial panel White
        hullPaintingService.reset(WHITE);
        int result = hullPaintingService.paintTheHull();
        hullPaintingService.printHull();

//   ## #  # #### #    ####   ## ###  #  #
//    # # #     # #       #    # #  # #  #
//    # ##     #  #      #     # ###  ####
//    # # #   #   #     #      # #  # #  #
// #  # # #  #    #    #    #  # #  # #  # #
//  ##  #  # #### #### ####  ##  ###  #  #
//        Time elapsed:	12 ms
        return result;
    }

}
