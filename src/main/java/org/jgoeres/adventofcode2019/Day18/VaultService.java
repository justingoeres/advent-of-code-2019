package org.jgoeres.adventofcode2019.Day18;

import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class VaultService {
    private final String DAY = "18";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private final HashSet<Character> keyNames = new HashSet<>();
    private final HashSet<Character> doorNames = new HashSet<>();

    private static final Character WALL = '#';
    private static final Character EMPTY = '.';
    private static final Character ENTRANCE = '@';

    private final HashMap<Character, XYPoint> keys = new HashMap<>();
    private final HashMap<Character, XYPoint> doors = new HashMap<>();
    private final HashMap<XYPoint, Character> areaMap = new HashMap<>();
    private HashMap<String, Route> routes = new HashMap<>();

    private XYPoint entrance = null;


    public VaultService() {
        init();
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public VaultService(String pathToFile) {
        init();
        loadInputs(pathToFile);
    }

    public void enumerateAllRoutes() {
        // Enumerate all the distances from each key to every other key

        // From the entrance
        XYPoint c = entrance;
        routes = enumerateRoutes(c, keys);

        // From everywhere else
        for (Map.Entry<Character, XYPoint> key1 : keys.entrySet()) {
            HashMap<String, Route> newRoutes = enumerateRoutes(key1.getValue(), keys);
            routes.putAll(newRoutes);
        }
    }

    public void explore() {
        // By the time we call this, we have a set of all the key-to-key routes and their requirements
        HashSet<Character> keysCollected = new HashSet<>();

        // Start at the entrance, and find the shortest route
        // for which we meet all the requirements
        XYPoint current = new XYPoint(entrance.getX(), entrance.getY());
        int totalDistance = 0;

        while (keysCollected.size() < keys.size()) {
            // do it until we have all the keys

            Character currentGlyph = areaMap.get(current);
            // Find the closest key for which we have all the requirements
            int minDistance = Integer.MAX_VALUE;
            String shortestRouteName = null;
            Route shortestRoute = null;
            for (Map.Entry<String, Route> route : routes.entrySet()) {
                // Does this route involve our key?
                if (route.getKey().contains(currentGlyph.toString())) {
                    // Do we meet the requirements?
                    boolean requirementsMet = true;
                    for (Character requirement : route.getValue().getRequirements())
                        if (!keysCollected.contains(Character.toLowerCase(requirement))) {
                            // If we DON'T have a key we need
                            requirementsMet = false;
                            // quit looking on this route
                            break;
                        }
                    if (requirementsMet) {
                        // If we DO meet the requirements
                        // Is it the shortest we've found so far?
                        if (route.getValue().getDistance() < minDistance) {
                            shortestRouteName = route.getKey();
                            shortestRoute = route.getValue();
                            minDistance = route.getValue().getDistance();
                        }
                    }
                }
            }
            // We should now have the shortest valid route, so let's move along it
            Character p1Name = shortestRouteName.charAt(0);
            Character p2Name = shortestRouteName.charAt(1);

            // Remove all the tracked routes that involve the currentGlyph
            // so we don't use them again later
            removeMatchingRoutes(currentGlyph);

            // The next point we're going to is the one in the route name that DOESN'T match our current one.
            if (currentGlyph.equals(p1Name)) {
                currentGlyph = p2Name;
            } else {
                currentGlyph = p1Name;
            }
            // Add on the distance to our running total
            totalDistance += shortestRoute.getDistance();
            // Collect the new key (which we just set as current a few lines ago)
            keysCollected.add(currentGlyph);
            System.out.println("Collected " + currentGlyph);
            current = keys.get(currentGlyph);

        }
    }

    private void removeMatchingRoutes(Character endpoint) {
        HashSet<String> toRemove = new HashSet<>();
        for (Map.Entry<String, Route> route : routes.entrySet()) {
            if (route.getKey().contains(endpoint.toString())) {
                // if this route matches the endpoint we're trying to remove
                // mark it for removal
                toRemove.add(route.getKey());
            }
        }
        for (String routeName : toRemove) {
            routes.remove(routeName);
        }

    }

    private boolean isRouteKnown(Character keyName1, Character keyName2) {
        // Check if this pair of keys is a known route, in either direction
        String routeName = keyName1.toString() + keyName2.toString();
        String routeNameBackward = keyName2.toString() + keyName1.toString();
        return routes.containsKey(routeName) || routes.containsKey(routeNameBackward);
    }

    public HashMap<String, Route> enumerateRoutes(XYPoint p, HashMap<Character, XYPoint> targets) {
        // Starting from point p, find the distance to each element of targets, record it
        // and return the resulting map.
        // Entries in the return map will be of the form
        //      "cq"    ->  104, {D, F, O}
        // where e.g. the distance from 'c' to 'q' (or from 'q' to 'c') is 104 and the route passes through doors D, F, and O
        HashMap<String, Route> routes = new HashMap<>();

        HashMap<XYPoint, Route> explorers = new HashMap<>();
        HashMap<XYPoint, Route> nextExplorers = new HashMap<>();
        HashMap<XYPoint, Integer> pointsExplored = new HashMap<>();

        // Start from point p
        pointsExplored.put(p, 0);  // don't explore ourselves
        explorers.put(p, emptyRoute());   // explore from the start point first

        Character sourceGlyph = areaMap.get(p);
        while (!explorers.isEmpty()) {
            // Keep going until we run out of places to explore!
            for (XYPoint explorer : explorers.keySet()) {
                // For each explorer head
                // Explore outward from our current location by
                // finding the valid (and unexplored) steps from
                // here, and setting them up to explore next


                Character explorerGlyph = areaMap.get(explorer);
                int explorerDistance = explorers.get(explorer).getDistance();
                HashSet<Character> currentRequirements = explorers.get(explorer).getRequirements();

                // If this point is a key, record it as a Route from
                // start (p) to here
                if (keys.containsKey(explorerGlyph) && !explorer.equals(p)) {
                    if (!isRouteKnown(explorerGlyph, sourceGlyph)) {
                        Route route = new Route(explorerDistance, currentRequirements);
                        String routeName = areaMap.get(p).toString() + areaMap.get(explorer).toString();
                        routes.put(routeName, route);
                    }
                }
                // If this point is a door, record it as a requirement on the current Route
                if (doors.containsKey(explorerGlyph)) {
                    currentRequirements.add(areaMap.get(explorer));
                }

                //

                for (DirectionURDL direction : DirectionURDL.values()) {
                    XYPoint q = explorer.getRelativeLocation(direction);
                    // Is this a valid point to move to?
                    boolean valid = areaMap.containsKey(q);
                    // Have we already explored it?
                    boolean explored = pointsExplored.containsKey(q);
                    // If it is valid and NOT explored
                    if (valid && !explored) {
                        // Add it to the list to explore next
                        Route nextRoute = new Route(explorerDistance + 1, (HashSet<Character>) currentRequirements.clone());
                        nextExplorers.put(q, nextRoute); // It's one step further out than we are now
                    }
                }
                // We've now finished exploring around this point, so
                // add it to the explored list
                pointsExplored.put(explorer, explorerDistance);
            }
            // Switch the nextExplorers into place for the next step
            HashMap<XYPoint, Route> temp;
            temp = explorers;
            explorers = nextExplorers;
            nextExplorers = temp;
            nextExplorers.clear();  // clear the nextExplorers list to start fresh next iteration
        }


        return routes;
    }

    private Route emptyRoute() {
        return new Route(0, new HashSet<>());
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
                        keys.put(c, p);
                        areaMap.put(p, c);
                        continue;
                    }
                    if (doorNames.contains(c)) {
                        // doors are empty and also are doors
                        doors.put(c, p);
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

    class Route {
        Integer distance;
        HashSet<Character> requirements;

        public Route(Integer distance, HashSet<Character> requirements) {
            this.distance = distance;
            this.requirements = requirements;
        }

        public Integer getDistance() {
            return distance;
        }

        public HashSet<Character> getRequirements() {
            return requirements;
        }

    }
}

