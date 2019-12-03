package org.jgoeres.adventofcode2019.Day03;

import com.google.common.collect.Range;

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
    private ArrayList<List<WireSegment>> wires = new ArrayList<>();

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
        return null;
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

                ArrayList<WireSegment> wireSegments = new ArrayList<>();
                // Start at the origin and build out the list of segments in this wire.
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

                        // Create this wire segment
                        WireSegment wireSegment = new WireSegment(direction, distance, p1);

                        // Add it to the segment list of the current wire
                        wireSegments.add(wireSegment);

                        // Update the origin point for the next segment.
                        p1 = wireSegment.getP2();
                    }
                }
                // Add this wire to the set of all wires.
                wires.add(wireSegments);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public XYPoint findIntersection(WireSegment w1, WireSegment w2) {
        final XYPoint NO_INTERSECTION = null;

        Integer lowestIntersection;
        if (isVertical(w1) && isVertical(w2)) {
            // If both are vertical
            if (w1.getP1().getX() != w2.getP1().getX()) {
                // and their x-coords aren't equal, then no intersection
                return NO_INTERSECTION;
            } else if ((lowestIntersection = overlapping(w1.getP1().getY(), w1.getP2().getY(), w2.getP1().getY(), w2.getP2().getY())) != null) {
                // if they overlap, return the closest overlap point
                System.out.println(lowestIntersection);
                return new XYPoint(w1.getP1().getX(), lowestIntersection);
            }
        } else if (isHorizontal(w1) && isHorizontal(w2)) {
            // If both are horizontal
            if (w1.getP1().getY() != w2.getP1().getY()) {
                // and their x-coords aren't equal, then no intersection
                return NO_INTERSECTION;
            } else if ((lowestIntersection = overlapping(w1.getP1().getX(), w1.getP2().getX(), w2.getP1().getX(), w2.getP2().getX())) != null) {
                // if they overlap, return the closest overlap point
                System.out.println(lowestIntersection);
                return new XYPoint(lowestIntersection, w1.getP1().getY());
            }
        } else {
            // The two segments are different orientations; let's see if they intersect!
            // TODO: IMPLEMENT INTERSECTION CHECK HERE
        }

        return null;
    }

    private boolean isVertical(WireSegment wireSegment) {
        return (wireSegment.getP1().getX() == wireSegment.getP2().getX());
    }

    private boolean isHorizontal(WireSegment wireSegment) {
        return (wireSegment.getP1().getY() == wireSegment.getP2().getY());
    }

    private Integer overlapping(int x1, int x2, int x3, int x4) {
        // These segments overlap if x3 is between x1 and x2
        //                      or if x4 is between x1 and x2
        Range<Integer> w1Range = Range.closed(Math.min(x1, x2), Math.max(x1, x2));
        Range<Integer> w2Range = Range.closed(Math.min(x3, x4), Math.max(x3, x4));

        // Do these two segments overlap?
        Range<Integer> intersection = w1Range.intersection(w2Range);
        if (intersection != null) {
            if (Math.abs(intersection.lowerEndpoint()) < Math.abs(intersection.upperEndpoint())) {
                return intersection.lowerEndpoint();
            } else {
                return intersection.upperEndpoint();
            }
        } else {
            return null;
        }
    }


}
