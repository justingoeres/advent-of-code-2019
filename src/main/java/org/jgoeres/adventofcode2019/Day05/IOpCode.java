package org.jgoeres.adventofcode2019.Day05;

import java.util.ArrayList;

public interface IOpCode {
    boolean execute(int pc, ArrayList<Integer> programCode);
}
