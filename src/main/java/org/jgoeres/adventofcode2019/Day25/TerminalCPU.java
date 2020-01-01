package org.jgoeres.adventofcode2019.Day25;

import org.jgoeres.adventofcode2019.common.intcode.CPU;

import java.util.HashMap;

public class TerminalCPU extends CPU {
    public TerminalCPU(HashMap<Long, Long> programCode) {
        super(programCode);
    }

    public TerminalCPU(String inputFile) {
        super(inputFile);
    }
}
