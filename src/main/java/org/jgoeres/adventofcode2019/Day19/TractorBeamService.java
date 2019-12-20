package org.jgoeres.adventofcode2019.Day19;

import org.jgoeres.adventofcode2019.common.XYPoint;
import org.jgoeres.adventofcode2019.common.intcode.IntCodeProcessorService;

import java.util.ArrayList;
import java.util.HashSet;

public class TractorBeamService extends IntCodeProcessorService {
    private final String DAY = "19";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private final Integer AREA_SIZE = 50;
    private final Character BEAM = '#';
    private final Character EMPTY = '.';

    private final int SANTAS_SHIP = 100;

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
//                // Reset the cpu on every run??
//                cpu.reset();
//                // Maneuver the drone through the whole 50x50 range of the beam
//                // At each point, tell the CPU where we are and then run until
//                // we get the output telling us whether the beam is on us.
                drone.set(x, y);
                if (pointInBeam(drone)) {
                    // Add this point to the beam area
//                    System.out.println(drone + ":\t" + inBeam);
                    tractorBeamArea.add(new XYPoint(x, y));
                }
            }
        }
    }


    public boolean pointInBeam(XYPoint p) {
        // Run the intcode program to see if this point is in the beam
        // Reset the cpu on every run??
        cpu.reset();
        // Maneuver the drone through the whole 50x50 range of the beam
        // At each point, tell the CPU where we are and then run until
        // we get the output telling us whether the beam is on us.
        // Execute to the place where we input the X coordinate
        executeToNextInput();
        setCpuInputValue((long) p.getX());
        // Process the value
        executeNext();
        // Execute to the place where we input the Y coordinate
        executeToNextInput();
        setCpuInputValue((long) p.getY());
        // Process the value
        executeNext();
        // Now run to the output (0: no beam, 1: in beam)
        executeToNextOutput();
        Long inBeam = getProgramOutput();
        return (inBeam == 1L);
    }

    public int fitSantasShip() {
        // Find the 100x100 square closest to the emitter that fits entirely within the tractor beam;
        // within that square, find the point closest to the emitter.
        // What value do you get if you take that point's X coordinate,
        // multiply it by 10000, then add the point's Y coordinate?
        XYPoint pSanta = fitSquareInBeam(SANTAS_SHIP);

        int result = pSanta.getX() * 10000 + pSanta.getY();
        return result;
    }

    public XYPoint fitSquareInBeam(int shipSize) {
//        Find the 100x100 square closest to the emitter that fits entirely within the tractor beam;
//        within that square, find the point closest to the emitter.

        // The beam always moves down & to the right. So if have the BOTTOM LEFT beam point x
        // in line y, then the TOP RIGHT point in the beam on line y-1 has to be >= x.
        // We can use that to trace the left edge of the beam quickly.

        // From there, all we need to do is check the point (x+99, y-99)
        // If it's in the beam, the ship will fit!

        int x = Math.max(shipSize, 6);      // Start far enough out to avoid index out of bounds
        int y = Math.max(shipSize, 6);      // and also the break in the beam near 0,0
        XYPoint p = new XYPoint();   // It's going to be way beyond this, so 100, 100 should be safe to start
        XYPoint pTest = new XYPoint();
        while (true) {
            // go until we're done
            p.set(x, y);
            boolean inBeam = pointInBeam(p);
            if (inBeam) {
                // If we're currently in the beam
                // Check the top right corner
                pTest.set(p.getX() + (shipSize - 1), p.getY() - (shipSize - 1));
                boolean pTestInBeam = pointInBeam(pTest);
                if (pTestInBeam) {
                    // We found it!
                    // Return the TOP LEFT corner (not any of the points we checked)
                    p.set(x, pTest.getY());
                    return p;
                }
                // If we're here, we didn't find it yet but we are in the beam.
                // So search down as well as over
                y++;
            }
            // If we're here, we're not in the beam at all
            // So search right
            x++;
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
            String line = y + ":\t";
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
}
