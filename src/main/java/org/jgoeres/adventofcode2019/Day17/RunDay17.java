package org.jgoeres.adventofcode2019.Day17;

public abstract class RunDay17 {
    static String DAY = "17";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static ScaffoldService scaffoldService = new ScaffoldService();

    public static int problem17A() {
        // Day 17A
        System.out.println("=== DAY " + DAY + "A ===");
        int result = 0;
        scaffoldService.createScaffoldMap();
        result = scaffoldService.calculateAlignmentParameters();

        System.out.println("Day " + DAY + "A: Total of alignment parameters = " + result);

//        Day 17A: Total of alignment parameters = 5948
//        Time elapsed:	241 ms

        return result;
    }

    public static int problem17B() {
        // Day 17B
        System.out.println("=== DAY " + DAY + "B ===");

        scaffoldService = new ScaffoldService();

        scaffoldService.createScaffoldMap();
        scaffoldService.printAreaMap();
        scaffoldService.explore();
        int result = 0;

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 17B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
