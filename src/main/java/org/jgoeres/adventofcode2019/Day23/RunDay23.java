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
//        intCodeNetworkService.runSpringScriptProgram(program);
//        int result = intCodeNetworkService.getProgramOutput().intValue();

        System.out.println("Day " + DAY + "A: Value of packet sent to address 255 = " + finalPacketValue);

//        Day 23A: Value of packet sent to address 255 = 17541
//        Time elapsed:	503 ms
        return finalPacketValue;
    }

    public static int problem23B() {
        // Day 23B
        System.out.println("=== DAY " + DAY + "B ===");

        intCodeNetworkService.setProblemPart(PART_B);
        int result = intCodeNetworkService.getProgramOutput().intValue();

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 23A: Hull damage reported = 19353692
//        Time elapsed:	364 ms

        return result;
    }

}
