package org.jgoeres.adventofcode2019.Day20;

import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;

import javax.sound.sampled.Port;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DonutMazeService {
    private final String DAY = "20";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private final HashMap<XYPoint, Character> areaMap = new HashMap<>();
    private static final HashSet<Character> portalCharacters = new HashSet<>();
    private final HashSet<XYPoint> maze = new HashSet<>();
    private HashMap<String, XYPoint> knownPortals = new HashMap<>();
    private HashMap<XYPoint, XYPoint> portals = new HashMap<>();
    private final String START = "AA";
    private final String FINISH = "ZZ";
    XYPoint startPoint = null;
    XYPoint finishPoint = null;

    static {
        // Make the set of all valid portal name characters
        for (Character c = 'A'; c <= 'Z'; c++) {
            portalCharacters.add(c);
        }
    }

    public DonutMazeService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public DonutMazeService(String pathToFile) {
        loadInputs(pathToFile);
    }


    private void loadInputs(String pathToFile) {
        final Character EMPTY = '.';
        final Character WALL = '#';
        final Character VOID = ' ';

        areaMap.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            int y = 0;

            while ((line = br.readLine()) != null) {
                // process the line.
                // A given point can be:
                //  a portal [AA-ZZ, can occur vertically]
                //  empty '.'
                //  a wall '#'
                //  void space ' ' (behaves like a wall)
                for (int x = 0; x < line.length(); x++) {
                    // Read this character
                    Character c = line.charAt(x);
                    // If it's a wall, do nothing
                    if (c.equals(WALL) || c.equals(VOID)) continue;
                    // Otherwise it's something, so create the XY point for this
                    XYPoint p = new XYPoint(x, y);
                    // For now, just throw everything into the map as characters at XYPoints
                    // We will figure out the portal connections later
                    if (c.equals(EMPTY) || portalCharacters.contains(c)) {
                        areaMap.put(p, c);
                        continue;
                    }
                }
                y++;
            }
            // Now we've got everything in the areaMap but we don't know where the portals are
            // Find the portals by going through the whole map looking for letters.

            // If we find a letter, look around it to find the connection point
            // We are ONLY interested in the "connected" sides of the portals right now.
            // Once we find those, we'll figure out the whole name

            for (XYPoint c : areaMap.keySet()) {
                // Is this a portal?
                if (portalCharacters.contains(areaMap.get(c))) {
                    // is it a connection point? Look around
                    XYPoint connectionPoint = null;
                    String portalName = "";
                    DirectionURDL portalDirection = null;
                    for (DirectionURDL direction : DirectionURDL.values()) {
                        XYPoint p = c.getRelativeLocation(direction);
                        if (areaMap.containsKey(p)) {
                            if (areaMap.get(p).equals(EMPTY)) {
                                // If there's an adjacent point that's "empty" then that's our connection point
                                // and the "coordinate" of our portal is p
                                connectionPoint = p;
                                portalDirection = direction;
                                break;
                            }
                        }
                    }
                    if (connectionPoint != null) {
                        // If this IS a portal connection point
                        // Figure out its name
                        for (DirectionURDL direction : DirectionURDL.values()) {
                            XYPoint p = c.getRelativeLocation(direction);
                            if (portalCharacters.contains(areaMap.get(p))) {
                                // If there's an adjacent point that is also a portal name character
                                // Build our portal name
                                Character cChar = areaMap.get(c);
                                Character pChar = areaMap.get(p);
                                switch (portalDirection) {
                                    case LEFT:
                                        // Label order is c,p
                                        portalName = "" + cChar + pChar;
                                        break;
                                    case RIGHT:
                                        // Label order is p,c
                                        portalName = "" + pChar + cChar;
                                        break;
                                    case DOWN:
                                        // Label order is p,c
                                        portalName = "" + pChar + cChar;
                                        break;
                                    case UP:
                                        // Label order is c,p
                                        portalName = "" + cChar + pChar;
                                        break;
                                }
                                // Is it the start or finish?
                                if (portalName.equals(START)) {
                                    startPoint = connectionPoint;
                                } else if (portalName.equals(FINISH)){
                                    finishPoint = connectionPoint;
                                }
                                // Then do we already know about one end of it?
                                if (!knownPortals.containsKey(portalName)) {
                                    // If this portal is NOT already known
                                    // Add it to the known portals with p as its exit
                                    knownPortals.put(portalName, c);
                                } else {
                                    // This portal IS already half-known
                                    // Add both ends of it to the list of portals
                                    XYPoint endpt1 = knownPortals.get(portalName);
                                    XYPoint endpt2 = c;
                                    portals.put(endpt1, endpt2);
                                    portals.put(endpt2, endpt1);
                                }
                                break;
                            }
                        }
                    }
                }
            }
            //
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
