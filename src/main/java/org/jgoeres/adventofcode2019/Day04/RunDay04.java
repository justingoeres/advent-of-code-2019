package org.jgoeres.adventofcode2019.Day04;

public abstract class RunDay04 {
    static String XX = "4";
    static String YY = "0" + XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static SecureContainerService secureContainerService = new SecureContainerService();

    public static int problem4A() {
        // Day 4A
        System.out.println("=== DAY " + XX + "A ===");

        int result = secureContainerService.countValidPasswords();

        System.out.println("Day " + XX + "A: # of valid passwords in range = " + result);

//        Day 4A: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

    public static int problem4B() {
        // Day 4B
        System.out.println("=== DAY " + XX + "B ===");

        int result = 0;

        System.out.println("Day " + XX + "B: Answer = " + result);

//        Day 4B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
