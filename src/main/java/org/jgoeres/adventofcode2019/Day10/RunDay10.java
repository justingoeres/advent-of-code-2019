package org.jgoeres.adventofcode2019.Day10;

import org.jgoeres.adventofcode2019.common.XYPoint;

public abstract class RunDay10 {
    static String XX = "10";
    static String YY = XX;

    static String pathToInputs = "data/day" + YY + "/input.txt";

    static AsteroidMonitorService asteroidMonitorService = new AsteroidMonitorService();
    static AsteroidVisibleData maxVisible;
    static XYPoint maxVisibleAsteroid;

    public static int problem10A() {
        // Day 10A
        System.out.println("=== DAY " + XX + "A ===");

        maxVisible = asteroidMonitorService.findMaxVisibleAsteroids();
        int result = maxVisible.getNumVisible();
        maxVisibleAsteroid = maxVisible.getXyPoint();
        System.out.println("Day " + XX + "A: Asteroid " + maxVisibleAsteroid + " can see " + result + " other asteroids");

//        Day 10A: Asteroid 29, 28 can see 256 other asteroids
//        Time elapsed:	287 ms
        return result;
    }

    public static int problem10B() {
        // Day 10B
        System.out.println("=== DAY " + XX + "B ===");

        int limit = 200;
        XYPoint lastDestroyed = asteroidMonitorService.vaporizeAsteroidsFromPoint(maxVisibleAsteroid, limit);
        int result = asteroidMonitorService.calculateDay10BAnswerValue(lastDestroyed);

        System.out.println("Day " + XX + "B: " + limit + "th asteroid destroyed is at " + lastDestroyed + " ==> answer is " + result);

//        Day 10B: 200th asteroid destroyed is at 17, 7 ==> answer is 1707
//        Time elapsed:	36 ms

        return result;
    }

}
