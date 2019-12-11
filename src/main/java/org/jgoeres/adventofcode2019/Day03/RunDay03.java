package org.jgoeres.adventofcode2019.Day03;

import org.jgoeres.adventofcode2019.common.AoCMath;
import org.jgoeres.adventofcode2019.common.XYPoint;

import static org.jgoeres.adventofcode2019.common.AoCMath.ORIGIN;
import static org.jgoeres.adventofcode2019.common.AoCMath.manhattanDistance;

public abstract class RunDay03 {
    static String XX = "3";
    static String YY = "0" + XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";


    static WireService wireService = new WireService();

    public static int problem3A() {
        // Day XXA
        System.out.println("=== DAY " + XX + "A ===");

        XYPoint closestIntersection = wireService.findClosestIntersection(ORIGIN);
        int result = manhattanDistance(closestIntersection,ORIGIN);

        System.out.println("Day " + XX + "A: Distance to closest intersection = " + result);

//        Day 3A: Distance to closest intersection = 245
//        Time elapsed:	138 ms

        return result;
    }

    public static int problem3A(String pathToInputs) {
        wireService = new WireService(pathToInputs);
        int result = problem3A();
        return result;
    }

    public static int problem3B() {
        // Day XXB
        System.out.println("=== DAY " + XX + "B ===");

        int result = wireService.findLeastIntersectionSteps();

        System.out.println("Day " + XX + "B: Least steps to intersection = " + result);

//        Day 3B: Least steps to intersection = 48262
//        Time elapsed:	27 ms

        return result;
    }

}
