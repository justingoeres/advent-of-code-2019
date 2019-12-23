package org.jgoeres.adventofcode2019.Day23;

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

        String program = "NOT A T\n" +  // T = is A empty?
                "NOT B J\n" +           // J = is B empty?
                "OR J T\n" +            // T = is A or B empty?
                "NOT C J\n" +           // J = is C empty?
                "OR J T\n" +            // T = is (A or B empty) or C empty?
                "AND D T\n" +           // T = is (A or B or C empty) and D ground
                "AND C J\n" +            // J = is (C empty) and C ground?	// Idea is to safely reset J. C can't be empty in line 4 and ground here
                "OR E J\n" +            // J = is E ground?		NOTE: J will already be true here if C is empty!
                "OR H J\n" +            // J = is (E) or H ground?
                "AND T J\n" +           // J = is (A or B or C empty) and D ground) AND (E or H ground)?
                "RUN\n";
//        intCodeNetworkService.runSpringScriptProgram(program);
        int result = intCodeNetworkService.getProgramOutput().intValue();

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 23A: Hull damage reported = 19353692
//        Time elapsed:	364 ms

        return result;
    }

}
