package org.jgoeres.adventofcode2019.Day10;

import org.jgoeres.adventofcode2019.common.XYPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

public class AsteroidMonitorService {
    private final String XX = "10";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";

    private final char ASTEROID = '#';

    //    private HashSet<XYPoint> asteroids = new HashSet<>();
    private ArrayList<XYPoint> asteroids = new ArrayList<>();

    public AsteroidMonitorService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public AsteroidMonitorService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public int countVisibleAsteroidsFromPoint(XYPoint c) {
        ArrayList<XYPoint> visibleAsteroids = listVisibleAsteroidsFromPoint(c);
        return visibleAsteroids.size();
    }

    public AsteroidVisibleData findMaxVisibleAsteroids() {
        int maxVisible = Integer.MIN_VALUE;
        XYPoint maxVisibleAsteroid = null;
        // iterate over all the asteroids
        for (XYPoint asteroid : asteroids) {
            int visible = countVisibleAsteroidsFromPoint(asteroid);
            if (visible > maxVisible) {
                maxVisible = visible;
                maxVisibleAsteroid = asteroid;
//                System.out.println("New Max:\t" + maxVisibleAsteroid.toString() + "\t" + "can see " + maxVisible);
            }
        }
        return new AsteroidVisibleData(maxVisibleAsteroid, maxVisible);
    }

    public XYPoint vaporizeAsteroidsFromPoint(XYPoint c) {
        // Vaporize everything, return the last one
        XYPoint lastDestroyed = vaporizeAsteroidsFromPoint(c, Integer.MAX_VALUE);
        return lastDestroyed;
    }


    public XYPoint vaporizeAsteroidsFromPoint(XYPoint c, int limit) {
        // Before we start, sort the asteroids array by distance relative to our reference point.
        sortAsteroidsByDistanceToPoint(c);

        int i = 1;
        XYPoint lastDestroyed = null;
        // Remove our base from the asteroids list since we never blast it.
        asteroids.remove(c);
        while (!asteroids.isEmpty()) {
            // Keep going until we're out of asteroids (or see below to break when we hit the limit)
            // Get all the asteroids visible from point c.
            ArrayList<XYPoint> visibleAsteroids = listVisibleAsteroidsFromPoint(c);
            // Sort them by angle
            TreeSet<AsteroidAngle> sortedVisible = sortVisibleFromPoint(visibleAsteroids, c);
            // Iterate through those visible asteroids, removing them from the big list (vaporizing them!)
            for (AsteroidAngle target : sortedVisible) {
                XYPoint targetAsteroid = target.getAsteroid();
                if (asteroids.contains(targetAsteroid)) {
                    asteroids.remove(targetAsteroid);
                    lastDestroyed = targetAsteroid;
//                    System.out.println("Destroyed asteroid #" + i + " at " + lastDestroyed);
                    if (i >= limit) {
                        // If we've hit the limit of asteroids we want to destroy
                        return lastDestroyed;
                    } else {
                        // continue
                        i++;
                    }
                } else {
                    System.out.println("WARNING:\tAsteroid " + target.getAsteroid() + " not found in asteroids list.");
                }
            }
//            System.out.println("Round complete");
        }
        return lastDestroyed;
    }

    public void sortAsteroidsByDistanceToPoint(XYPoint c) {
        // Sort the 'asteroids' ArrayList by distance from point c
        Collections.sort(asteroids, new AsteroidDistanceComparator(c));
    }

    public ArrayList<XYPoint> listVisibleAsteroidsFromPoint(XYPoint c) {
        HashSet<AsteroidVector> visible = new HashSet<>();
        ArrayList<XYPoint> visibleAsteroids = new ArrayList<>();
        // Iterate through all the asteroids
        for (XYPoint asteroid : asteroids) {
            // Skip the one we're on
            if (asteroid.equals(c)) continue;

            // Calculate whether the view from c to the target is clear
            // Find the slope & quadrant from c to this asteroid
            int dX = asteroid.getX() - c.getX();
            int dY = asteroid.getY() - c.getY();

            String slope = asFraction(dY, dX);
            QuadrantEnum quadrant = Quadrant.quadrantFromXY(dX, dY);
            AsteroidVector asteroidVector = new AsteroidVector(slope, quadrant);

            // If so, add the target to the list of visible asteroids
            if (visible.add(asteroidVector)) {
                visibleAsteroids.add(asteroid);
            }
        }
        return visibleAsteroids;
    }

    public TreeSet<AsteroidAngle> sortVisibleFromPoint(ArrayList<XYPoint> asteroids, XYPoint c) {
        TreeSet<AsteroidAngle> sorted = new TreeSet<>(new AsteroidAngleComparator());
        for (XYPoint asteroid : asteroids) {
            double angle = angleFromPoint(asteroid, c);
            AsteroidAngle asteroidAngle = new AsteroidAngle(asteroid, angle);
            sorted.add(asteroidAngle);
        }
        return sorted;
    }

    public void listAllAngles(ArrayList<XYPoint> asteroids, XYPoint c) {
        for (XYPoint asteroid : asteroids) {
            System.out.println(asteroid.toString() + "\t" + angleFromPoint(asteroid, c));
        }
        return;
    }

    public double angleFromPoint(XYPoint p, XYPoint c) {
        XYPoint endpt = new XYPoint(p.getX() - c.getX(), p.getY() - c.getY());
        double angleInRadians = Math.atan2(endpt.getY(), endpt.getX());
        // Shift the range so that it runs from -PI/2 to 3*PI/2 (instead of -PI to +PI)
        if (angleInRadians < -Math.PI / 2) angleInRadians += 2 * Math.PI;
//        double angleInRadians = Math.atan2(endpt.getX(), endpt.getY());
        return angleInRadians;
    }

    /**
     * @return the greatest common denominator
     */
    public static long gcm(long a, long b) {
        return b == 0 ? a : gcm(b, a % b); // Not bad for one line of code :)
    }

    public static String asFraction(long a, long b) {
        long gcm = gcm(a, b);
        return (a / gcm) + "/" + (b / gcm);
    }

    public int calculateDay10BAnswerValue(XYPoint p) {
        // what do you get if you multiply its X coordinate by 100
        // and then add its Y coordinate? (For example, 8,2 becomes 802.)
        return (100 * p.getX() + p.getY());
    }

    private void loadInputs(String pathToFile) {
        asteroids.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                // process the line character by character
                for (int x = 0; x < line.length(); x++) {
                    // If this character is an asteroid
                    if (line.charAt(x) == ASTEROID) {
                        // Add an asteroid at this x,y point to the list
                        XYPoint newAsteroid = new XYPoint(x, y);
                        asteroids.add(newAsteroid);
                    }
                }
                y++;    // next line
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
