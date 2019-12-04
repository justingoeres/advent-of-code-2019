package org.jgoeres.adventofcode2019.Day03;

public abstract class RunDay03 {
    static String XX = "3";
    static String YY = "0" + XX;

    public static XYPoint ORIGIN = new XYPoint(0, 0);
    static String pathToInputs = "data/day" + YY + "/input.txt";


    static WireService wireService = new WireService();

    public static int problem3A() {
        // Day XXA
        System.out.println("=== DAY " + XX + "A ===");

        XYPoint closestIntersection = wireService.findClosestIntersection(ORIGIN);
        int result = wireService.manhattanDistance(closestIntersection,ORIGIN);

        System.out.println("Day " + XX + "A: Answer = " + result);

//        Day XXA: Answer =
//        Time elapsed:	xxx ms

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

        int result = 0;

        System.out.println("Day " + XX + "B: Answer = " + result);

//        Day XXB: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
