package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day03.Direction;
import org.jgoeres.adventofcode2019.Day03.RunDay03;
import org.jgoeres.adventofcode2019.Day03.WireSegment;
import org.jgoeres.adventofcode2019.Day03.WireService;
import org.jgoeres.adventofcode2019.Day03.XYPoint;
import org.jgoeres.adventofcode2019.DayXX.RunDayXX;
import org.junit.Assert;
import org.junit.Test;

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
    public void Day3A() {
        try {
            int result = RunDay03.problem3A();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Day3B() {
        try {
            int result = RunDay03.problem3B();

            Assert.assertEquals(0, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
