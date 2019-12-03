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

    @Test
    public void TestVerticalSegmentOverlap() {
        WireService wireService = new WireService();
        XYPoint origin = new XYPoint(0, 0);
        XYPoint otherSegment = new XYPoint(0, 15);
        WireSegment w1 = new WireSegment(Direction.UP, 10, origin);
        WireSegment w2 = new WireSegment(Direction.DOWN, 10, otherSegment);

        XYPoint intersection = wireService.findIntersection(w1, w2);

        Assert.assertEquals(0,intersection.getX() );
        Assert.assertEquals(5,intersection.getY() );
    }

    @Test
    public void TestHorizontalSegmentOverlap() {
        WireService wireService = new WireService();
        XYPoint origin = new XYPoint(0, 0);
        XYPoint otherSegment = new XYPoint(-15, 0);
        WireSegment w1 = new WireSegment(Direction.LEFT, 10, origin);
        WireSegment w2 = new WireSegment(Direction.RIGHT, 10, otherSegment);

        XYPoint intersection = wireService.findIntersection(w1, w2);

        Assert.assertEquals(-5,intersection.getX() );
        Assert.assertEquals(0,intersection.getY() );
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
