package org.jgoeres.adventofcode2019.Day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class IntCodeProcessorService {

    private final String DEFAULT_INPUTS_PATH = "data/day02/input.txt";

    private String inputFile = DEFAULT_INPUTS_PATH;
    private CPU cpu;

    public IntCodeProcessorService() {
        // Load the inputs from the default file
        loadInputs();
    }

    public IntCodeProcessorService(String pathToFile) {
        inputFile = pathToFile;
        loadInputs();
    }

    public void runToCompletion() {
        while (processCurrentOpCode()) ;
    }

    public void setValueAtPosition(int position, int value) {
        cpu.setValueAtPosition(position, value);
    }

    public boolean processCurrentOpCode() {
        boolean keepGoing = cpu.executeNext();
        return keepGoing;
    }

    public int getValueAtPosition(int position) {
        return cpu.getValueAtPosition(position);
    }

    public void reset() {
        cpu.reset();
    }

    private void loadInputs() {
        // To load the program, simply read all the ints into an ArrayList.
        // We will interpret opcodes/arguments/pc as we execute it later
        ArrayList<Integer> programCode = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Add all the codes from this line to the programCode list
                for (String element : data) {
                    programCode.add(Integer.parseInt(element));
                }
            }
            // Initialize the CPU with the code we just loaded.
            cpu = new CPU(programCode);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
