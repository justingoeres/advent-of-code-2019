package org.jgoeres.adventofcode2019.Day23;

import static org.jgoeres.adventofcode2019.Day23.IntCodeNetworkService.ProblemPart.PART_B;

public abstract class RunDay23 {
    static String DAY = "23";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static IntCodeNetworkService intCodeNetworkService = new IntCodeNetworkService();

    public static Long problem23A() {
        // Day 23A
        System.out.println("=== DAY " + DAY + "A ===");

        Long finalPacketValue = intCodeNetworkService.runUntilPacketCatch();

        System.out.println("Day " + DAY + "A: Value of packet sent to address 255 = " + finalPacketValue);

//        Day 23A: Value of packet sent to address 255 = 17541
//        Time elapsed:	503 ms
        return finalPacketValue;
    }

    public static Long problem23B() {
        IntCodeNetworkService intCodeNetworkServicePartB = new IntCodeNetworkService();

        // Day 23B
        System.out.println("=== DAY " + DAY + "B ===");

        intCodeNetworkServicePartB.setProblemPart(PART_B);
        Long finalPacketValue = intCodeNetworkServicePartB.runUntilPacketCatch();

        System.out.println("Day " + DAY + "B: Value of first NAT packet sent twice consecutively = " + finalPacketValue);

//        Day 23B: Value of first NAT packet sent twice consecutively = 12415
//        Time elapsed:	3780 ms

        return finalPacketValue;
    }

}
