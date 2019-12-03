package org.jgoeres.adventofcode2019.Day03;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WireService {
    private final String XX = "03";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private ArrayList<Integer> inputList = new ArrayList<>();
    private ArrayList<List<XYPoint>> wires = new ArrayList<>();

    public WireService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public WireService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public XYPoint findClosestIntersection(ArrayList<XYPoint> wire1, ArrayList<XYPoint> wire2) {
        // Iterate through all the wire combinations to find the closest intersection to the origin
        XYPoint closest = new XYPoint(9999, 9999);

        // Check all wire1 segments
        for (int i = 0; i < wire1.size() - 1; i++) {
            // Check all wire2 segments that haven't been checked against this wire1 yet (don't repeat old combinations)
            for (int j = i; j < wire2.size() - 1; j++) {
                // Do these two segments intersect?


            }
        }
    }

    private void loadInputs(String pathToFile) {
        inputList.clear();
        wires.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;

            final XYPoint origin = new XYPoint(0, 0);
            while ((line = br.readLine()) != null) {
                // Each line of text is one wire
                // e.g. R1002,U407,R530,D268,R516,U937,L74
                Scanner scanner = new Scanner(line);
                Pattern pattern = Pattern.compile("([UDRL])(\\d+)");
                String match;

                ArrayList<XYPoint> junctionPoints = new ArrayList<>();
                // Start at the origin and build out the list of segments in this wire.
                junctionPoints.add(origin);

                XYPoint p1 = origin;
                while ((match = scanner.findInLine(pattern)) != null) {
//                    System.out.println(match);
                    Matcher matcher = pattern.matcher(match);

                    if (matcher.find()) {
                        // Parse out each junction into a direction and a distance.
                        String directionString = matcher.group(1);
                        Direction direction = Direction.get(directionString);
                        Integer distance = Integer.parseInt(matcher.group(2));

//                        System.out.println(matcher.group(0) + "\t" + direction + "\t" + distance);
                        XYPoint nextJunction = endPointFromDirectionAndLength(p1, direction, distance);
                        junctionPoints.add(nextJunction);

                        // Update the origin point for the next segment.
                        p1 = nextJunction;
                    }
                }
                // Add this wire to the set of all wires.
                wires.add(junctionPoints);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private XYPoint endPointFromDirectionAndLength(XYPoint p1, Direction direction, int length) {
        int x2 = 0;
        int y2 = 0;
        switch (direction) {
            case UP:
                x2 = p1.getX();
                y2 = p1.getY() + length;
                break;
            case RIGHT:
                x2 = p1.getX() + length;
                y2 = p1.getY();
                break;
            case DOWN:
                x2 = p1.getX();
                y2 = p1.getY() - length;
                break;
            case LEFT:
                x2 = p1.getX() - length;
                y2 = p1.getY();
                break;
        }
        return new XYPoint(x2, y2);
    }

    private XYPoint findIntersection(XYPoint p1a, XYPoint p1b, XYPoint p2a, XYPoint p2b) {
        XYPoint NO_INTERSECTION = null;

        if (isVertical(p1a, p1b) && isVertical(p2a, p2b)) {
            // If both are vertical
            if (p1a.getX() != p2a.getX()) {
                // and their x-coords aren't equal, then no intersection
                return NO_INTERSECTION;
            } else if (overlapping(p1a.getX(), p1b.getX(), p2a.getX(), p2b.getX())) {
                // If they overlap, find the second-closest endpoint – that will be the closest intersection point to the origin
                

            }
        }

    }

    private boolean isVertical(XYPoint p1, XYPoint p2) {
        return (p1.getX() == p2.getX());
    }

    private boolean isHorizontal(XYPoint p1, XYPoint p2) {
        return (p1.getX() == p2.getX());
    }

    private boolean overlapping(int x1, int x2, int x3, int x4) {
        // These segments overlap if x3 is between x1 and x2
        //                      or if x4 is between x1 and x2
        if ((x3 >= x1 && x3 <= x2) ||
                (x3 >= x2 && x3 <= x1) ||
                (x4 >= x1 && x4 <= x2) ||
                (x4 >= x2 && x4 <= x1)) {
            return true;
        }
        return false;
    }

}
