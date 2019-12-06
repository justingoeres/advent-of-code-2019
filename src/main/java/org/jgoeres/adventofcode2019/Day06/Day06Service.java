package org.jgoeres.adventofcode2019.Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day06Service {
    private final String XX = "06";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private ArrayList<Integer> inputList = new ArrayList<>();
    private Map<String, Orbiter> allOrbiters = new HashMap<>();
    private Set<Orbiter> terminals = new HashSet<>();

    public Day06Service() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public Day06Service(String pathToFile) {
        loadInputs(pathToFile);
    }


    private void loadInputs(String pathToFile) {
        // Input example:
        //  CYP)BC6
        //  FPL)G1W
        //  6MM)5MX
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                String[] orbitPair = line.split("\\)");

                // orbitPair[0] is the parent orbiter
                // orbitPair[1] is the child orbiter (parent is its 'direct orbit')
                // Create both orbiters (if they don't already exist)
                String parentName = orbitPair[0];
                String childName = orbitPair[1];

                // Retrieve parent orbiter, or create new if it doesn't exist
                Orbiter parent;
                if (allOrbiters.containsKey(parentName)) {
                    // If the parent orbiter already exists, get it.
                    parent = allOrbiters.get(parentName);

                    // Because this is a parent, we know it's not a terminal, so
                    // remove it from terminals if it's in there.
                    if (terminals.contains(parent)) {
                        terminals.remove(parent);
                    }
                } else {
                    // Otherwise create it and put it in the big set of orbiters.
                    parent = new Orbiter(); // create it empty, as we don't know its parent yet.
                    allOrbiters.put(parentName, parent);
                }

                // Retrieve child orbiter, or create new with parent
                if (allOrbiters.containsKey(childName)) {
                    // If the child already exists.
                    // Set its parent
                    allOrbiters.get(childName).setParentOrbit(parent);
                } else {
                    // Otherwise create it, link with parent, and put it in the big set of orbiters
                    Orbiter child = new Orbiter(parent);
                    allOrbiters.put(childName, child);

                    // If child is new, add it to terminals
                    terminals.add(child);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
