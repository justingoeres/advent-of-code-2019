package org.jgoeres.adventofcode2019.Day18;

import org.jgoeres.adventofcode2019.common.DirectionURDL;
import org.jgoeres.adventofcode2019.common.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

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

    private final HashMap<SystemStateB, Integer> shortestSystemStateBs = new HashMap<>();

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

    public SystemStateB explore() {
//        // By the time we call this, we have a set of all the key-to-key routes and their requirements
//        HashSet<Character> keysCollected = new HashSet<>();
//
//        // Start at the entrance, and find the shortest route
//        // for which we meet all the requirements
//        XYPoint current = new XYPoint(entrance.getX(), entrance.getY());
//        int totalDistance = 0;

        // Start by creating an initial state with the robots at the entrances
        // It has zero distance and no keys collected
        SystemStateB firstSystemStateB = new SystemStateB(ENTRANCES);
        for (Character entrance : ENTRANCES.toCharArray()) {
            firstSystemStateB.getKeysCollected().add(entrance);  // Add the entrance 'key' so we don't keep going back there
        }

        // Keep the active SystemStateBs in a priority queue, sorted so the *shortest systemStateB with the fewest keys* is at the top
        PriorityQueue<SystemStateB> activeSystemStateBQueue = new PriorityQueue<>(new SystemStateBLeastKeysCollectedThenDistanceComparator());
        activeSystemStateBQueue.add(firstSystemStateB);
        SystemStateB shortestSystemStateB = infiniteSystemStateB();    // Initialize the first shortest systemStateB to a super long one

        int i = 0;
        while (!activeSystemStateBQueue.isEmpty()) {
            // Process all active systemStateBs, continue until there are no more systemStateBs left
            /*** DEBUG ***/
            if (i % 10000 == 0) {
                System.out.println("SystemStateBs checked:\t" + i + "\tcurrent queue:\t" + activeSystemStateBQueue.size());
            }

            SystemStateB systemStateB = activeSystemStateBQueue.poll();
//            System.out.println(systemStateB);
            // Find all the keys we can reach from this systemStateB's current position
            // (i.e. all routes for which we have all requirements)
            HashMap<Character, RouteData> reachableRoutes = findReachableRoutes(systemStateB.getRobots(), systemStateB.getKeysCollected());

            if (reachableRoutes.size() == 0) {
                // If there are no reachable routes from here, this systemStateB is over!
                // Compare this to our shortest known systemStateB.
                // If this one is shorter, it's the new shortest!
                /*** DEBUG ***/
                System.out.println("COMPLETE:\t" + systemStateB);
                if (shortestSystemStateB != null) {
                    Integer shortestDistance = shortestSystemStateB.getTotalDistance();
                    Integer newDistance = systemStateB.getTotalDistance();
                    if (newDistance < shortestDistance) {
                        /*** DEBUG ***/
                        System.out.println("---new shortest systemStateB:\t" + systemStateB.getTotalDistance());
                        shortestSystemStateB = systemStateB;
                    }
                } else {
                    // If this is our first completed systemStateB, it's the shortest by default
                    shortestSystemStateB = systemStateB;
                }
            }
            // For each reachable route, spawn a new systemStateB based on the current one
            for (Map.Entry<Character, RouteData> reachable : reachableRoutes.entrySet()) {
                /*** DEBUG ***/
//                System.out.println("Reachable from " + systemStateB.getCurrentLocation() + ":\t" + reachable.getKey() + " at distance of " + reachable.getValue().getDistance());
                // Add up the new distance
                Integer newDistance = systemStateB.getTotalDistance() + reachable.getValue().getDistance();
                // Duplicate the keys collected list in the new systemStateB
                HashSet<Character> newKeysCollected = new HashSet<>();
                newKeysCollected.addAll(systemStateB.getKeysCollected());
                Character newLocation = reachable.getKey();
                Quadrant q = areaMap.getQuadrantFromChar(newLocation);


                ArrayList<Character> newRobots = (ArrayList<Character>) systemStateB.robots.clone();
                switch(q) {
                    case UPPER_LEFT:
                        newRobots.set(0,newLocation);
                        break;
                    case UPPER_RIGHT:
                        newRobots.set(1,newLocation);
                        break;
                    case LOWER_LEFT:
                        newRobots.set(2,newLocation);
                        break;
                    case LOWER_RIGHT:
                        newRobots.set(3,newLocation);
                        break;
                }

                // Add the new key we're just now picking up
                newKeysCollected.add(Character.toUpperCase(newLocation));
                SystemStateB newSystemStateB = new SystemStateB(newRobots, newKeysCollected, newDistance);
                // Add this new systemStateB to the active queue...
                // If it's shorter than any equivalent systemStateB
                if (!shortestSystemStateBs.containsKey(newSystemStateB)) {
                    // If we haven't seen this systemStateB before
                    // then this is by definition the shortest. Add it.
                    shortestSystemStateBs.put(newSystemStateB, newSystemStateB.getTotalDistance());
                    // and add it to the active systemStateBs queue
                    activeSystemStateBQueue.add(newSystemStateB);
                } else {
                    // Get the distance of the shortest-so-far version of this systemStateB
                    Integer shortestDistance = shortestSystemStateBs.get(newSystemStateB);
                    if (newSystemStateB.getTotalDistance() < shortestDistance) {
                        // If this is a new shortest distance
                        // replace this entry in the map
                        shortestSystemStateBs.put(newSystemStateB, newSystemStateB.getTotalDistance());
                        // and add it to the active systemStateBs queue
                        activeSystemStateBQueue.add(newSystemStateB);
                    }
                }
                // otherwise this is neither a new systemStateB, nor is it the shortest version of itself
                // so just drop it.
            }
            i++;
        }
        return shortestSystemStateB;
    }

    private HashMap<Character, RouteData> findReachableRoutes(ArrayList<Character> robotLocations, HashSet<Character> keysCollected) {
        HashMap<Character, RouteData> reachableRoutes = new HashMap<>();
        for (Character robot : robotLocations) {
            // 1. Get all the routes with currentGlyph as their source
            HashMap<Character, RouteData> potentialRoutes = routes.get(robot);

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
                    boolean valid = areaMap.getQuadrantMap(q).containsKey(p2);
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

    private SystemStateB infiniteSystemStateB() {
        SystemStateB infiniteSystemStateB = new SystemStateB();
        infiniteSystemStateB.setTotalDistance(Integer.MAX_VALUE);
        return infiniteSystemStateB;
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

    public class SystemStateB {
        ArrayList<Character> robots = new ArrayList<>();

        Integer totalDistance;

        HashSet<Character> keysCollected;   // shared among all robots

        public SystemStateB() {
            keysCollected = new HashSet<>();
            totalDistance = 0;
        }
//
//        public SystemStateB(Character robot1, Character robot2, Character robot3, Character robot4) {
//            this.robot1 = robot1;
//            this.robot2 = robot2;
//            this.robot3 = robot3;
//            this.robot4 = robot4;
//            keysCollected = new HashSet<>();
//            totalDistance = 0;
//        }

        public SystemStateB(ArrayList<Character> robots) {
            this.robots = (ArrayList<Character>) robots.clone();
            keysCollected = new HashSet<>();
            totalDistance = 0;
        }

        public SystemStateB(String robotsString) {
            for (int i = 0; i < robotsString.length(); i++) {
                robots.add(robotsString.charAt(i));
            }
//            robot1 = robotsString.charAt(0);
//            robot2 = robotsString.charAt(1);
//            robot3 = robotsString.charAt(2);
//            robot4 = robotsString.charAt(3);
            keysCollected = new HashSet<>();
            totalDistance = 0;
        }

        public SystemStateB(ArrayList<Character> robots, HashSet<Character> keysCollected, Integer totalDistance) {
            this.robots = robots;
            this.keysCollected = keysCollected;
            this.totalDistance = totalDistance;
        }

        @Override
        public int hashCode() {
            // Two states are "equal" based on their current robot locations & keysCollected
            // NOT based on their distance
            return Objects.hash(robots.get(0),
                    robots.get(1),
                    robots.get(2),
                    robots.get(3));
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof SystemStateB)) {
                return false;
            }
            SystemStateB systemStateB = (SystemStateB) o;
            return (systemStateB.keysCollected.equals(keysCollected)
                    && systemStateB.robots.equals(((SystemStateB) o).robots));
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

        public ArrayList<Character> getRobots() {
            return robots;
        }

//        public ArrayList<Character> getAllLocations() {
//            ArrayList<Character> allLocations = new ArrayList<>();
//            allLocations.add(robot1);
//            allLocations.add(robot2);
//            allLocations.add(robot3);
//            allLocations.add(robot4);
//            return allLocations;
//        }

//        public Character getCurrentLocation(Robot robot) {
//            switch (robot) {
//                case ROBOT_1:
//                    return robot1;
//                case ROBOT_2:
//                    return robot2;
//                case ROBOT_3:
//                    return robot3;
//                case ROBOT_4:
//                    return robot4;
//            }
//            return null;
//        }

        @Override
        public String toString() {
            return "Robots: " + robots + ", Distance: " + totalDistance + ", Keys: " + keysCollected.size() + " " + keysCollected;
        }
    }

    enum Robot {
        ROBOT_1,
        ROBOT_2,
        ROBOT_3,
        ROBOT_4;
    }

    class SystemStateBDistanceComparator implements Comparator<SystemStateB> {
        // Overriding compare()method of Comparator
        // Sort SystemStateBs so shorter ones come first
        public int compare(SystemStateB j1, SystemStateB j2) {
            if (j1.totalDistance > j2.totalDistance)
                return 1;
            else if (j1.totalDistance < j2.totalDistance)
                return -1;
            return 0;
        }
    }

    class SystemStateBKeysCollectedComparator implements Comparator<SystemStateB> {
        // Overriding compare()method of Comparator
        // Sort SystemStateBs so ones with more keys collected come first
        public int compare(SystemStateB j1, SystemStateB j2) {
            if (j1.keysCollected.size() < j2.keysCollected.size())
                return 1;
            else if (j1.keysCollected.size() > j2.keysCollected.size())
                return -1;
            return 0;
        }
    }

    class SystemStateBLeastKeysCollectedComparator implements Comparator<SystemStateB> {
        // Overriding compare()method of Comparator
        // Sort SystemStateBs so ones with more keys collected come first
        public int compare(SystemStateB j1, SystemStateB j2) {
            if (j1.keysCollected.size() > j2.keysCollected.size())
                return 1;
            else if (j1.keysCollected.size() < j2.keysCollected.size())
                return -1;
            return 0;
        }
    }

    class SystemStateBKeysCollectedThenDistanceComparator implements Comparator<SystemStateB> {
        // Overriding compare()method of Comparator
        // Sort SystemStateBs so ones with more keys collected come first
        public int compare(SystemStateB j1, SystemStateB j2) {
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

    class SystemStateBLeastKeysCollectedThenDistanceComparator implements Comparator<SystemStateB> {
        // Overriding compare()method of Comparator
        // Sort SystemStateBs so ones with fewer keys and then less distance come first
        public int compare(SystemStateB j1, SystemStateB j2) {
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

