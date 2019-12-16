package org.jgoeres.adventofcode2019.Day16;

import jdk.nashorn.api.tree.BreakTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class FFTService {
    private final String DAY = "16";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    int[] BASE_FFT_PATTERN = {0, 1, 0, -1};
    private final boolean DISPLAY = false;

    private ArrayList<Integer> inputList = new ArrayList<>();

    public FFTService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public FFTService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public int calculateFFT(int numPhases) {
        int result = 0;
        // FFT operates in repeated phases. In each phase, a new list is constructed with the
        // same length as the input list. This new list is also used as the input for the next phase.

        // Each element in the new list is built by multiplying every value in the input list by a
        // value in a repeating pattern and then adding up the results.
        ArrayList<Integer> resultList = runPhases(numPhases);
        String resultString = inputListToString(resultList);
        // Take the first 8 digits of the final output
        result = Integer.parseInt(resultString.substring(0, 8));
        return result;
    }

    public ArrayList<Integer> runPhases(int numPhases) {
        ArrayList<Integer> currentInput = inputList;
        for (int i = 0; i < numPhases; i++) {
            // Calculate all the output elements of this phase
            ArrayList<Integer> nextInput = calculateAllElements(currentInput);
            // Set the new outputs as inputs to the next phase
            currentInput = nextInput;

            /** DEBUG **/
            if (DISPLAY) {
                String nextInputString = inputListToString(nextInput);
                System.out.println();
                System.out.println("After " + (i + 1) + " phases: " + nextInputString);
                System.out.println();
            }
        }
        return currentInput;    // The final result ends up in currentInput
    }

    public ArrayList<Integer> calculateAllElements(ArrayList<Integer> inputList) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            int result = calculateElement(inputList, i);
            results.add(result);
        }
        return results;
    }

    public int calculateElement(ArrayList<Integer> inputList, Integer elementIndex) {
        String debug = "";

        int elementValue = 0;
        int patternIndex = 1;   // Starts at 1 to skip the first item on the first term of each element
//        int patternIndex = 0;
        int[] fftPattern = BASE_FFT_PATTERN;
//        int[] fftPattern = {1, 2, 3};
        for (Integer element : inputList) {
            int coefficient = patternCoefficient(elementIndex, patternIndex, fftPattern);
            int nextValue = element * coefficient;
            /** DEBUG **/
            if (DISPLAY) {
                debug += element + "*" + coefficient + "  + ";
            }

            elementValue += nextValue;
            patternIndex++;

        }
        // Take just the ones digit (make it positive)
        elementValue = Math.abs(elementValue % 10);

        /** DEBUG **/
        if (DISPLAY) {
            debug = debug.substring(0, debug.length() - 2) + "= " + elementValue;
            System.out.println(debug);
        }
        return elementValue;
    }

    private int patternCoefficient(int elementIndex, int patternIndex, int[] fftPattern) {
        // Get the correct element of the pattern array,
        // accounting for wrapping and also the extended
        // pattern size of later elements

        // repeat each value in the pattern a number of times equal to the position
        // in the output list being considered. Repeat once for the first element,
        // twice for the second element, three times for the third element, and so on.
        // So, if the third element of the output list is being calculated,
        // repeating the values would produce: 0, 0, 0, 1, 1, 1, 0, 0, 0, -1, -1, -1.
        patternIndex = patternIndex / (elementIndex + 1);

        int i = patternIndex % fftPattern.length;   // wrap the index
        return fftPattern[i];
    }

    private String inputListToString(ArrayList<Integer> inputList) {
        String nextInputString = "";
        for (Integer element : inputList) {
            nextInputString += element.toString();
        }
        return nextInputString;
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
