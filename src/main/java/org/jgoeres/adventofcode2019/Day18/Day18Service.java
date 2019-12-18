package org.jgoeres.adventofcode2019.Day18;

import org.jgoeres.adventofcode2019.common.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day18Service {
    private final String DAY = "18";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private final HashSet<Character> keyNames = new HashSet<>();
    private final HashSet<Character> doorNames = new HashSet<>();
    private static final Character WALL = '#';
    private static final Character EMPTY = '.';
    private static final Character ENTRANCE = '@';

    private final HashMap<XYPoint, Character> keys = new HashMap<>();
    private final HashMap<XYPoint, Character> doors = new HashMap<>();
    private final HashMap<XYPoint, Character> areaMap = new HashMap<>();

    private XYPoint entrance = null;


    public Day18Service() {
        init();
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public Day18Service(String pathToFile) {
        init();
        loadInputs(pathToFile);
    }

    private void init() {
        // Make the set of all door names
        for (Character c = 'a'; c <= 'z'; c++) {
            keyNames.add(c);
        }
        for (Character c = 'A'; c <= 'Z'; c++) {
            doorNames.add(c);
        }
    }

    private void loadInputs(String pathToFile) {
        keys.clear();
        doors.clear();
        areaMap.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                // process the line character by character
                // A given point can be:
                //  a key [a-z]
                //  a door [A-Z]
                //  the entrance [@]
                //  empty '.'
                //  a wall '#'
                for (int x = 0; x < line.length(); x++) {
                    // Read this character
                    Character c = line.charAt(x);
                    // If it's a wall, do nothing
                    if (c.equals(WALL)) continue;
                    // Otherwise it's something, so create the XY point for this
                    XYPoint p = new XYPoint(x, y);
                    // Figure out what it is
                    if (c.equals(EMPTY)) {
                        areaMap.put(p, c);
                        continue;
                    }
                    if (c.equals(ENTRANCE)) {
                        // the entrance is empty and is also the entrance
                        entrance = p;
                        areaMap.put(p, c);
                        continue;
                    }
                    if (keyNames.contains(c)) {
                        // keys are empty and also are keys
                        keys.put(p, c);
                        areaMap.put(p, c);
                        continue;
                    }
                    if (doorNames.contains(c)) {
                        // doors are empty and also are doors
                        doors.put(p, c);
                        areaMap.put(p, c);
                        continue;
                    }
                }
                y++;    // next line
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
