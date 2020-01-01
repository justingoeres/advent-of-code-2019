package org.jgoeres.adventofcode2019.Day25;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public class IntCodeTerminalService extends IntCodeProcessorService {
    private final String DAY = "25";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    public IntCodeTerminalService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = new TerminalCPU(DEFAULT_INPUTS_PATH);
    }

    public IntCodeTerminalService(String pathToFile) {
        super(pathToFile);
    }
}
