package org.jgoeres.adventofcode2019.Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FFTService {
    private final String DAY = "16";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private ArrayList<Integer> inputList = new ArrayList<>();

    public FFTService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public FFTService(String pathToFile) {
        loadInputs(pathToFile);
    }

    

    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                for (int i = 0; i < line.length(); i++) {
                    nextInt = Character.getNumericValue(line.charAt(i));
                    inputList.add(nextInt);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
