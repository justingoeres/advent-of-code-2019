package org.jgoeres.adventofcode2019.Day23;

import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Long.MIN_VALUE;
import static org.jgoeres.adventofcode2019.Day23.IntCodeNetworkService.ProblemPart.PART_A;
import static org.jgoeres.adventofcode2019.Day23.IntCodeNetworkService.ProblemPart.PART_B;

public class IntCodeNetworkService extends IntCodeProcessorService {
    private final String DAY = "23";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private HashMap<Integer, NetworkCPU> networkCPUHashMap = new HashMap<>();
    private HashMap<Integer, Queue<Long>> networkIOMap = new HashMap<>();
    private Queue<NetworkPacket> networkIOQueue = new LinkedList<>();
    private Long finalPacketY;
    private NetworkPacket currentNATPacket = null;
    private Long lastNATY = MIN_VALUE; // initialize to garbage

    enum ProblemPart {
        PART_A,
        PART_B;
    }

    private ProblemPart problemPart = PART_A;  // Part A by default

    public IntCodeNetworkService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = loadInputs();
        makeCPUs(50);
    }

    public IntCodeNetworkService(String pathToFile) {
        inputFile = pathToFile;
        cpu = loadInputs();
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
        return finalPacketY;
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
                keepGoing = processNATPacket(nextPacket);
            }
        }
        if (problemPart == PART_B && currentNATPacket != null) {
            keepGoing = processNATMonitoring();
        }
        return keepGoing;
    }

    private boolean processNATPacket(NetworkPacket packet) {
        switch (problemPart) {
            case PART_A:
                // No NAT in Part A
                finalPacketY = packet.getY();
                // Tell the service to stop on the first NAT packet received
                return false;
            case PART_B:
                // The NAT remembers only the last packet it receives;
                // that is, the data in each packet it receives overwrites
                // the NAT's packet memory with the new packet's X and Y values.
                currentNATPacket = packet;
                return true;
        }
        return true;
    }

    private boolean processNATMonitoring() {
        // The NAT also monitors all computers on the network.
        // If all computers have empty incoming packet queues and are continuously
        // trying to receive packets without sending packets, the network is considered idle.
        boolean keepGoing = true;   // assume we keep going

        // Are all computers' input queues empty?
        boolean allEmpty = true;   // Assume true
        for (Queue<Long> cpuQueue : networkIOMap.values()) {
            if (!cpuQueue.isEmpty()) {
                // If at least one queue is NOT empty
                allEmpty = false;
                // set the flag and stop looking
                break;
            }
        }
        // Are all computers also waiting for input?
        boolean allWaiting = true;  // assume true
        for (NetworkCPU networkCPU : networkCPUHashMap.values()) {
            if (!networkCPU.isWaitingForInput()) {
                // If at least one computer is NOT waiting for input
                allWaiting = false;
                // set the flag and stop looking
            }
        }
        // Is the network idle?
        if (allEmpty && allWaiting) {
            // Once the network is idle, the NAT sends only the last *packet*
            // it received to address 0; this will cause the computers on the
            // network to resume activity.

            // Forward the most recent packet we received, to address 0.
            currentNATPacket.setAddress(0);
            networkIOQueue.add(currentNATPacket);
            // Monitor packets released to the computer at address 0 by the NAT.
            // What is the first Y value delivered by the NAT to the computer at address 0
            // twice in a row?
            if (currentNATPacket.getY().equals(lastNATY)) {
                // We've sent the same value twice in a row, so stop!
                finalPacketY = currentNATPacket.getY();
                keepGoing = false; // tell the service to stop
            } else {
                // cache this NAT y-value and keep going
                lastNATY = currentNATPacket.getY();
            }
        }
        return keepGoing;
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
            networkCPU.addToInputQueue((long) i);
        }
    }

    @Override
    public void reset() {
        super.reset();
        // Reset all the CPUs
        for (NetworkCPU networkCPU : networkCPUHashMap.values()) {
            networkCPU.reset();
        }
    }
}
