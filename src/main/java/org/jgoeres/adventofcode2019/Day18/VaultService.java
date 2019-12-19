package org.jgoeres.adventofcode2019.Day18;

import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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
    private HashMap<Character, HashMap<Character, RouteData>> routes = new HashMap<>();

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
        enumerateRoutes(c);

        // From everywhere else
        for (Map.Entry<Character, XYPoint> key1 : keys.entrySet()) {
            enumerateRoutes(key1.getValue());
        }
    }

    public ArrayList<Journey> explore() {
        // By the time we call this, we have a set of all the key-to-key routes and their requirements
        HashSet<Character> keysCollected = new HashSet<>();

        // Start at the entrance, and find the shortest route
        // for which we meet all the requirements
        XYPoint current = new XYPoint(entrance.getX(), entrance.getY());
        int totalDistance = 0;

        // Start by creating one journey at the entrance
        // It has zero distance and no keys collected
        Journey firstJourney = new Journey(areaMap.get(entrance));
        firstJourney.getKeysCollected().add(ENTRANCE);  // Add the entrance 'key' so we don't keep going back there

        ArrayList<Journey> activeJourneys = new ArrayList<>();
        activeJourneys.add(firstJourney);

        ArrayList<Journey> nextActiveJourneys = new ArrayList<>();
        ArrayList<Journey> completedJourneys = new ArrayList<>();

        while (!activeJourneys.isEmpty()) {
            // Process all active journeys, continue until there are no more journeys left
            for (Journey journey : activeJourneys) {
                // Find all the keys we can reach from this journey's current position
                // (i.e. all routes for which we have all requirements)
                HashMap<Character, RouteData> reachableRoutes = findReachableRoutes(journey.getCurrentLocation(), journey.getKeysCollected());

                if (reachableRoutes.size() == 0) {
                    // If there are no reachable routes from here, this journey is over!
                    completedJourneys.add(journey);
                }
                // For each reachable route, spawn a new journey based on the current one
                for (Map.Entry<Character, RouteData> reachable : reachableRoutes.entrySet()) {
                    /*** DEBUG ***/
//                    System.out.println("Reachable from " + journey.getCurrentLocation() + ":\t" + reachable.getKey() + " at distance of " + reachable.getValue().getDistance());
                    // Add up the new distance
                    Integer newDistance = journey.getTotalDistance() + reachable.getValue().getDistance();
                    // Duplicate the keys collected list in the new journey
                    HashSet<Character> newKeysCollected = new HashSet<>();
                    newKeysCollected.addAll(journey.getKeysCollected());
                    Character newLocation = reachable.getKey();
                    // Add the new key we're just now picking up
                    newKeysCollected.add(Character.toUpperCase(newLocation));
                    Journey newJourney = new Journey(newLocation, newKeysCollected, newDistance);
                    // Add the new journey to the active list
                    nextActiveJourneys.add(newJourney);
                }
            }
            // Switch the new list of active journeys in for the next iteration
            ArrayList<Journey> tempJourneys = activeJourneys;
            activeJourneys = nextActiveJourneys;
            nextActiveJourneys = tempJourneys;
            // Clear the list we just processed, so we don't have to allocate this new every time.
            nextActiveJourneys.clear();
        }
        return completedJourneys;
    }

    private HashMap<Character, RouteData> findReachableRoutes(Character currentGlyph, HashSet<Character> keysCollected) {
        HashMap<Character, RouteData> reachableRoutes = new HashMap<>();
        // 1. Get all the routes with currentGlyph as their source
        HashMap<Character, RouteData> potentialRoutes = routes.get(currentGlyph);

        // 2. Check all those routes and find the ones whose requirements are satisfied. They are "reachable"
        for (Map.Entry<Character, RouteData> endpoint : potentialRoutes.entrySet()) {
            boolean someKeysCollected = !keysCollected.isEmpty();
            boolean noRequirements = endpoint.getValue().getRequirements().isEmpty();
            boolean notAlreadyVisited = !keysCollected.contains(Character.toUpperCase(endpoint.getKey()));
            boolean requirementsMet = notAlreadyVisited &&
                    (noRequirements ||
                            (someKeysCollected && keysCollected.containsAll(endpoint.getValue().getRequirements())));
            if (requirementsMet) {
                // Add this endpoint to the set of reachables
                reachableRoutes.put(endpoint.getKey(), endpoint.getValue());
            }
        }
        return reachableRoutes;
    }

    public Journey findShortestJourney(ArrayList<Journey> journeys) {
        int minDistance = Integer.MAX_VALUE;
        Journey minJourney = null;
        for (Journey journey : journeys) {
            if (journey.getTotalDistance() < minDistance) {
                minDistance = journey.totalDistance;
                minJourney = journey;
            }
        }
        return minJourney;
    }

    private Character nextDestinationFromRoute(Character currentGlyph, String routeName) {
        Character destinationGlyph;

        Character p1Name = routeName.charAt(0);
        Character p2Name = routeName.charAt(1);

        // The next point we're going to is the one in the route name that DOESN'T match our current one.
        if (currentGlyph.equals(p1Name)) {
            destinationGlyph = p2Name;
        } else {
            destinationGlyph = p1Name;
        }
        return destinationGlyph;
    }

//    private String findShortestRoute(Character fromGlyph, HashSet<Character> keysCollected) {
//        int minDistance = Integer.MAX_VALUE;
//
//        String shortestRouteName = null;
//        for (Map.Entry<Character, HashMap<Character, RouteData>> route : routes.entrySet()) {
//            // Does this route involve our key?
//            if (route.getKey().equals(fromGlyph)) {
//                // Do we meet the requirements?
//                boolean requirementsMet = true;
//                for (Character requirement : route.getValue().getRequirements())
//                    if (!keysCollected.contains(Character.toLowerCase(requirement))) {
//                        // If we DON'T have a key we need
//                        requirementsMet = false;
//                        // quit looking on this route
//                        break;
//                    }
//                if (requirementsMet) {
//                    // If we DO meet the requirements
//                    // Is it the shortest we've found so far?
//                    if (route.getValue().getDistance() < minDistance) {
//                        shortestRouteName = route.getKey();
//                        minDistance = route.getValue().getDistance();
//                    }
//                }
//            }
//        }
//        return shortestRouteName;
//    }

    // TODO: Remove this – we don't want to remove routes anymore
    private void removeMatchingRoutes(Character endpoint) {
        HashSet<Character> toRemove = new HashSet<>();
        for (Map.Entry<Character, HashMap<Character, RouteData>> route : routes.entrySet()) {
            if (route.getKey().equals(endpoint)) {
                // if this route matches the endpoint we're trying to remove
                // mark it for removal
                toRemove.add(route.getKey());
            }
        }
        for (Character routeName : toRemove) {
            routes.remove(routeName);
        }

    }

    private boolean isRouteKnown(Character keyName1, Character keyName2) {
        // Check if this pair of keys is a known route, in either direction
        String routeName = keyName1.toString() + keyName2.toString();
        String routeNameBackward = keyName2.toString() + keyName1.toString();
        return routes.containsKey(routeName) || routes.containsKey(routeNameBackward);
    }

    public HashMap<Character, HashMap<Character, RouteData>> enumerateRoutes(XYPoint p) {
        // Starting from point p, find the distance to each element of targets, record it
        // and return the resulting map.
        // Entries in the return map will be of the form
        //      "cq"    ->  104, {D, F, O}
        // where e.g. the distance from 'c' to 'q' (or from 'q' to 'c') is 104 and the route passes through doors D, F, and O
        HashMap<XYPoint, RouteData> explorers = new HashMap<>();
        HashMap<XYPoint, RouteData> nextExplorers = new HashMap<>();
        HashMap<XYPoint, Integer> pointsExplored = new HashMap<>();

        // Start from point p
        pointsExplored.put(p, 0);  // don't explore ourselves
        explorers.put(p, emptyRoute());   // explore from the start point first

        Character source = areaMap.get(p);
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
//                    if (!isRouteKnown(explorerGlyph, source)) {
                    RouteData routeData = new RouteData(explorerDistance, currentRequirements);
//                        String routeName = areaMap.get(p).toString() + areaMap.get(explorer).toString();
                    // Put the new route into p -> explorer -> routedata
                    routes.get(source).put(explorerGlyph, routeData);
                    // Also put in the reverse route while we're here
                    routes.get(explorerGlyph).put(source, routeData);
//                    }
                }
                // If this point is a door, record it as a requirement on the current Route
                if (doors.containsKey(explorerGlyph)) {
                    currentRequirements.add(areaMap.get(explorer));
                }

                for (DirectionURDL direction : DirectionURDL.values()) {
                    XYPoint q = explorer.getRelativeLocation(direction);
                    // Is this a valid point to move to?
                    boolean valid = areaMap.containsKey(q);
                    // Have we already explored it?
                    boolean explored = pointsExplored.containsKey(q);
                    // If it is valid and NOT explored
                    if (valid && !explored) {
                        // Add it to the list to explore next
                        RouteData nextRouteData = new RouteData(explorerDistance + 1, (HashSet<Character>) currentRequirements.clone());
                        nextExplorers.put(q, nextRouteData); // It's one step further out than we are now
                    }
                }
                // We've now finished exploring around this point, so
                // add it to the explored list
                pointsExplored.put(explorer, explorerDistance);
            }
            // Switch the nextExplorers into place for the next step
            HashMap<XYPoint, RouteData> temp;
            temp = explorers;
            explorers = nextExplorers;
            nextExplorers = temp;
            nextExplorers.clear();  // clear the nextExplorers list to start fresh next iteration
        }
        return routes;
    }

    private RouteData emptyRoute() {
        return new RouteData(0, new HashSet<>());
    }


    private void init() {
        routes.clear();
        routes.put(ENTRANCE, new HashMap<>());
        // Make the set of all door names & initialize the routes maps
        for (Character c = 'a'; c <= 'z'; c++) {
            keyNames.add(c);
            routes.put(c, new HashMap<>());
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


    class RouteData {
        Integer distance;
        HashSet<Character> requirements;

        public RouteData(Integer distance, HashSet<Character> requirements) {
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

    public class Journey {
        private Character currentLocation;
        private HashSet<Character> keysCollected;
        private Integer totalDistance;

        public Journey() {
            keysCollected = new HashSet<>();
            totalDistance = 0;
        }

        public Journey(Character currentLocation) {
            this.currentLocation = currentLocation;
            keysCollected = new HashSet<>();
            totalDistance = 0;
        }

        public Journey(Character currentLocation, HashSet<Character> keysCollected, Integer totalDistance) {
            this.currentLocation = currentLocation;
            this.keysCollected = keysCollected;
            this.totalDistance = totalDistance;
        }

        public void setTotalDistance(Integer totalDistance) {
            this.totalDistance = totalDistance;
        }

        public void addToTotalDistance(int amount) {
            totalDistance += amount;
        }

        public boolean isComplete() {
            return (keysCollected.size() == keys.size());
        }

        public void duplicateKeysCollected(HashSet<Character> sourceKeys) {
            keysCollected.addAll(sourceKeys);
        }

        public HashSet<Character> getKeysCollected() {
            return keysCollected;
        }

        public Integer getTotalDistance() {
            return totalDistance;
        }

        public Character getCurrentLocation() {
            return currentLocation;
        }
    }
}

