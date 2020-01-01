package org.jgoeres.adventofcode2019.Day25;

public abstract class RunDay25 {
    static String DAY = "25";

    static String pathToInputs = "data/day" + DAY + "/input.txt";

    static IntCodeTerminalService intCodeTerminalService = new IntCodeTerminalService();

    public static int problem25A() {
        // Day 25A
        System.out.println("=== DAY " + DAY + "A ===");

        String getAllItems = "north\n" +
                "north\n" +
                "north\n" +
                "take mutex\n" +
                "south\n" +
                "south\n" +
                "east\n" +
                "north\n" +
                "take loom\n" +
                "south\n" +
                "west\n" +
                "south\n" +
                "east\n" +
                "east\n" +
                "take ornament\n" +
                "west\n" +
                "take semiconductor\n" +
                "west\n" +
                "west\n" +
                "west\n" +
                "south\n" +
                "east\n" +
                "north\n" +
                "take wreath\n" +
                "south\n" +
                "take asterisk\n" +
                "west\n" +
                "north\n" +
                "take sand\n" +
                "north\n" +
                "take dark matter\n" +
                "east\n";
//n

        intCodeTerminalService.executeToNextInput();
        int result = 0;

//      DAY 25 COMPLETION TEXT:
//        A loud, robotic voice says "Analysis complete! You may proceed." and you enter the cockpit.
//        Santa notices your small droid, looks puzzled for a moment, realizes what has happened, and radios your ship directly.
//        "Oh, hello! You should be able to get in by typing 8912902 on the keypad at the main airlock."

        return result;
    }

    // There is no Day 25 Part B

}
