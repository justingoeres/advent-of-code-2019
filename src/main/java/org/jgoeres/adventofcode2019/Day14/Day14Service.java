package org.jgoeres.adventofcode2019.Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day14Service {
    private final String DAY = "14";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private ArrayList<Integer> inputList = new ArrayList<>();

    public Day14Service() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public Day14Service(String pathToFile) {
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
