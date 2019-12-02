package org.jgoeres.adventofcode2019.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FuelCalculationService {

    private final String DEFAULT_INPUTS_PATH = "data/day01/input.txt";

    private ArrayList<Integer> moduleMasses = new ArrayList<>();

    public FuelCalculationService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public FuelCalculationService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public int calculateModuleFuelSimple(int moduleMass) {
//        Fuel required to launch a given module is based on its mass.
//        Specifically, to find the fuel required for a module, take its mass,
//        divide by three, round down, and subtract 2.

        int moduleFuel = (moduleMass / 3) - 2;
        return moduleFuel;
    }

    public int calculateModuleFuel(int moduleMass) {
//        Fuel itself requires fuel just like a module - take its mass, divide by three,
//        round down, and subtract 2. However, that fuel also requires fuel, and that fuel
//        requires fuel, and so on. Any mass that would require negative fuel should instead
//        be treated as if it requires zero fuel; the remaining mass, if any, is instead
//        handled by wishing really hard, which has no mass and is outside the scope of this
//        calculation.
//
//        So, for each module mass, calculate its fuel and add it to the total. Then, treat
//        the fuel amount you just calculated as the input mass and repeat the process,
//        continuing until a fuel requirement is zero or negative.

        // Calculate the fuel required for this mass
        int moduleFuel = calculateModuleFuelSimple(moduleMass);

        // If the mass of fuel is >0, add in the fuel required for THAT too
        if (moduleFuel > 0) {
            moduleFuel += calculateModuleFuel(moduleFuel);
            return moduleFuel;
        } else {
            // No more fuel required, stop recursing.
            return 0;
        }
    }

    public int calculateTotalFuelSimple() throws Exception {
        int totalFuel = 0;

        for (Integer moduleMass : moduleMasses) {
            Integer thisFuel = calculateModuleFuelSimple(moduleMass);
//            System.out.println(moduleMass + "\t"+thisFuel);
            totalFuel += thisFuel;
        }
        return totalFuel;
    }

    public int calculateTotalFuelExpert() throws Exception {
        int totalFuel = 0;

        for (Integer moduleMass : moduleMasses) {
            Integer thisFuel = calculateModuleFuel(moduleMass);
            totalFuel += thisFuel;
        }
        return totalFuel;
    }

    private void loadInputs(String pathToFile) {
        moduleMasses.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                nextInt = Integer.parseInt(line);

                moduleMasses.add(nextInt);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
