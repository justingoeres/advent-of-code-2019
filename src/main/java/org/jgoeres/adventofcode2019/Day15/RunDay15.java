package org.jgoeres.adventofcode2019.Day15;

public abstract class RunDay15 {
    static String DAY = "15";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static RepairService repairService = new RepairService();

    public static int problem15A() {
        // Day 15A
        System.out.println("=== DAY " + DAY + "A ===");

        int result = 0;
        try {
            repairService.explore();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Day " + DAY + "A: Answer = " + result);

//        Day 15A: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

    public static int problem15B() {
        // Day 15B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = 0;

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 15B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
