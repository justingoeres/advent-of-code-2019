package org.jgoeres.adventofcode2019.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FuelCalculationService {

    private final String DEFAULT_INPUTS_PATH = "data/day01/input.txt";

    private ArrayList<Integer> moduleMasses = new ArrayList<>();
    private HashMap<Integer, Integer> frequencyHistogram = new HashMap<>();

    public FuelCalculationService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public FuelCalculationService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Integer calculateTotalFuelSimple() throws Exception {
        int totalFuel = 0;

        for (Integer moduleMass : moduleMasses) {
            Integer thisFuel = calculateModuleFuelSimple(moduleMass);
            totalFuel += thisFuel;
        }
        return totalFuel;
    }

    public int calculateModuleFuelSimple(int moduleMass) {
//        Fuel required to launch a given module is based on its mass.
//        Specifically, to find the fuel required for a module, take its mass,
//        divide by three, round down, and subtract 2.

        int moduleFuel = (moduleMass / 3) - 2;
        return moduleFuel;
    }

    public Integer calculateTotalFuelExpert() throws Exception {
        int totalFuel = 0;

        for (Integer moduleMass : moduleMasses) {
            Integer thisFuel = calculateModuleFuelExpert(moduleMass);
            totalFuel += thisFuel;
        }
        return totalFuel;
    }

    public int calculateModuleFuelExpert(int moduleMass) {
        int moduleFuel = 0;
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

        int massUnaccountedFor = moduleMass;
        int fuelRequired = 0;
        while (true) {
        // Do this until the amount of fuel required is zero or less
            fuelRequired = calculateModuleFuelSimple(massUnaccountedFor);
            if (fuelRequired > 0) {
                // If this module still  may) require fuel
                moduleFuel += fuelRequired;
                massUnaccountedFor = fuelRequired;
            }
            else {
                // We're done
                break;
            }
        }
        return moduleFuel;
    }

    private void loadInputs(String pathToFile) {
        moduleMasses.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextFrequency = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                nextFrequency = Integer.parseInt(line);

                moduleMasses.add(nextFrequency);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
