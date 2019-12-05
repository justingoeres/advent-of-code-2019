package org.jgoeres.adventofcode2019.Day04;

public abstract class RunDay04 {
    static String XX = "4";
    static String YY = "0" + XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static SecureContainerService secureContainerService = new SecureContainerService();

    public static int problem4A() {
        // Day 4A
        System.out.println("=== DAY " + XX + "A ===");

        secureContainerService.findAllValid();
        int result = secureContainerService.countValidPasswords();

        System.out.println("Day " + XX + "A: # of valid passwords in range = " + result);

//        Day 4A: # of valid passwords in range = 511
//        Time elapsed:	728 ms
        return result;
    }

    public static int problem4B() {
        // Day 4B
        System.out.println("=== DAY " + XX + "B ===");

        int result = secureContainerService.countPart2ValidPasswords();

        System.out.println("Day " + XX + "B: # of valid passwords in range = " + result);

//        Day 4B: # of valid passwords in range = 316
//        Time elapsed:	1 ms
        return result;
    }

}
