package org.jgoeres.adventofcode2019.Day25;

import org.jgoeres.adventofcode2019.common.intcode.CPU;
import org.jgoeres.adventofcode2019.common.intcode.Instruction;

import java.util.HashMap;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.*;
import static org.jgoeres.adventofcode2019.Day25.TerminalCPU.Operation.TAKE;

public class TerminalCPU extends CPU {
    private final boolean DISPLAY = false;
    public String outputBuffer = EMPTY;

    private int itemsMask;


    public TerminalCPU(HashMap<Long, Long> programCode) {
        super(programCode);
    }

    public TerminalCPU(String inputFile) {
        super(inputFile);
    }

    @Override
    protected boolean output(Instruction instruction) {
        // Run the output instruction same as in the generic CPU but dump the result into an output buffer
        boolean keepGoing = super.output(instruction);

        // Accumulate the output buffer, and print it on a linefeed
        char c = (char) getLastOutput().longValue();
        if (c != '\n') {
            // If this char is NOT a linefeed
            // Add it to the output buffer
            outputBuffer += c;
        } else {
            // If this char IS a linefeed
            // Print the buffer and reset it
            System.out.println(outputBuffer);
            outputBuffer = EMPTY;
        }
        return keepGoing;
    }

    @Override
    protected boolean input(Instruction instruction) {
        Long val1 = getOutputArgValue(instruction, 0);
        Long inputValue;
        if ((inputValue = inputQueue.poll()) == null) {
            // If there NOT is an input waiting for us
            // Pause to get a line of input from the console
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            // Command shortcuts
            switch (command) {
                case "n":
                    command = "north";
                    break;
                case "s":
                    command = "south";
                    break;
                case "e":
                    command = "east";
                    break;
                case "w":
                    command = "west";
                    break;
                case "i":
                    command = "inv";
                    break;
                case "do":
                    command = "drop ornament";
                    break;
                case "dl":
                    command = "drop loom";
                    break;
                case "dm":
                    command = "drop mutex";
                    break;
                case "ds":
                    command = "drop semiconductor";
                    break;
                case "dw":
                    command = "drop wreath";
                    break;
                case "da":
                    command = "drop asterisk";
                    break;
                case "dsa":
                    command = "drop sand";
                    break;
                case "ddm":
                    command = "drop dark matter";
                    break;
                case "to":
                    command = "take ornament";
                    break;
                case "tl":
                    command = "take loom";
                    break;
                case "tm":
                    command = "take mutex";
                    break;
                case "ts":
                    command = "take semiconductor";
                    break;
                case "tw":
                    command = "take wreath";
                    break;
                case "ta":
                    command = "take asterisk";
                    break;
                case "tsa":
                    command = "take sand";
                    break;
                case "tdm":
                    command = "take dark matter";
                    break;
                case "x":
                    // Run the next masked command
                    itemsMask++;
                    command = getNextAttempt();
                    break;
            }
            if (DISPLAY) {
                System.out.println("Command entered:\t" + command);
            }
            // Add the command to the input queue
            addToInputQueue(command);
            // Then get the first thing we put on the queue, and proceed to process it.
            inputValue = inputQueue.poll();
        }
        // If there IS an input waiting, process it.
        programCode.put(val1, inputValue);
        return true;
    }

    enum Operation {
        TAKE,
        DROP
    }

    private final int ALL_ITEMS_MASK = 255;

    private String commandDropAll() {
        return createItemHandlingCommand(Operation.DROP, ALL_ITEMS_MASK);
    }

    public String getNextAttempt() {
        // Drop everything, then get the items for the next itemsMask and go East to try it on the door
        String command = EMPTY;
        command += commandDropAll();    // drop everything
        command += createItemHandlingCommand(TAKE, itemsMask);
        System.out.println("*** ITEMS MASK:\t" + itemsMask);
        command += "east\n";
        return command;
    }

    private String createItemHandlingCommand(Operation op, int itemsMask) {
        // itemsMask is a bitmask of all the items we can carry:
        // bit
        // 0    dark matter
        // 1    sand
        // 2    asterisk
        // 3    wreath
        // 4    semiconductor
        // 5    mutex
        // 6    loom
        // 7    ornament
        int darkMatter = 1;
        int sand = 2;
        int asterisk = 4;
        int wreath = 8;
        int semiconductor = 16;
        int mutex = 32;
        int loom = 64;
        int ornament = 128;

        String opString = (op == TAKE) ? "take " : "drop ";
        final String NEWLINE = "\n";
        String command = EMPTY;
        if ((itemsMask & darkMatter) > 0) command += opString + "dark matter" + NEWLINE;
        if ((itemsMask & sand) > 0) command += opString + "sand" + NEWLINE;
        if ((itemsMask & asterisk) > 0) command += opString + "asterisk" + NEWLINE;
        if ((itemsMask & wreath) > 0) command += opString + "wreath" + NEWLINE;
        if ((itemsMask & semiconductor) > 0) command += opString + "semiconductor" + NEWLINE;
        if ((itemsMask & mutex) > 0) command += opString + "mutex" + NEWLINE;
        if ((itemsMask & loom) > 0) command += opString + "loom" + NEWLINE;
        if ((itemsMask & ornament) > 0) command += opString + "ornament" + NEWLINE;

        return command;
    }
}
