package org.jgoeres.adventofcode2019.Day18;

public abstract class RunDay18 {
    static String DAY = "18";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static VaultService vaultService = new VaultService();

    public static int problem18A() {
        // Day 18A
        System.out.println("=== DAY " + DAY + "A ===");

        vaultService.enumerateAllRoutes();
        VaultService.Journey shortestJourney = vaultService.explore();
        int result = shortestJourney.getTotalDistance();

        System.out.println("Day " + DAY + "A: Fewest steps to collect all keys = " + result);

//        Day 18A: Fewest steps to collect all keys = 4520
//        Time elapsed:	106412 ms

        return result;
    }

    public static int problem18B() {
        VaultServicePartB vaultServicePartB = new VaultServicePartB();

        // Day 18B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = 0;
        vaultServicePartB.enumerateAllRoutes();
        VaultServicePartB.SystemStateB shortestState = vaultServicePartB.explore();
        result = shortestState.getTotalDistance();

        System.out.println("Day " + DAY + "B: Fewest steps to collect all keys = " + result);

//        Day 18B: Fewest steps to collect all keys = 1540
//        Time elapsed:	147302 ms

        return result;
    }

}
