package org.jgoeres.adventofcode2019.Day11;

import static org.jgoeres.adventofcode2019.common.AoCMath.ORIGIN;

public abstract class RunDay11 {
    static String XX = "11";
    static String YY = XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static HullPaintingService hullPaintingService = new HullPaintingService();
    static PaintingRobot paintingRobot = new PaintingRobot(ORIGIN);

    public static int problem11A() {
        // Day 11A
        System.out.println("=== DAY " + XX + "A ===");
        int result = hullPaintingService.paintTheHull();

        System.out.println("Day " + XX + "A: Number of panels painted = " + result);

//        Day 11A: Number of panels painted = 1894
//        Time elapsed:	216 ms

        return result;
    }

    public static int problem11B() {
        // Day 11B
        System.out.println("=== DAY " + XX + "B ===");

        int result = 0;

        System.out.println("Day " + XX + "B: Answer = " + result);

//        Day 11B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
