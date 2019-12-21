package org.jgoeres.adventofcode2019.Day21;

public abstract class RunDay21 {
    static String DAY = "21";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static SpringdroidService springdroidService = new SpringdroidService();

    public static int problem21A() {
        // Day 21A
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

        springdroidService.runSpringScriptProgram(program);
        int result = springdroidService.getProgramOutput().intValue();

        System.out.println("Day " + DAY + "A: Hull damage reported = " + result);

//        Day 21A: Hull damage reported = 19353692
//        Time elapsed:	389 ms

        return result;
    }

    public static int problem21B() {
        // Day 21B
        System.out.println("=== DAY " + DAY + "B ===");

        int result = 0;

        System.out.println("Day " + DAY + "B: Answer = " + result);

//        Day 21B: Answer =
//        Time elapsed:	xxx ms

        return result;
    }

}
