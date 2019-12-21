package org.jgoeres.adventofcode2019.Day21;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public class SpringdroidService extends IntCodeProcessorService {
    private final String DAY = "21";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final Character BEAM = '#';
    private final Character EMPTY = '.';

    public SpringdroidService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = loadInputs();
        reset();
    }

    public SpringdroidService(String pathToFile) {
        inputFile = pathToFile;
        cpu = loadInputs();
        reset();
    }

    public void simpleWalkProgram() {
        runSpringScriptProgram("WALK");
    }

    public void runSpringScriptProgram(String program) {
        // The Intcode program expects ASCII inputs and outputs.
        // It will begin by displaying a prompt; then, input the desired
        // instructions one per line. End each line with a newline (ASCII code 10).
        // When you have finished entering your program, provide the command WALK
        // followed by a newline to instruct the springdroid to begin surveying the hull.

        // If the springdroid falls into space, an ASCII rendering of the last moments
        // of its life will be produced. In these, @ is the springdroid, # is hull, and . is empty space.

        cpu.reset(); // reset before running the springscript

        String buffer = "";
        enterInputString(program);
        while (!isHalted()) {
            // Execute, accumulate output, and print it by line
            executeToNextOutput();
            char c = (char) getProgramOutput().longValue();
            if (c != '\n') {
                // If this char is NOT a linefeed
                // Add it to the output buffer
                buffer += c;
            } else {
                // If this char IS a linefeed
                // Print the buffer and reset it
                System.out.println(buffer);
                buffer = "";
            }
        }
    }

    public void enterInputString(String input) {
        final Character NEWLINE = '\n';
        if (input.charAt(input.length() - 1) != NEWLINE) {
            input += NEWLINE;   // Add a newline at the end if needed.
        }
        for (Character c : input.toCharArray()) {
            executeToNextInput();   // Make sure we're actually waiting for input
            setCpuInputValue((long) c);
            executeNext();  // Execute the input instruction after entering
        }
    }
}
