package org.jgoeres.adventofcode2019.Day19;

import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;
import org.jgoeres.adventofcode2019.common.robot.RobotURDL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class TractorBeamService extends IntCodeProcessorService {
    private final String DAY = "19";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final Integer AREA_SIZE = 50;
    private final Character BEAM = '#';
    private final Character EMPTY = '.';

    private ArrayList<Integer> inputList = new ArrayList<>();

    private HashSet<XYPoint> tractorBeamArea = new HashSet<>();

    public TractorBeamService() {
        inputFile = DEFAULT_INPUTS_PATH;
        cpu = loadInputs();
        reset();
    }

    public TractorBeamService(String pathToFile) {
        inputFile = pathToFile;
        cpu = loadInputs();
        reset();
    }

    public Integer getTractorBeamSize() {
        return tractorBeamArea.size();
    }

    public void createTractorBeamMap() {
        reset();
        XYPoint drone = new XYPoint();
        for (int x = 0; x < AREA_SIZE; x++) {
            for (int y = 0; y < AREA_SIZE; y++) {
                // Reset the cpu on every run??
                cpu.reset();
                // Maneuver the drone through the whole 50x50 range of the beam
                // At each point, tell the CPU where we are and then run until
                // we get the output telling us whether the beam is on us.
                drone.set(x, y);
                // Execute to the place where we input the X coordinate
                executeToNextInput();
                setCpuInputValue((long) x);
                // Process the value
                executeNext();
                // Execute to the place where we input the Y coordinate
                executeToNextInput();
                setCpuInputValue((long) y);
                // Process the value
                executeNext();
                // Now run to the output (0: no beam, 1: in beam)
                executeToNextOutput();
                Long inBeam = getProgramOutput();
                if (inBeam == 1) {
                    // Add this point to the beam area
//                    System.out.println(drone + ":\t" + inBeam);
                    tractorBeamArea.add(new XYPoint(x, y));
                }
            }
        }
    }

    @Override
    public void reset() {
        // Reset the CPU
        super.reset();
        // Reset the map
        tractorBeamArea.clear();
    }

    public void printAreaMap() {
        int xMin = 0;
        int xMax = AREA_SIZE;
        int yMin = 0;
        int yMax = AREA_SIZE;

        // y is lines, x is characters
        for (int y = yMin; y < yMax; y++) {    // It's upside-down
            String line = "";
            // for each line
            for (int x = xMin; x < xMax; x++) {
                XYPoint p = new XYPoint(x, y);
                Character locationChar;
                // for each position on the line
                if (tractorBeamArea.contains(p)) {
                    locationChar = BEAM;
                } else {
                    locationChar = EMPTY;
                }
                // Add the character to this line
                line += locationChar;
            }
            // when we're done with the line, print it
            System.out.println(line);
        }
    }

    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                nextInt = Integer.parseInt(line);

                inputList.add(nextInt);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
