package org.jgoeres.adventofcode2019.Day23;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static org.jgoeres.adventofcode2019.Day23.IntCodeNetworkService.ProblemPart.PART_A;

public class IntCodeNetworkService extends IntCodeProcessorService {
    private final String DAY = "23";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private HashMap<Integer, NetworkCPU> networkCPUHashMap = new HashMap<>();
    private HashMap<Integer, Queue<Long>> networkIOMap = new HashMap<>();
    private Queue<NetworkPacket> networkIOQueue = new LinkedList<>();
    private Long finalPacketValue;
    private Long NATvalue;

    enum ProblemPart {
        PART_A,
        PART_B;
    }

    private ProblemPart problemPart = PART_A;  // Part A by default

    public IntCodeNetworkService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = loadInputs();
        makeCPUs(50);
//        reset();
    }

    public IntCodeNetworkService(String pathToFile) {
        inputFile = pathToFile;
        cpu = loadInputs();
        reset();
        makeCPUs(50);
    }

    public Long runUntilPacketCatch() {
        // Run until we catch a packet to addr 255
        while (true) {
            // First, process any pending network traffic
            if (!processNetworkTraffic()) break;    // break when the network tells us to
            // Then tick the timer for all CPUs.
            for (NetworkCPU cpu : networkCPUHashMap.values()) {
                cpu.executeNext();
            }
        }
        return finalPacketValue;
    }

    public void setProblemPart(ProblemPart problemPart) {
        this.problemPart = problemPart;
    }

    private boolean processNetworkTraffic() {
        // Process all the pending network packets and route them to the
        // correct CPU input queues
        boolean keepGoing = true;
        NetworkPacket nextPacket;
        while ((nextPacket = networkIOQueue.poll()) != null) {
            // Route the next packet to the correct CPU
            // Unwrap the packet
            Integer addr = nextPacket.getAddress();
            Long X = nextPacket.getX();
            Long Y = nextPacket.getY();
            // Send the data
            // by putting it in the input queue of the target computer
            if (addr != 255) {
                // Process the traffic normally
                networkIOMap.get(addr).add(X);
                networkIOMap.get(addr).add(Y);
                // and keep going
            } else {
                // Stop on packet to address 255!
//                finalPacketValue = Y;
//                keepGoing = false;
                keepGoing = processNAT(Y);
            }
        }
        return keepGoing;
    }

    private boolean processNAT(Long packetValue) {
        switch (problemPart) {
            case PART_A:
                // No NAT in Part A
                finalPacketValue = packetValue;
                // and then tell the service to stop!
                return false;
            case PART_B:
                return true;
        }
        return true;
    }

    private void makeCPUs(int cpuCount) {
        // The computers have network addresses 0 through 49;
        // when each computer boots up, it will request its network address via a
        // single input instruction. Be sure to give each computer a unique network address.
        for (int i = 0; i < cpuCount; i++) {
            // Make 50 CPUs, each with a copy of the "original" program code.
            NetworkCPU networkCPU = new NetworkCPU(cpu.getProgramCodeOriginal());
            networkCPUHashMap.put(i, networkCPU);
            // Put its input queue into the IO map
            networkIOMap.put(i, networkCPU.getInputQueue());
            // Give the new CPU a network socket to output to
            networkCPU.setNetworkIO(networkIOQueue);
            // Supply the new CPU with its network address
//            networkCPU.runToNextInput();
            networkCPU.addToInputQueue((long) i);
//            networkCPU.executeNext();
        }
    }

    @Override
    public void reset() {
        super.reset();
        // Reset all the CPUs
        for (NetworkCPU networkCPU : networkCPUHashMap.values()) {
            networkCPU.reset();
        }
        NATvalue = null;
    }
}
