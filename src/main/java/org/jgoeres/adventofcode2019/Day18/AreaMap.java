package org.jgoeres.adventofcode2019.Day18;

import org.jgoeres.adventofcode2019.common.XYPoint;

import java.util.HashMap;
import java.util.HashSet;

public class AreaMap {
    private final HashMap<Quadrant, HashMap<XYPoint, Character>> map = new HashMap<>();
    private final HashMap<Quadrant, XYPoint> entrances = new HashMap<>();

    public AreaMap() {
        for (Quadrant quadrant : Quadrant.values()) {
            // Create a separate sub-map for each quadrant
            map.put(quadrant, new HashMap<>());
        }
    }

    public HashMap<XYPoint, Character> getQuadrantMap(Quadrant quadrant) {
        return map.get(quadrant);
    }

    public void findQuadrantAndPut(XYPoint p, Character c) {
        // What quadrant does this point live in?
        Quadrant q = Quadrant.findQuadrant(p);
        // Put it in that quadrant's map
        map.get(q).put(p, c);
    }

    public Character getFromQuadrant(Quadrant q, XYPoint p) {
        return map.get(q).get(p);
    }

    public void setQuadrantEntrance(Quadrant q, XYPoint p) {
        entrances.put(q, p);
    }

    public XYPoint getEntrance(Quadrant q) {
        return entrances.get(q);
    }

    public Quadrant getQuadrantFromChar(Character c) {
        for (Quadrant quadrant : Quadrant.values()) {
            // Check each sub-map for the target point
            // and return its quadrant
            if (map.get(quadrant).containsValue(c))
                return quadrant;
        }
        // If not quadrant matches this character, return null
        return null;
    }

    public boolean quadrantContainsPoint(Quadrant q, XYPoint p) {
        return map.get(q).containsKey(p);
    }

    public boolean anyQuadrantContainsPoint(XYPoint p) {
        for (Quadrant quadrant : Quadrant.values()) {
            // Check each sub-map for the target point
            if (map.get(quadrant).containsKey(p))
                return true;
        }
        // Nothing had it, return false
        return false;
    }

    public void clear() {
        // Clear all the quadrant maps
        for (Quadrant quadrant : Quadrant.values()) {
            // Create a separate sub-map for each quadrant
            map.get(quadrant).clear();
        }
    }
}

