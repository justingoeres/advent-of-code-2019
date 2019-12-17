package org.jgoeres.adventofcode2019.Day16;

public abstract class RunDay16 {
    static String DAY = "16";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static FFTService fftService = new FFTService();
    static final boolean PART_A = false;
    static final boolean PART_B = true;

    public static int problem16A() {
        // Day 16A
        System.out.println("=== DAY " + DAY + "A ===");

        int result = fftService.calculateFFT(100);

        System.out.println("Day " + DAY + "A: First 8 digits after 100 phases = " + result);

//        Day 16A: First 8 digits after 100 phases = 94960436
//        Time elapsed:	381 ms

        return result;
    }

    public static int problem16B() {
        // Day 16B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = fftService.calculateFFTPartB(100);;


        System.out.println("Day " + DAY + "B: Eight-digit message = " + result);

//        Day 16B: Eight-digit message = 57762756
//        Time elapsed:	2561 ms

        return result;
    }

}
