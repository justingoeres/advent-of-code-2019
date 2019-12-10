package org.jgoeres.adventofcode2019.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AsteroidMonitorService {
    private final String XX = "10";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private ArrayList<Integer> inputList = new ArrayList<>();

    public AsteroidMonitorService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public AsteroidMonitorService(String pathToFile) {
        loadInputs(pathToFile);
    }


    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                nextInt = Integer.parseInt(line);

                inputList.add(nextInt);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
