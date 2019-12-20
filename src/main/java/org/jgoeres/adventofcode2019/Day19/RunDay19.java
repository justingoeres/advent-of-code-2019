package org.jgoeres.adventofcode2019.Day19;

public abstract class RunDay19 {
    static String DAY = "19";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static TractorBeamService tractorBeamService = new TractorBeamService();

    public static int problem19A() {
        // Day 19A
        System.out.println("=== DAY " + DAY + "A ===");

        int result = 0;
        tractorBeamService.createTractorBeamMap();
//        tractorBeamService.printAreaMap();
        result = tractorBeamService.getTractorBeamSize();

        System.out.println("Day " + DAY + "A: Tractor beam area = " + result);
//        Day 19A: Tractor beam area = 118
//        Time elapsed:	723 ms

        return result;
    }

    public static int problem19B() {
        // Day 19B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = tractorBeamService.fitSantasShip();
        System.out.println("Day " + DAY + "B: Closest fit for Santa's gigantic ship = " + result);

//        Day 19B: Closest fit for Santa's gigantic ship = 18651593
//        Time elapsed:	533 ms

        return result;
    }

}
