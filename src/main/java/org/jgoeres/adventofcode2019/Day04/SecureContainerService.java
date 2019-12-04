package org.jgoeres.adventofcode2019.Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SecureContainerService {
    private final String XX = "04";
    private int DEFAULT_INPUT_LOWER_BOUND = 359282;
    private int DEFAULT_INPUT_UPPER_BOUND = 820401;

    private int lowerBound;
    private int upperBound;

    private int currentPassword;

    private ArrayList<Integer> inputList = new ArrayList<>();

    public SecureContainerService() {
        lowerBound = DEFAULT_INPUT_LOWER_BOUND;
        upperBound = DEFAULT_INPUT_UPPER_BOUND;
        reset();
    }

    public SecureContainerService(int lowerBound, int upperBound) {
        lowerBound = lowerBound;
        upperBound = upperBound;
        reset();
    }

    public void reset() {
        currentPassword = lowerBound;
    }

    public int findNextValid(int password) {
        // If this password is already valid, just return it
        if (isValid(password)) return password;

        // Otherwise, jump to the next valid password.
        // To do that, scan forward through the password until we find a _decreased_ digit.
        // After finding that, fill the decreased digit and all subsequent digits with the previous one.
        //  e.g.    36035
        //          36666
        //            ^ set to 6 because it's the previous digit, then fill forward with more 6s.

        Scanner s = new Scanner(Integer.toString(password)).useDelimiter("");
        // Scan through the digits to find the decreasing one.
        int prevInt = -1;
        int digitPosition = 0;
        while (s.hasNextInt()) {
            int currentInt = s.nextInt();
            /******* Check for only increasing digits *******/
            if (currentInt < prevInt) {
                // If this digit is LESS than the previous
                break; // once we find a decreasing digit, we can stop checking
            }
            // Set current to previous and check the next one
            prevInt = currentInt;
            digitPosition++;
        }

        s.close();
        return 0;   //FIXME
    }

    public int countValidPasswords() {
        int validCount = 0;
        // Iterate from the lower bound to the upper bound and count all the valid passwords
        for (int i = lowerBound; i <= upperBound; i++) {
            if (isValid(i)) {
                validCount++;
            }
        }
        return validCount;
    }

    public boolean isValid(int password) {
        // Requirements:
        //  Two adjacent digits are the same (like 22 in 122345).
        //  Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).

        boolean hasDouble = false;
        boolean hasIncreasingOnly = true;   // Assume we have increasing-only, and look for the exception to that.

        Scanner s = new Scanner(Integer.toString(password)).useDelimiter("");
        // Scan through the digits to verify that there is a pair.
        int prevInt = -1;
        while (s.hasNextInt()) {
            int currentInt = s.nextInt();
            /******* Check for a double *******/
            if (!hasDouble && currentInt == prevInt) {
                // If this digit is part of a pair
                // We're done, set the flag.
                hasDouble = true;
            }
            /******* Check for only increasing digits *******/
            if (hasIncreasingOnly && currentInt < prevInt) {
                // If this digit is LESS than the previous
                hasIncreasingOnly = false;
                break; // once we find a decreasing digit, we can stop checking
            }
            // Set current to previous and check the next one
            prevInt = currentInt;
        }
        s.close();
        // When we get here, we are done scanning. Either we found a double (continue) or we didn't (return invalid)
        return (hasDouble && hasIncreasingOnly);
    }

}
