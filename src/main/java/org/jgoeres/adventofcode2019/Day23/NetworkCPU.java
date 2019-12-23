package org.jgoeres.adventofcode2019.Day23;

import org.jgoeres.adventofcode2019.common.intcode.CPU;
import org.jgoeres.adventofcode2019.common.intcode.Instruction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NetworkCPU extends CPU {
    private Queue<NetworkPacket> networkIO = new LinkedList<>();
    private NetworkPacket nextPacket = null;

    /**
     * Extends IntCode CPU by wrapping I/O in network addressing
     **/
    public NetworkCPU(HashMap<Long, Long> programCode) {
        super(programCode);
    }

    public void runToNextInput() {
        while (!isWaitingForInput() &&
                !isHalted()) {
            executeNext();
        }
    }

    @Override
    public boolean executeNext() {
        // Override the generic executeNext so it doesn't block while waiting for input
        nextInstruction = decodeInstruction();
        boolean keepGoing = opCodeFunctorMap().get(nextInstruction.getOpCode()).execute(nextInstruction);
        return keepGoing;
    }

    @Override
    protected boolean input(Instruction instruction) {
        // INPUT
        // To receive a packet from another computer, the NIC will use an input instruction.
        // If the incoming packet queue is empty, provide -1.
        // Otherwise, provide the X value of the next packet;
        // the computer will then use a second input instruction to receive the Y value
        // for the same packet. Once both values of the packet are read in this way,
        // the packet is removed from the queue.
        Long val1 = getOutputArgValue(instruction, 0);
        Long inputValue;
        // If there IS an input waiting, process it.
        if ((inputValue = inputQueue.poll()) == null) {
            // If the incoming packet queue is empty, provide -1.
            inputValue = -1L;
            waitingForInput = true;
        } else {
            waitingForInput = false;
        }
        // Whatever the input value is, stick it in the specified location
        programCode.put(val1, inputValue);
        return true;
    }

    @Override
    protected boolean output(Instruction instruction) {
        // OUTPUT
        // To send a packet to another computer, the NIC will use three output
        // instructions that provide the destination address of the packet
        // followed by its X and Y values. For example, three output instructions
        // that provide the values 10, 20, 30 would send a packet with X=20 and Y=30
        // to the computer with address 10.

        Long val1 = getArgValue(instruction, 0);
        lastOutput = val1;
//        System.out.println(val1);
        // Build (or keep building) the output network packet
        if (nextPacket == null) {
            // First comes the address
            nextPacket = new NetworkPacket(lastOutput.intValue());
            return true;
        } else if (nextPacket.getX() == null) {
            // Next comes X
            nextPacket.setX(lastOutput);
        } else if (nextPacket.getY() == null) {
            // Finally comes Y
            nextPacket.setY(lastOutput);
        }
        // Check if the packet is complete, and if so, send it
        if (nextPacket != null && nextPacket.isComplete()) {
            networkIO.add(nextPacket);
            nextPacket = null;  // clear the packet once we send it.
        }
        return true;
    }

    public void setNetworkIO(Queue<NetworkPacket> networkIO) {
        this.networkIO = networkIO;
    }

    public Queue<Long> getInputQueue() {
        // Return the queue reference for use by other CPUs
        return inputQueue;
    }
}

