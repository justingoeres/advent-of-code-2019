package org.jgoeres.adventofcode2019.Day12;

import org.jgoeres.adventofcode2019.common.XYZPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NBodyService {
    private final String XX = "12";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private ArrayList<Moon> moons = new ArrayList<>();
    private Set<ArrayList<Moon>> moonPairs = new HashSet<>();

    public NBodyService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public NBodyService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public void simulate(int numTicks) {
        /***** DEBUG *****/
//        System.out.println("After " + 0 + " steps:");
//        for (Moon moon : moons) {
//            System.out.println(moon);
//        }

        // Simulate the specified number of timer ticks
        for (int i = 0; i < numTicks; i++) {
//            Within each time step, first update the velocity of every moon by applying gravity.
//            Then, once all moons' velocities have been updated, update the position of every moon
//            by applying velocity.
//            Time progresses by one step once all of the positions are updated.

            /***** APPLY GRAVITY *****/
            for (ArrayList<Moon> moonPair : moonPairs) {
                // To apply gravity, consider every pair of moons.
                // On each axis (x, y, and z), the velocity of each moon changes
                // by exactly +1 or -1 to pull the moons together.
                // However, if the positions on a given axis are the same,
                // the velocity on that axis does not change for that pair of moons.
                applyGravity(moonPair.get(0), moonPair.get(1));
            }

            /***** CALCULATE NEW POSITIONS *****/
            // simply add the velocity of each moon to its own position.
            // For example, if Europa has a position of x=1, y=2, z=3 and a velocity of x=-2, y=0,z=3,
            // then its new position would be x=-1, y=2, z=6.
            // This process does not modify the velocity of any moon.
            for (Moon moon : moons) {
                moon.doTimerTick();
            }
            /***** DEBUG *****/
//            System.out.println("After " + (i + 1) + " steps:");
//            for (Moon moon : moons) {
//                System.out.println(moon);
//            }
        }
    }

    private void applyGravity(Moon moon1, Moon moon2) {
        // On each axis (x, y, and z), the velocity of each moon changes
        // by exactly +1 or -1 to pull the moons together.
        // However, if the positions on a given axis are the same,
        // the velocity on that axis does not change for that pair of moons.

        /*** X ***/
        int moon1x = moon1.position.getX();
        int moon2x = moon2.position.getX();

        int differenceX = moon2x - moon1x;
        int signX = Math.round(Math.signum(differenceX));

        // Adjust the velocities in opposite directions
        moon1.velocity.setX(moon1.velocity.getX() + signX);
        moon2.velocity.setX(moon2.velocity.getX() - signX);

        /*** Y ***/
        int moon1y = moon1.position.getY();
        int moon2y = moon2.position.getY();

        int differenceY = moon2y - moon1y;
        int signY = Math.round(Math.signum(differenceY));

        // Adjust the velocities in opposite directions
        moon1.velocity.setY(moon1.velocity.getY() + signY);
        moon2.velocity.setY(moon2.velocity.getY() - signY);

        /*** Z ***/
        int moon1z = moon1.position.getZ();
        int moon2z = moon2.position.getZ();

        int differenceZ = moon2z - moon1z;
        int signZ = Math.round(Math.signum(differenceZ));

        // Adjust the velocities in opposite directions
        moon1.velocity.setZ(moon1.velocity.getZ() + signZ);
        moon2.velocity.setZ(moon2.velocity.getZ() - signZ);
    }

    public int calculateTotalSystemEnergy() {
        // The total energy for a single moon is its potential energy multiplied by its kinetic energy.
        // A moon's potential energy is the sum of the absolute values of its x, y, and z position coordinates.
        // A moon's kinetic energy is the sum of the absolute values of its velocity coordinates.
        int systemEnergy = 0;
        for (Moon moon : moons) {
            systemEnergy += moon.calculateTotalEnergy();
        }
        return systemEnergy;
    }

    private Set<ArrayList<Moon>> generateMoonPairs() {
        HashSet<ArrayList<Moon>> moonPairs = new HashSet<>();
        // Iterate through all pairs of moons, but don't do the same pair twice
        for (int i = 0; i < moons.size(); i++) {
            for (int j = i + 1; j < moons.size(); j++) {
                ArrayList<Moon> pair = new ArrayList<>(2);
                pair.add(moons.get(i));
                pair.add(moons.get(j));
                // Add this pair to the list of pairs
                moonPairs.add(pair);
            }
        }
        return moonPairs;
    }

    private void loadInputs(String pathToFile) {
        // Each line is the coordinates of one planet
        // e.g. <x=-12, y=2, z=-7>
        moons.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            Pattern p = Pattern.compile("[^0-9-]+(-?\\d+)");    // Match next signed integer
            while ((line = br.readLine()) != null) {
                ArrayList<Integer> xyz = new ArrayList<>();
                // process the line.
                // e.g. <x=-12, y=2, z=-7>
                Matcher m = p.matcher(line);
                while (m.find()) {
                    // Scan the (three) coordinates)
                    xyz.add(Integer.parseInt(m.group(1)));  // coordinate is in group 1 of the match
                }
                // Make a moon out of them
                int x = xyz.get(0);
                int y = xyz.get(1);
                int z = xyz.get(2);
                // Add it to the list
                moons.add(new Moon(new XYZPoint(x, y, z)));
            }
            // Generate all the pairs of moons we'll need later.
            moonPairs = generateMoonPairs();
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
