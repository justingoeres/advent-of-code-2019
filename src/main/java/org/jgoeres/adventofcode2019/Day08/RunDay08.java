package org.jgoeres.adventofcode2019.Day08;

public abstract class RunDay08 {
    static String XX = "8";
    static String YY = "0" + XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static SpaceImageService spaceImageService = new SpaceImageService();

    public static int problem8A() {
        // Day 8A
        System.out.println("=== DAY " + XX + "A ===");

        int minZeroLayer = spaceImageService.findLayerWithFewest('0');
        System.out.println("Layer " + minZeroLayer + " has the fewest zeroes");
        int minLayerCountOnes = spaceImageService.countInLayer('1', minZeroLayer);
        int minLayerCountTwos = spaceImageService.countInLayer('2', minZeroLayer);
        System.out.println("Layer " + minZeroLayer + " has "
                + minLayerCountOnes + " ones and "
                + minLayerCountTwos + " twos");
        int result = minLayerCountOnes * minLayerCountTwos;

        System.out.println("Day " + XX + "A: " + minLayerCountOnes + " * " + minLayerCountTwos + " = " + result);
//        Layer 5 has the fewest zeroes
//        Layer 5 has 12 ones and 130 twos
//        Day 8A: 12 * 130 = 1560
//        Time elapsed:	7 ms
        return result;
    }

    public static int problem8B() {
        // Day 8B
        System.out.println("=== DAY " + XX + "B ===");

        int result = 0;

        System.out.println("Day " + XX + "B: Answer = " + result);

//        Day 8B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
