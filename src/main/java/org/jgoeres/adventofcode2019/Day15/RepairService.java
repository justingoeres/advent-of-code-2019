package org.jgoeres.adventofcode2019.Day15;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

public class RepairService extends IntCodeProcessorService {
    private final String DAY = "15";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    public RepairService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = loadInputs();
    }

    public RepairService(String pathToFile) {
        inputFile = pathToFile;
        cpu = loadInputs();
    }
}
