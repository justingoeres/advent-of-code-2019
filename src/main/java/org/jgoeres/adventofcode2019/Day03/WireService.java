package org.jgoeres.adventofcode2019.Day03;

import com.google.common.collect.Range;
import org.jgoeres.adventofcode2019.common.Direction;
import org.jgoeres.adventofcode2019.common.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.jgoeres.adventofcode2019.common.AoCMath.ORIGIN;
import static org.jgoeres.adventofcode2019.common.AoCMath.manhattanDistance;

@SuppressWarnings("ConstantConditions")
public class WireService {
    private final String XX = "03";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";
    private final XYPoint NO_INTERSECTION = null;

    private ArrayList<List<WireSegment>> wires = new ArrayList<>();
    private ArrayList<XYPoint> allIntersections = new ArrayList<>();

    public WireService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public WireService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public XYPoint findClosestIntersection(XYPoint reference) {
        // Iterate through all the wire combinations to find the closest intersection to the origin
        // Also build a list of all intersections found, for use in Part B.

        int minDistance = Integer.MAX_VALUE;
        XYPoint minPoint = null;

        // Check all wire1 segments
        List<WireSegment> wire1 = wires.get(0);
        List<WireSegment> wire2 = wires.get(1);
        for (int i = 0; i < wire1.size(); i++) {
            // Check all wire2 segments against each wire1 segment
            for (int j = 0; j < wire2.size(); j++) {
                // Do these two segments intersect?
                XYPoint intersection = findIntersection(wire1.get(i), wire2.get(j));

                if (intersection != null && isNotOrigin(intersection)) {
                    // If there's an intersection
                    // Add it to the list of all intersections
                    allIntersections.add(intersection);

                    // Calculate its manhattan distance from the origin.
                    int manhattanDistance = manhattanDistance(intersection, reference);
                    if (manhattanDistance < minDistance) {
                        minDistance = manhattanDistance;
                        minPoint = intersection;
//                        System.out.println("New minimum distance:\twire1[" + i + "]" +
//                                "\twire2[" + j + "]" +
//                                "\t(" + minPoint.getX() + ", " + minPoint.getY() + ")" +
//                                "\tDistance: " + minDistance);
                    }
                }
            }
        }
        return minPoint;
    }

    public int findLeastIntersectionSteps() {
        // Iterate over all the intersection points found previously.
        // For each one, calculate the along-the-wires distance to get to it.
        // Find the smallest of those distances.
        int minDistance = Integer.MAX_VALUE;
        List<WireSegment> wire1 = wires.get(0);
        List<WireSegment> wire2 = wires.get(1);

        for (XYPoint intersection : allIntersections) {
            // Check each intersection
            int wire1Distance = wireDistanceToPoint(intersection, wire1);
            int wire2Distance = wireDistanceToPoint(intersection, wire2);

            int totalDistance = wire1Distance + wire2Distance;

            if (totalDistance < minDistance) {
//                System.out.println("New minimum distance:\t(" + intersection.getX() + ", " + intersection.getY() + ")" +
//                        "\tDistances: " + wire1Distance + " + " + wire2Distance + " = " + totalDistance);
                minDistance = totalDistance;

            }

        }
        return minDistance;
    }

    private void loadInputs(String pathToFile) {
        wires.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Each line of text is one wire
                // e.g. R1002,U407,R530,D268,R516,U937,L74
                Scanner scanner = new Scanner(line);
                Pattern pattern = Pattern.compile("([UDRL])(\\d+)");
                String match;

                ArrayList<WireSegment> wireSegments = new ArrayList<>();
                // Start at the origin and build out the list of segments in this wire.
                XYPoint p1 = ORIGIN;
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
        Integer lowestIntersection;
        if (isVertical(w1) && isVertical(w2)) {
            // If both are vertical
            if (w1.getP1().getX() != w2.getP1().getX()) {
                // and their x-coords aren't equal, then no intersection
                return NO_INTERSECTION;
            } else if ((lowestIntersection = overlapping(w1.getP1().getY(), w1.getP2().getY(), w2.getP1().getY(), w2.getP2().getY())) != null) {
                // if they overlap, return the closest overlap point
                return new XYPoint(w1.getP1().getX(), lowestIntersection);
            }
        } else if (isHorizontal(w1) && isHorizontal(w2)) {
            // If both are horizontal
            if (w1.getP1().getY() != w2.getP1().getY()) {
                // and their x-coords aren't equal, then no intersection
                return NO_INTERSECTION;
            } else if ((lowestIntersection = overlapping(w1.getP1().getX(), w1.getP2().getX(), w2.getP1().getX(), w2.getP2().getX())) != null) {
                // if they overlap, return the closest overlap point
                return new XYPoint(lowestIntersection, w1.getP1().getY());
            }
        } else {
            // The two segments are different orientations; let's see if they intersect!
            WireSegment hSegment;
            WireSegment vSegment;
            if (isHorizontal(w1)) {
                // w1 is horizontal, w2 is vertical
                hSegment = w1;
                vSegment = w2;
            } else {
                // w2 is vertical, w1 is horizontal
                hSegment = w2;
                vSegment = w1;
            }
            return orthoIntersection(hSegment, vSegment);
        }
        // If we get here, there's no intersection point
        return NO_INTERSECTION;
    }

    private XYPoint orthoIntersection(WireSegment hSegment, WireSegment vSegment) {
        // The two segments intersect if:
        //      The x-component of the vertical segment is in-range of the horizontal segment
        //      AND
        //      The y-component of the horizontal segment is in-range of the vertical segment

//        int hMin = Math.min(hSegment.getP1().getX(), hSegment.getP2().getX());
//        int hMax = Math.max(hSegment.getP1().getX(), hSegment.getP2().getX());
//        Range<Integer> hRange = Range.closed(hMin, hMax);
        Range<Integer> hRange = rangeFromWireSegment(hSegment);

//        int vMin = Math.min(vSegment.getP1().getY(), vSegment.getP2().getY());
//        int vMax = Math.max(vSegment.getP1().getY(), vSegment.getP2().getY());
//        Range<Integer> vRange = Range.closed(vMin, vMax);
        Range<Integer> vRange = rangeFromWireSegment(vSegment);

        if ((hRange.contains(vSegment.getP1().getX())) &&
                vRange.contains(hSegment.getP1().getY())) {
            // These segments intersect!
            // The intersection point is at the x-position of the *vertical* segment
            //  and the y-position of the *horizontal* segment
            XYPoint intersection = new XYPoint(vSegment.getP1().getX(), hSegment.getP1().getY());
            return intersection;
        } else {
            return NO_INTERSECTION;
        }
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
        Range<Integer> w1Range = Range.closed(Math.min(x1, x2), Math.max(x1, x2));  // Ranges must have the "lower" number first
        Range<Integer> w2Range = Range.closed(Math.min(x3, x4), Math.max(x3, x4));

        // Do these two segments overlap?
        Range<Integer> intersection = w1Range.intersection(w2Range);
        if (intersection != null) {
            // If the segments overlap, find the end of the overlap area
            // that is closest to zero (has the smallest absolute value)
            if (Math.abs(intersection.lowerEndpoint()) < Math.abs(intersection.upperEndpoint())) {
                return intersection.lowerEndpoint();
            } else {
                return intersection.upperEndpoint();
            }
        } else {
            return null;
        }
    }

    public int wireDistanceToPoint(XYPoint p1, List<WireSegment> wire) {
        // Walk along the given wire to find the specified point.
        int totalDistance = 0;
        for (WireSegment wireSegment : wire) {
            if (segmentContainsPoint(p1, wireSegment)) {
                // We found the segment that contains this intersection
                // The distance to the point is just the manhattan distance
                // from the current segment origin to the point.
                int partialDistanceX = manhattanDistance(p1, wireSegment.getP1());
                totalDistance += partialDistanceX;

                // Break out of the loop, since we found the intersection point
                break;
            } else {
                // This segment does not contain the target point,
                // so just accumulate the length and keep going
                totalDistance += wireSegment.getLength();
            }
        }
        return totalDistance;
    }

    private boolean segmentContainsPoint(XYPoint p1, WireSegment wireSegment) {
        Range<Integer> segmentRange = rangeFromWireSegment(wireSegment);
        // Does this segment contain the target point?
        boolean segmentContainsPoint = false;
        switch (wireSegment.getDirection()) {
            case UP:
            case DOWN:
                // Apply the range to the y-coord of the point
                segmentContainsPoint = ((wireSegment.getP1().getX() == p1.getX()) &&
                        (segmentRange.contains(p1.getY())));
                break;
            case LEFT:
            case RIGHT:
                // Apply the range to the x-coord of the point
                segmentContainsPoint = ((wireSegment.getP1().getY() == p1.getY()) &&
                        (segmentRange.contains(p1.getX())));
                break;
        }
        return segmentContainsPoint;
    }


    private boolean isNotOrigin(XYPoint p1) {
        return ((p1.getX() != ORIGIN.getX()) || (
                p1.getY() != ORIGIN.getY()));
    }

    private Range<Integer> rangeFromWireSegment(WireSegment wireSegment) {
        int endpt1 = 0;
        int endpt2 = 0;
        switch (wireSegment.getDirection()) {
            case UP:
            case DOWN:
                // Use the Y-coords of the segment to make the range
                endpt1 = wireSegment.getP1().getY();
                endpt2 = wireSegment.getP2().getY();
                break;
            case LEFT:
            case RIGHT:
                // Use the Y-coords of the segment to make the range
                endpt1 = wireSegment.getP1().getX();
                endpt2 = wireSegment.getP2().getX();
                break;
        }
        return Range.closed(Math.min(endpt1, endpt2), Math.max(endpt1, endpt2));  // Ranges must have the "lower" number first
    }

}
