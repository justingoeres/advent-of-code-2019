package org.jgoeres.adventofcode2019.common.intcode;


public interface IOpCode {
    boolean execute(Instruction instruction);
}
