package org.jgoeres.adventofcode2019.Day12;

import org.jgoeres.adventofcode2019.common.XYZPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NBodyService {
    private final String XX = "12";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private ArrayList<Moon> moons = new ArrayList<>();

    public NBodyService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public NBodyService(String pathToFile) {
        loadInputs(pathToFile);
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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
