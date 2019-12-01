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

    public Integer calculateTotalFuel() throws Exception {
        int totalFuel = 0;

        for (Integer moduleMass : moduleMasses) {
            Integer thisFuel = calculationModuleFuel(moduleMass);
            totalFuel += thisFuel;
        }
        return totalFuel;
    }

    public int calculationModuleFuel(int moduleMass) {
        int moduleFuel = (moduleMass / 3) - 2;
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
