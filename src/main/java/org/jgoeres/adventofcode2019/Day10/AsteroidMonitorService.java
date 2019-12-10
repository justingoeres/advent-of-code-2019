package org.jgoeres.adventofcode2019.Day10;

import org.jgoeres.adventofcode2019.Day03.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class AsteroidMonitorService {
    private final String XX = "10";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private final char ASTEROID = '#';

    private HashSet<XYPoint> asteroids = new HashSet<XYPoint>();

    public AsteroidMonitorService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public AsteroidMonitorService(String pathToFile) {
        loadInputs(pathToFile);
    }
    
    private void loadInputs(String pathToFile) {
        asteroids.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                // process the line character by character
                for (int x = 0; x < line.length(); x++) {
                    // If this character is an asteroid
                    if (line.charAt(x) == ASTEROID) {
                        // Add an asteroid at this x,y point to the list
                        XYPoint newAsteroid = new XYPoint(x, y);
                        asteroids.add(newAsteroid);
                    }
                }
                y++;    // next line
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
