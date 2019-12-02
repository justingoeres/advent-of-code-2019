package org.jgoeres.adventofcode2019.Day01;


public abstract class RunDay01 {
    static String pathToInputs = "data/day01/input.txt";
//    static FuelCalculationService fuelCalculationService = new FuelCalculationService(pathToInputs);
//    static FuelCalculationService fuelCalculationService = new FuelCalculationService();
    static FuelCalculationService fuelCalculationService = new FuelCalculationService("data/day01/input-fab.txt");

    static Integer totalFuelRequired = null;

    public static void problem1A() {
        // Day 1A
        System.out.println("=== DAY 1A ===");

        try {
            totalFuelRequired = fuelCalculationService.calculateTotalFuelSimple();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Day 1A: Total Fuel Required = " + totalFuelRequired);
//        Day 1A: Total Fuel Required = 3412207
//        Time elapsed:	3 ms
    }

    public static void problem1B() {
        // Day 1B
        System.out.println("=== DAY 1B ===");

        try {
            totalFuelRequired = fuelCalculationService.calculateTotalFuelExpert();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Day 1B: Total Fuel Required = " + totalFuelRequired);

        // Answer: 655 after 138 passes
    }
}





