package org.jgoeres.adventofcode2019.Day01;


public abstract class RunDay01 {
    static String pathToInputs = "data/day01/input.txt";
//    static FuelCalculationService fuelCalculationService = new FuelCalculationService(pathToInputs);
    static FuelCalculationService fuelCalculationService = new FuelCalculationService();

    static Integer totalFuelRequired = null;

    public static void problem1A() {
        // Day 01A
        System.out.println("=== DAY 1A ===");

        try {
            totalFuelRequired = fuelCalculationService.calculateTotalFuel();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Day 1A: Total Fuel Required = " + totalFuelRequired);
//        Day 1A: Total Fuel Required = 3412207
//        Time elapsed:	3 ms
    }

    public static void problem1B() {
        // Day 01B
        // You notice that the device repeats the same frequency change list over and over.
        // To calibrate the device, you need to find the first frequency it reaches twice.
        System.out.println("=== DAY 1B ===");

        // This MUST run after problem1A() above, or the frequencyHistogram in
        // frequencyChangeService won't be initialized.
//        System.out.println("Scanning for duplicates...");
////        System.out.print("Pass: ");
//        int pass = 1;
//        while (true) {
////            System.out.println("Pass at totalFrequencyChange = " + totalFrequencyChange);
////            System.out.print(pass);
//            try {
//                totalFuelRequired = frequencyChangeService.calculateTotalFrequencyChange(totalFuelRequired);
//            } catch (Exception e) {
//                System.out.println("Exception: " + e.getMessage() + " on pass #" + pass);
//
//                break;
//            }
////            System.out.print(", ");
//            pass = pass + 1;
//        }
        // Answer: 655 after 138 passes
    }
}





