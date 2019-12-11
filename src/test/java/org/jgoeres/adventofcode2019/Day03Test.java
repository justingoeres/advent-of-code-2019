package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day03.Direction;
import org.jgoeres.adventofcode2019.Day03.RunDay03;
import org.jgoeres.adventofcode2019.Day03.WireSegment;
import org.jgoeres.adventofcode2019.Day03.WireService;
import org.jgoeres.adventofcode2019.common.XYPoint;
import org.junit.Assert;
import org.junit.Test;

import static org.jgoeres.adventofcode2019.Day03.RunDay03.ORIGIN;

public class Day03Test {
    static String XX = "XX";
    WireService wireService = new WireService();

    @Test
    public void TestVerticalSegmentOverlap() {
        XYPoint origin = new XYPoint(0, 0);
        XYPoint otherSegment = new XYPoint(0, 15);
        WireSegment w1 = new WireSegment(Direction.UP, 10, origin);
        WireSegment w2 = new WireSegment(Direction.DOWN, 10, otherSegment);

        XYPoint intersection = wireService.findIntersection(w1, w2);

        Assert.assertEquals(0, intersection.getX());
        Assert.assertEquals(5, intersection.getY());
    }

    @Test
    public void TestVerticalSegmentNonOverlap() {
        XYPoint origin = new XYPoint(1, 0);
        XYPoint otherSegment = new XYPoint(0, 15);
        WireSegment w1 = new WireSegment(Direction.UP, 10, origin);
        WireSegment w2 = new WireSegment(Direction.DOWN, 10, otherSegment);

        XYPoint intersection = wireService.findIntersection(w1, w2);

        // Non-intersections are null;
        Assert.assertNull(intersection);
    }

    @Test
    public void TestHorizontalSegmentOverlap() {
        XYPoint origin = new XYPoint(0, 0);
        XYPoint otherSegment = new XYPoint(-15, 0);
        WireSegment w1 = new WireSegment(Direction.LEFT, 10, origin);
        WireSegment w2 = new WireSegment(Direction.RIGHT, 10, otherSegment);

        XYPoint intersection = wireService.findIntersection(w1, w2);

        Assert.assertEquals(-5, intersection.getX());
        Assert.assertEquals(0, intersection.getY());
    }

    @Test
    public void TestHorizontalSegmentNonOverlap() {
        XYPoint origin = new XYPoint(0, 0);
        XYPoint otherSegment = new XYPoint(-15, 1);
        WireSegment w1 = new WireSegment(Direction.LEFT, 10, origin);
        WireSegment w2 = new WireSegment(Direction.RIGHT, 10, otherSegment);

        XYPoint intersection = wireService.findIntersection(w1, w2);

        // Non-intersections are null;
        Assert.assertNull(intersection);
    }

    @Test
    public void TestOrthogonalSegmentOverlap() {
        XYPoint hOrigin = new XYPoint(5, 3);
        WireSegment hSegment = new WireSegment(Direction.LEFT, 10, hOrigin);
        XYPoint vOrigin = new XYPoint(-1, 6);
        WireSegment vSegment = new WireSegment(Direction.DOWN, 10, vOrigin);

        XYPoint intersection = wireService.findIntersection(hSegment, vSegment);

        Assert.assertEquals(vOrigin.getX(), intersection.getX());
        Assert.assertEquals(hOrigin.getY(), intersection.getY());
    }

    @Test
    public void TestOrthogonalSegmentNonOverlap() {
        XYPoint hOrigin = new XYPoint(10, 3);
        WireSegment hSegment = new WireSegment(Direction.RIGHT, 10, hOrigin);
        XYPoint vOrigin = new XYPoint(-1, 6);
        WireSegment vSegment = new WireSegment(Direction.DOWN, 10, vOrigin);

        XYPoint intersection = wireService.findIntersection(hSegment, vSegment);

        // Non-intersections are null;
        Assert.assertNull(intersection);
    }

    @Test
    public void Day3AExample1() {
//        R75,D30,R83,U83,L12,D49,R71,U7,L72
//        U62,R66,U55,R34,D71,R55,D58,R83 = distance 159

        WireService wireServiceExample1 = new WireService("data/day03/example1.txt");
        XYPoint closestIntersection = wireServiceExample1.findClosestIntersection(ORIGIN);
        int result = wireServiceExample1.manhattanDistance(closestIntersection, ORIGIN);

        Assert.assertEquals(159, result);
    }

    @Test
    public void Day3AExample2() {
//        R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
//        U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = distance 135

        WireService wireServiceExample1 = new WireService("data/day03/example2.txt");
        XYPoint closestIntersection = wireServiceExample1.findClosestIntersection(ORIGIN);
        int result = wireServiceExample1.manhattanDistance(closestIntersection, ORIGIN);

        Assert.assertEquals(135, result);
    }

    @Test
    public void Day3A() {
        try {
            int result = RunDay03.problem3A();

            Assert.assertEquals(245, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day3BExample1() {
//        R75,D30,R83,U83,L12,D49,R71,U7,L72
//        U62,R66,U55,R34,D71,R55,D58,R83 = 610 steps

        WireService wireServiceExample1 = new WireService("data/day03/example1.txt");
        // Have to find the closest intersection first, because that populates the allIntersections list.
        wireServiceExample1.findClosestIntersection(ORIGIN);
        int result = wireServiceExample1.findLeastIntersectionSteps();

        Assert.assertEquals(610, result);
    }

    @Test
    public void Day3BExample2() {
//        R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
//        U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = 410 steps

        WireService wireServiceExample1 = new WireService("data/day03/example2.txt");
        // Have to find the closest intersection first, because that populates the allIntersections list.
        wireServiceExample1.findClosestIntersection(ORIGIN);
        int result = wireServiceExample1.findLeastIntersectionSteps();

        Assert.assertEquals(410, result);
    }

    @Test
    public void Day3B() {
        try {
            int result = RunDay03.problem3B();

            Assert.assertEquals(48262, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
