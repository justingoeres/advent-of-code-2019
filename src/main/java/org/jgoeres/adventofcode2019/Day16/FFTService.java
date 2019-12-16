package org.jgoeres.adventofcode2019.Day16;

import jdk.nashorn.api.tree.BreakTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class FFTService {
    private final String DAY = "16";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    int[] BASE_FFT_PATTERN = {0, 1, 0, -1};
    private int offsetRaw;
    private int offset;
    private boolean DISPLAY = false;
    private int prevElementValue = 0;
    private List<Integer> subList;

    private List<Integer> inputList = new ArrayList<>();

    public FFTService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public FFTService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public int calculateFFT(int numPhases, final boolean PART_B) {
        int result = 0;
        // FFT operates in repeated phases. In each phase, a new list is constructed with the
        // same length as the input list. This new list is also used as the input for the next phase.

        // Each element in the new list is built by multiplying every value in the input list by a
        // value in a repeating pattern and then adding up the results.
        List<Integer> resultList = runPhases(numPhases, PART_B);
        String resultString = inputListToString(resultList);
        // Take the first 8 digits of the final output
        result = Integer.parseInt(resultString.substring(0, 8));
        return result;
    }

    public int calculateFFTPartB(int numPhases, final boolean PART_B) {
        int result = 0;
        // The real signal is your puzzle input repeated 10000 times.
        //extendInputList(10000);

        List<Integer> phasesOutputList = runPhases(numPhases, PART_B);
        // Now that we've got the result list, find where in the list our answer is.
        // The first seven digits of your initial input signal also represent the message offset.
        // Specifically, the message offset indicates the number of digits to skip before
        // reading the eight-digit message.
        List<Integer> resultLocationList = phasesOutputList.subList(0, 6);

        String resultLocationString = inputListToString(resultLocationList);
        int resultOffset = Integer.parseInt(resultLocationString);
        List<Integer> resultList = phasesOutputList.subList(resultOffset, resultOffset + 8);

        String resultString = inputListToString(resultList);
        result = Integer.parseInt(resultString);

        return result;
    }

    public List<Integer> runPhases(int numPhases, boolean partB) {
        List<Integer> currentInput = inputList;
        for (int i = 0; i < numPhases; i++) {
            // Calculate all the output elements of this phase
            List<Integer> nextInput;
            if (partB) {
                nextInput = calculateAllElementsPartB(currentInput);
            } else {
                nextInput = calculateAllElements(currentInput);
            }
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
//            index += elementIndex + 1;

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

    public List<Integer> calculateAllElementsPartB(List<Integer> inputList) {

        ArrayList<Integer> results = new ArrayList<>(inputList.size()); // preallocate the size of the incoming list

        // Let's sum up the inputList so we know it for later
        int inputListSum = 0;
        for (Integer inputElement : inputList) {
            inputListSum += inputElement;
        }
        prevElementValue = 0;

        for (int i = 0; i < inputList.size(); i++) {
            int result = calculateElementPartB(inputList, i, inputListSum);
            results.add(result);
        }
        // TODO: This stops too early
        return results;
    }

    public int calculateElementPartB(List<Integer> inputList, final Integer elementIndex, final int inputListSum) {
        // Since our pattern is {0,1,0,-1} there's a pattern in how things contribute to the value of each element.
        // 1. Start at index 'elementIndex' in inputList. Everything coefficient before this is zero.
        // 2. Starting from that index, get a subset of length 'elementIndex', sum it, and add to the running total.
        // 3. Skip ahead by 'elementIndex' steps – these coefficients are all zero.
        // 4. Starting from that new index, get a new subset of length 'elementIndex', sum it, and subtract from the running total.
        // 5. Skip ahead by 'elementIndex' steps again.
        // 6. Repeat from 2.

        // Because the part B offsets start so far into the inputList, I think the problem degenerates.
        // For example 5...
        //      *0303673*2577212944063491565474664 becomes 84462026
        // the inputlist is "only" 320000 items long, which leaves only 16327 after the offset.
        // This means the coefficient will be "1" for _every single term_ and the element value will
        // simply be the sum of the last 16327 elements in the inputList.
        // This reveals some more interesting facts:
        //      1. If the first element past the offset is the sum of the last 16327 elements,
        //          then the second element is just the sum of the last 16326 elements!
        //          i.e. we can get the second and so on elements by substracting _one_ term, not _adding_ a ton again
        //      2.  Because the inputList is a repeating pattern, we should be able to sum it ONCE and then
        //          figure out how many repeats of that pattern each element uses.
        //  RIGHT???

        int elementValue = 0;

        // The sum of all elements from offset to the end of the extended inputList is....
        //      sum from offset to the end of inputList

        if (elementIndex == 0) {
            // Calculate the first element's sum, then just subtract little by little to get the rest
            subList = inputList.subList((offset + elementIndex) % inputList.size(), inputList.size());
            int subListSum = 0;
            for (Integer subListElement : subList) {
                subListSum += subListElement;
            }
            //      plus the sum of the inputList itself, repeated enough times to get to the end of the extended list
            int extensionSum = inputListSum * (10000 * inputList.size() - offsetRaw) / inputList.size();
            elementValue = subListSum + extensionSum;
        } else {
            int toSubtract = inputList.get((offset + elementIndex) % inputList.size());
            elementValue = prevElementValue - toSubtract;
        }
        // Take just the ones digit (make it positive)
        elementValue = Math.abs(elementValue % 10);

        prevElementValue = elementValue;
        /** DEBUG **/
//        if (DISPLAY) {
//            debug = debug.substring(0, debug.length() - 2) + "= " + elementValue;
//            System.out.println(debug);
//        }
        return elementValue;
    }


    public int calculateElementOld(ArrayList<Integer> inputList, Integer elementIndex) {
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
        ArrayList<Integer> extendedInputList = new ArrayList<Integer>(inputList.size() * factor);
        for (int i = 0; i < factor; i++) {
            // Add (another) repeat of the original list onto the extended one.
            extendedInputList.addAll(inputList);
        }
        // When done, get the relevant subset of the extended list (from the 'offset' to the end)
        offset = Integer.parseInt(inputListToString(inputList.subList(0, 7)));
        int end = extendedInputList.size() - 1;
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

            // (For Part B) Calculate the first element (starting at 'offset')
            // Figure out what offset is in terms of the extended inputList
            offsetRaw = Integer.parseInt(inputListToString(inputList.subList(0, 7)));
            offset = offsetRaw % inputList.size();

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
