package org.jgoeres.adventofcode2019;

import org.jgoeres.adventofcode2019.Day01.RunDay01;
import org.jgoeres.adventofcode2019.Day02.RunDay02;
import org.jgoeres.adventofcode2019.Day03.RunDay03;
import org.jgoeres.adventofcode2019.Day04.RunDay04;
import org.jgoeres.adventofcode2019.Day05.RunDay05;
import org.jgoeres.adventofcode2019.Day06.RunDay06;
import org.jgoeres.adventofcode2019.Day07.RunDay07;
import org.jgoeres.adventofcode2019.Day08.RunDay08;
import org.jgoeres.adventofcode2019.Day09.RunDay09;
import org.jgoeres.adventofcode2019.Day10.RunDay10;
import org.jgoeres.adventofcode2019.Day11.RunDay11;
import org.jgoeres.adventofcode2019.Day12.RunDay12;
import org.jgoeres.adventofcode2019.Day13.RunDay13;
import org.jgoeres.adventofcode2019.Day14.RunDay14;
import org.jgoeres.adventofcode2019.Day15.RunDay15;
import org.jgoeres.adventofcode2019.Day16.RunDay16;
import org.jgoeres.adventofcode2019.Day17.RunDay17;
import org.jgoeres.adventofcode2019.Day18.RunDay18;
import org.jgoeres.adventofcode2019.Day19.RunDay19;
import org.jgoeres.adventofcode2019.Day20.RunDay20;

/**
 * Hello world!
 */
public class App {
    static final boolean RUN_ALL = false;

    static final boolean RUN_DAY_1 = false;
    static final boolean RUN_DAY_2 = false;
    static final boolean RUN_DAY_3 = false;
    static final boolean RUN_DAY_4 = false;
    static final boolean RUN_DAY_5 = false;
    static final boolean RUN_DAY_6 = false;
    static final boolean RUN_DAY_7 = false;
    static final boolean RUN_DAY_8 = false;
    static final boolean RUN_DAY_9 = false;
    static final boolean RUN_DAY_10 = false;
    static final boolean RUN_DAY_11 = false;
    static final boolean RUN_DAY_12 = false;
    static final boolean RUN_DAY_13 = false;
    static final boolean RUN_DAY_14 = false;
    static final boolean RUN_DAY_15 = false;
    static final boolean RUN_DAY_16 = false;
    static final boolean RUN_DAY_17 = false;
    static final boolean RUN_DAY_18 = false;
    static final boolean RUN_DAY_19 = false;
    static final boolean RUN_DAY_20 = true;
    static final boolean RUN_DAY_21 = false;
    static final boolean RUN_DAY_22 = false;
    static final boolean RUN_DAY_23 = false;
    static final boolean RUN_DAY_24 = false;
    static final boolean RUN_DAY_25 = false;

    private static long startTime = 0L;
    private static long totalElapsed = 0L;

    public static void main(String[] args) {
        //https://adventofcode.com/2018/

        if (RUN_DAY_1 || RUN_ALL) {
//             Day 1A
            setStartTime();
            RunDay01.problem1A();
            printElapsedTime();

            blankLine();

            //             Day 1B
            setStartTime();
            RunDay01.problem1B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_2 || RUN_ALL) {
            setStartTime();
            RunDay02.problem2A();
            printElapsedTime();

            blankLine();

//            DAY 2B
            setStartTime();
            RunDay02.problem2B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_3 || RUN_ALL) {
            setStartTime();
            RunDay03.problem3A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay03.problem3B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_4 || RUN_ALL) {
            setStartTime();
            RunDay04.problem4A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay04.problem4B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_5 || RUN_ALL) {
            setStartTime();
            RunDay05.problem5A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay05.problem5B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_6 || RUN_ALL) {
            setStartTime();
            RunDay06.problem6A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay06.problem6B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_7 || RUN_ALL) {
            setStartTime();
            RunDay07.problem7A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay07.problem7B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_8 || RUN_ALL) {
            setStartTime();
            RunDay08.problem8A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay08.problem8B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_9 || RUN_ALL) {
            setStartTime();
            RunDay09.problem9A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay09.problem9B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_10 || RUN_ALL) {
            setStartTime();
            RunDay10.problem10A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay10.problem10B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_11 || RUN_ALL) {
            setStartTime();
            RunDay11.problem11A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay11.problem11B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_12 || RUN_ALL) {
            setStartTime();
            RunDay12.problem12A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay12.problem12B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_13 || RUN_ALL) {
            setStartTime();
            RunDay13.problem13A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay13.problem13B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_14 || RUN_ALL) {
            setStartTime();
            RunDay14.problem14A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay14.problem14B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_15 || RUN_ALL) {
            setStartTime();
            RunDay15.problem15A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay15.problem15B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_16 || RUN_ALL) {
            setStartTime();
            RunDay16.problem16A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay16.problem16B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_17 || RUN_ALL) {
            setStartTime();
            RunDay17.problem17A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay17.problem17B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_18 || RUN_ALL) {
            setStartTime();
            RunDay18.problem18A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay18.problem18B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_19 || RUN_ALL) {
            setStartTime();
            RunDay19.problem19A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay19.problem19B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_20 || RUN_ALL) {
            setStartTime();
            RunDay20.problem20A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay20.problem20B();
            printElapsedTime();

            blankLine();
        }

//        if (RUN_DAY_21 || RUN_ALL) {
//            setStartTime();
//            RunDay21.problem21A();
//            printElapsedTime();
//
//            blankLine();
//
//            setStartTime();
//            RunDay21.problem21B();
//            printElapsedTime();
//
//            blankLine();
//        }
//
//        if (RUN_DAY_22 || RUN_ALL) {
//            setStartTime();
//            RunDay22.problem22A();
//            printElapsedTime();
//
//            blankLine();
//
//            setStartTime();
//            RunDay22.problem22B();
//            printElapsedTime();
//
//            blankLine();
//        }
//
//        if (RUN_DAY_23 || RUN_ALL) {
//            setStartTime();
//            RunDay23.problem23A();
//            printElapsedTime();
//
//            blankLine();
//
//            setStartTime();
//            RunDay23.problem23B();
//            printElapsedTime();
//
//            blankLine();
//        }
//
//        if (RUN_DAY_24 || RUN_ALL) {
//            setStartTime();
//            RunDay24.problem24A();
//            printElapsedTime();
//
//            blankLine();
//
//            setStartTime();
//            RunDay24.problem24B();
//            printElapsedTime();
//
//            blankLine();
//        }
//
//        if (RUN_DAY_25 || RUN_ALL) {
//            setStartTime();
//            RunDay25.problem25A();
//            printElapsedTime();
//
//            blankLine();
//        }

        System.out.println("\n\nTOTAL ELAPSED TIME:\t" + totalElapsed + " ms");
    }

    private static void blankLine() {
        System.out.println();
    }

    private static void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    private static void printElapsedTime() {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        totalElapsed += elapsedTime;

        System.out.println("Time elapsed:\t" + elapsedTime + " ms");
    }
}


