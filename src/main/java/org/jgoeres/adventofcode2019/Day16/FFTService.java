package org.jgoeres.adventofcode2019.Day16;

import jdk.nashorn.api.tree.BreakTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FFTService {
    private final String DAY = "16";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private int offset;
    private boolean DISPLAY = false;

    private List<Integer> inputList = new ArrayList<>();

    public FFTService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public FFTService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public int calculateFFT(int numPhases) {
        int result;
        // FFT operates in repeated phases. In each phase, a new list is constructed with the
        // same length as the input list. This new list is also used as the input for the next phase.

        // Each element in the new list is built by multiplying every value in the input list by a
        // value in a repeating pattern and then adding up the results.
        List<Integer> resultList = runPhases(numPhases);
        String resultString = inputListToString(resultList);
        // Take the first 8 digits of the final output
        result = Integer.parseInt(resultString.substring(0, 8));
        return result;
    }

    public int calculateFFTPartB(int numPhases) {
        // This gets us the input list extended 10000x, but then chopped so we only have the segment of thost 6.5 million
        // elements that comes AFTER our area of interest
        extendInputList(10000);
        List<Integer> E = inputList;

        for (int i = 0; i < numPhases; i++) {
            // Process all the phases
            // The set E for the next phase is always the sum-mod-10 of element N+1 and element N-of-the-previous-phase
            // So we start at the END of the list and work backwards
            int result = 0;
            for (int n = E.size() - 1 - 1; n >= 0; n--) {   //  Start from "end minus 1" because the last digit never changes
                int newValue = (E.get(n) + E.get(n + 1)) % 10;
                E.set(n, newValue);
            }
        }
        List<Integer> resultList = E.subList(0, 8);
        String resultString = inputListToString(resultList);
        int result = Integer.parseInt(resultString);
        return result;
    }

    public List<Integer> runPhases(int numPhases) {
        List<Integer> currentInput = inputList;
        for (int i = 0; i < numPhases; i++) {
            // Calculate all the output elements of this phase
            List<Integer> nextInput;
            nextInput = calculateAllElements(currentInput);
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

    public List<Integer> calculateAllElements(List<Integer> inputList) {
        ArrayList<Integer> results = new ArrayList<>(inputList.size()); // preallocate the size of the incoming list
        for (int i = 0; i < inputList.size(); i++) {
            int result = calculateElement(inputList, i);
            results.add(result);
        }
        return results;
    }

    public int calculateElement(List<Integer> inputList, final Integer elementIndex) {
        // Since our pattern is {0,1,0,-1} there's a pattern in how things contribute to the value of each element.
        // 1. Start at index 'elementIndex' in inputList. Everything coefficient before this is zero.
        // 2. Starting from that index, get a subset of length 'elementIndex', sum it, and add to the running total.
        // 3. Skip ahead by 'elementIndex' steps – these coefficients are all zero.
        // 4. Starting from that new index, get a new subset of length 'elementIndex', sum it, and subtract from the running total.
        // 5. Skip ahead by 'elementIndex' steps again.
        // 6. Repeat from 2.
        String debug = "";

        int elementValue = 0;

        int index = 0;
        int nextStart = elementIndex;
        int step = elementIndex + 1;
        outer:
        while (true) {
            // 2. Starting from 'elementIndex', get a subset of length 'elementIndex', sum it, and add to the running total.
            for (index = nextStart; index < nextStart + step; index++) {
                if (index >= inputList.size()) break outer;
                // Add up the next 'elementIndex' elements
                elementValue += inputList.get(index);
                /** DEBUG **/
                if (DISPLAY) {
                    debug += inputList.get(index) + "*1  + ";
                }
            }
            // 3. Skip ahead by 'elementIndex' steps – these coefficients are all zero.
            nextStart = nextStart + step + step;
            // 4. Starting from that new index, get a new subset of length 'elementIndex', sum it, and subtract from the running total.
            for (index = nextStart; index < nextStart + step; index++) {
                if (index >= inputList.size()) break outer;
                // Add up the next 'elementIndex' elements
                elementValue -= inputList.get(index);
                /** DEBUG **/
                if (DISPLAY) {
                    debug += inputList.get(index) + "*-1 + ";
                }
            }

            // 5. Skip ahead by 'elementIndex' steps again.
            nextStart = nextStart + step + step;

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

    private String inputListToString(List<Integer> inputList) {
        String nextInputString = "";
        for (Integer element : inputList) {
            nextInputString += element.toString();
        }
        return nextInputString;
    }

    public void extendInputList(int factor) {
        // (Part B) The real signal is your puzzle input repeated 10000 times.

        // Preallocate the new array
        List<Integer> extendedInputList = new ArrayList<Integer>(inputList.size() * factor);
        for (int i = 0; i < factor; i++) {
            // Add (another) repeat of the original list onto the extended one.
            extendedInputList.addAll(inputList);
        }
        // When done, get the relevant subset of the extended list (from the 'offset' to the end)
        offset = Integer.parseInt(inputListToString(inputList.subList(0, 7)));
        int end = extendedInputList.size(); // -1
        inputList = extendedInputList.subList(offset, end);
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
