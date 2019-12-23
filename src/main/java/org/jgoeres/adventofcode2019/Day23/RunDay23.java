package org.jgoeres.adventofcode2019.Day23;

public abstract class RunDay23 {
    static String DAY = "23";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static IntCodeNetworkService intCodeNetworkService = new IntCodeNetworkService();

    public static int problem23A() {
        // Day 23A
        System.out.println("=== DAY " + DAY + "A ===");

        // Robot
        // The robot's jump distance is THREE tiles

        // Registers
        // A -> is ground One tile away? (immediately in front of robot)
        // B -> is ground Two tiles away?
        // C -> is ground Three tiles away?
        // D -> is grount Four tiles away?
        // T -> TEMP register (output)
        // J -> JUMP IF TRUE (output)

        // Instructions
        //  AND X Y
        //  OR X Y
        //  NOT X Y

        // Execution
        // Robot executes the script at every step
        // and jumps if J is TRUE
        String program = "NOT A T\n" +  // T = is A empty?
                "NOT B J\n" +           // J = is B empty?
                "OR J T\n" +            // T = is A or B empty?
                "NOT C J\n" +           // J = is C empty?
                "OR J T\n" +            // T = is C empty or (A or B empty)?
                "AND D T\n" +           // T = is D ground AND (C or A or B empty)?
                "AND T J\n" +           // JUMP = previous result
                "NOT A T\n" +           // Again, is A empty?
                "OR T J\n" +            // Previous result, or jump if A is empty since we die if we don't
                "WALK\n";               // GO

        intCodeNetworkService.runSpringScriptProgram(program);
        int result = intCodeNetworkService.getProgramOutput().intValue();

        System.out.println("Day " + DAY + "A: Hull damage reported = " + result);

//        Day 23A: Hull damage reported = 19353692
//        Time elapsed:	389 ms

        return result;
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
        intCodeNetworkService.runSpringScriptProgram(program);
        int result = intCodeNetworkService.getProgramOutput().intValue();

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 23A: Hull damage reported = 19353692
//        Time elapsed:	364 ms

        return result;
    }

}
