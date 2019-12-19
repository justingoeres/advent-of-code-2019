package org.jgoeres.adventofcode2019.Day18;

import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

import static org.jgoeres.adventofcode2019.Day18.Quadrant.UPPER_LEFT;
import static org.jgoeres.adventofcode2019.Day18.Quadrant.findQuadrant;

public class VaultServicePartB {
    private final String DAY = "18";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input2.txt";

    private final HashSet<Character> keyNames = new HashSet<>();
    private final HashSet<Character> doorNames = new HashSet<>();

    private static final Character WALL = '#';
    private static final Character EMPTY = '.';
    private static final Character ENTRANCE = '@';
    private static final String ENTRANCES = "1234";

    private final HashMap<Character, XYPoint> keys = new HashMap<>();
    private final HashMap<Character, XYPoint> doors = new HashMap<>();
    private final AreaMap areaMap = new AreaMap();
    private HashMap<Character, HashMap<Character, RouteData>> routes = new HashMap<>();

    private XYPoint entrance = null;

    private final HashMap<Journey, Integer> shortestJourneys = new HashMap<>();

    public VaultServicePartB() {
        init();
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public VaultServicePartB(String pathToFile) {
        init();
        loadInputs(pathToFile);
    }

    public void enumerateAllRoutes() {
        // For each quadrant

        for (Quadrant q : Quadrant.values()) {
            // Enumerate all the distances from each key to every other key
            // From the entrances
            XYPoint c = areaMap.getEntrance(q);
            enumerateRoutes(c);

            // From everywhere else
            for (Map.Entry<Character, XYPoint> key1 : keys.entrySet()) {
                enumerateRoutes(key1.getValue());
            }
        }
    }

    public Journey explore() {
        // By the time we call this, we have a set of all the key-to-key routes and their requirements
        HashSet<Character> keysCollected = new HashSet<>();

        // Start at the entrance, and find the shortest route
        // for which we meet all the requirements
        XYPoint current = new XYPoint(entrance.getX(), entrance.getY());
        int totalDistance = 0;

        // Start by creating one journey at the entrance
        // It has zero distance and no keys collected
        Journey firstJourney = new Journey(areaMap.getFromQuadrant(UPPER_LEFT, entrance));   // TODO: THIS IS A PLACEHOLDER
        firstJourney.getKeysCollected().add(ENTRANCE);  // Add the entrance 'key' so we don't keep going back there

        // Keep the active Journeys in a priority queue, sorted so the *shortest journey with the fewest keys* is at the top
        PriorityQueue<Journey> activeJourneyQueue = new PriorityQueue<>(new JourneyLeastKeysCollectedThenDistanceComparator());
        activeJourneyQueue.add(firstJourney);
        Journey shortestJourney = infiniteJourney();    // Initialize the first shortest journey to a super long one

        int i = 0;
        while (!activeJourneyQueue.isEmpty()) {
            // Process all active journeys, continue until there are no more journeys left
            /*** DEBUG ***/
            if (i % 10000 == 0) {
                System.out.println("Journeys checked:\t" + i + "\tcurrent queue:\t" + activeJourneyQueue.size());
            }

            Journey journey = activeJourneyQueue.poll();
//            System.out.println(journey);
            // Find all the keys we can reach from this journey's current position
            // (i.e. all routes for which we have all requirements)
            HashMap<Character, RouteData> reachableRoutes = findReachableRoutes(journey.getCurrentLocation(), journey.getKeysCollected());

            if (reachableRoutes.size() == 0) {
                // If there are no reachable routes from here, this journey is over!
                // Compare this to our shortest known journey.
                // If this one is shorter, it's the new shortest!
                /*** DEBUG ***/
                System.out.println("COMPLETE:\t" + journey);
                if (shortestJourney != null) {
                    Integer shortestDistance = shortestJourney.getTotalDistance();
                    Integer newDistance = journey.getTotalDistance();
                    if (newDistance < shortestDistance) {
                        /*** DEBUG ***/
                        System.out.println("---new shortest journey:\t" + journey.getTotalDistance());
                        shortestJourney = journey;
                    }
                } else {
                    // If this is our first completed journey, it's the shortest by default
                    shortestJourney = journey;
                }
            }
            // For each reachable route, spawn a new journey based on the current one
            for (Map.Entry<Character, RouteData> reachable : reachableRoutes.entrySet()) {
                /*** DEBUG ***/
//                System.out.println("Reachable from " + journey.getCurrentLocation() + ":\t" + reachable.getKey() + " at distance of " + reachable.getValue().getDistance());
                // Add up the new distance
                Integer newDistance = journey.getTotalDistance() + reachable.getValue().getDistance();
                // Duplicate the keys collected list in the new journey
                HashSet<Character> newKeysCollected = new HashSet<>();
                newKeysCollected.addAll(journey.getKeysCollected());
                Character newLocation = reachable.getKey();
                // Add the new key we're just now picking up
                newKeysCollected.add(Character.toUpperCase(newLocation));
                Journey newJourney = new Journey(newLocation, newKeysCollected, newDistance);
                // Add this new journey to the active queue...
                // If it's shorter than any equivalent journey
                if (!shortestJourneys.containsKey(newJourney)) {
                    // If we haven't seen this journey before
                    // then this is by definition the shortest. Add it.
                    shortestJourneys.put(newJourney, newJourney.getTotalDistance());
                    // and add it to the active journeys queue
                    activeJourneyQueue.add(newJourney);
                } else {
                    // Get the distance of the shortest-so-far version of this journey
                    Integer shortestDistance = shortestJourneys.get(newJourney);
                    if (newJourney.getTotalDistance() < shortestDistance) {
                        // If this is a new shortest distance
                        // replace this entry in the map
                        shortestJourneys.put(newJourney, newJourney.getTotalDistance());
                        // and add it to the active journeys queue
                        activeJourneyQueue.add(newJourney);
                    }
                }
                // otherwise this is neither a new journey, nor is it the shortest version of itself
                // so just drop it.
            }
            i++;
        }
        return shortestJourney;
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

        Quadrant q = findQuadrant(p);
        Character source = areaMap.getFromQuadrant(q, p);
        while (!explorers.isEmpty()) {
            // Keep going until we run out of places to explore!
            for (XYPoint explorer : explorers.keySet()) {
                // For each explorer head
                // Explore outward from our current location by
                // finding the valid (and unexplored) steps from
                // here, and setting them up to explore next

                Character explorerGlyph = areaMap.getFromQuadrant(q, explorer);// TODO: THIS IS A PLACEHOLDER
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
                    currentRequirements.add(areaMap.getFromQuadrant(q, explorer)); // TODO: THIS IS A PLACEHOLDER
                }

                for (DirectionURDL direction : DirectionURDL.values()) {
                    XYPoint p2 = explorer.getRelativeLocation(direction);
                    // Is this a valid point to move to?
                    boolean valid = q.inQuadrant(p2);    // TODO: THIS IS A PLACEHOLDER
                    // Have we already explored it?
                    boolean explored = pointsExplored.containsKey(p2);
                    // If it is valid and NOT explored
                    if (valid && !explored) {
                        // Add it to the list to explore next
                        RouteData nextRouteData = new RouteData(explorerDistance + 1, (HashSet<Character>) currentRequirements.clone());
                        nextExplorers.put(p2, nextRouteData); // It's one step further out than we are now
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
        for (Character entrance : ENTRANCES.toCharArray()) {
            routes.put(entrance, new HashMap<>());
        }
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
                        areaMap.findQuadrantAndPut(p, c);
                        continue;
                    }
//                    if (c.equals(ENTRANCE)) {
                    if (ENTRANCES.contains(Character.toString(c))) {
                        // the entrance is empty and is also the entrance
                        areaMap.setQuadrantEntrance(findQuadrant(p), p);
                        areaMap.findQuadrantAndPut(p, c);
                        continue;
                    }
                    if (keyNames.contains(c)) {
                        // keys are empty and also are keys
                        keys.put(c, p);
                        areaMap.findQuadrantAndPut(p, c);
                        continue;
                    }
                    if (doorNames.contains(c)) {
                        // doors are empty and also are doors
                        doors.put(c, p);
                        areaMap.findQuadrantAndPut(p, c);
                        continue;
                    }
                }
                y++;    // next line
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private Journey infiniteJourney() {
        Journey infiniteJourney = new Journey();
        infiniteJourney.setTotalDistance(Integer.MAX_VALUE);
        return infiniteJourney;
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

        @Override
        public int hashCode() {
            // Two journeys are "equal" based on their current location & keysCollected
            // NOT based on their distance
            return Objects.hash(currentLocation);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Journey)) {
                return false;
            }
            Journey journey = (Journey) o;
            return journey.keysCollected.equals(keysCollected) &&
                    journey.currentLocation == currentLocation;
        }

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

        @Override
        public String toString() {
            return "Point '" + currentLocation + "', Distance: " + totalDistance + ", Keys: " + keysCollected.size() + " " + keysCollected;
        }
    }

    class JourneyDistanceComparator implements Comparator<Journey> {
        // Overriding compare()method of Comparator
        // Sort Journeys so shorter ones come first
        public int compare(Journey j1, Journey j2) {
            if (j1.totalDistance > j2.totalDistance)
                return 1;
            else if (j1.totalDistance < j2.totalDistance)
                return -1;
            return 0;
        }
    }

    class JourneyKeysCollectedComparator implements Comparator<Journey> {
        // Overriding compare()method of Comparator
        // Sort Journeys so ones with more keys collected come first
        public int compare(Journey j1, Journey j2) {
            if (j1.keysCollected.size() < j2.keysCollected.size())
                return 1;
            else if (j1.keysCollected.size() > j2.keysCollected.size())
                return -1;
            return 0;
        }
    }

    class JourneyLeastKeysCollectedComparator implements Comparator<Journey> {
        // Overriding compare()method of Comparator
        // Sort Journeys so ones with more keys collected come first
        public int compare(Journey j1, Journey j2) {
            if (j1.keysCollected.size() > j2.keysCollected.size())
                return 1;
            else if (j1.keysCollected.size() < j2.keysCollected.size())
                return -1;
            return 0;
        }
    }

    class JourneyKeysCollectedThenDistanceComparator implements Comparator<Journey> {
        // Overriding compare()method of Comparator
        // Sort Journeys so ones with more keys collected come first
        public int compare(Journey j1, Journey j2) {
            if (j1.keysCollected.size() < j2.keysCollected.size()) {
                return 1;
            } else if (j1.keysCollected.size() > j2.keysCollected.size()) {
                return -1;
            } else {
                // Keys collected is equal, so sort on distance (shorter first)
                if (j1.totalDistance > j2.totalDistance) {
                    return 1;
                } else if (j1.totalDistance < j2.totalDistance) {
                    return -1;
                }
            }
            return 0;
        }
    }

    class JourneyLeastKeysCollectedThenDistanceComparator implements Comparator<Journey> {
        // Overriding compare()method of Comparator
        // Sort Journeys so ones with fewer keys and then less distance come first
        public int compare(Journey j1, Journey j2) {
            if (j1.keysCollected.size() > j2.keysCollected.size()) {
                return 1;
            } else if (j1.keysCollected.size() < j2.keysCollected.size()) {
                return -1;
            } else {
                // Keys collected is equal, so sort on distance (shorter first)
                if (j1.totalDistance > j2.totalDistance) {
                    return 1;
                } else if (j1.totalDistance < j2.totalDistance) {
                    return -1;
                }
            }
            return 0;
        }
    }
}

