package org.jgoeres.adventofcode2019.common.intcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntCodeProcessorService {

    private String inputFile;
    private CPU cpu;

    public IntCodeProcessorService(String pathToFile) {
        inputFile = pathToFile;
        cpu = loadInputs();
    }

    public void runToCompletion() {
        while (cpu.executeNext()) ;
    }

    public boolean executeNext() {
        return cpu.executeNext();
    }


    public void setValueAtPosition(int position, int value) {
        cpu.setValueAtPosition(position, value);
    }

    public void setCpuInputValue(int cpuInputValue) {
        cpu.addToInputQueue(cpuInputValue);
    }

    public int getValueAtPosition(int position) {
        return cpu.getValueAtPosition(position);
    }

    public int getProgramOutput() {
        return cpu.getLastOutput();
    }

    public boolean isWaitingForInput() {
        return cpu.isWaitingForInput();
    }

    public boolean isOutputReady() {
        return cpu.isOutputReady();
    }

    public boolean isHalted() {
        return cpu.isHalted();
    }

    protected String getInputFile() {
        return inputFile;
    }

    public void reset() {
        cpu.reset();
    }

    protected CPU loadInputs() {
        // To load the program, simply read all the ints into an ArrayList.
        // We will interpret opcodes/arguments/pc as we execute it later
        HashMap<Integer, Integer> programCode = new HashMap<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            while ((line = br.readLine()) != null) {
                int addr = 0;
                String[] data = line.split(",");
                // Add all the codes from this line to the programCode list
                for (String element : data) {
                    programCode.put(addr, Integer.parseInt(element));
                    addr++;
                }
            }
            // Initialize the CPU with the code we just loaded.
            return (new CPU(programCode));
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return null;
    }
}
