package org.jgoeres.adventofcode2019.Day04;

import java.util.ArrayList;
import java.util.Scanner;

public class SecureContainerService {
    private final String XX = "04";
    private int DEFAULT_INPUT_LOWER_BOUND = 359282;
    private int DEFAULT_INPUT_UPPER_BOUND = 820401;

    private int lowerBound;
    private int upperBound;

    private int currentPassword;                        // e.g. 359282
    // Array to hold the 6 digits of the password value
    private int[] currentPasswordDigits = new int[6];
    // e.g. [ "3", "5", "9", "2", "8", "2" ]

    private ArrayList<int[]> knownValidPasswords = new ArrayList<>();

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
        // Put the digits of the password into the digits array.
        updateCurrentPasswordToDigits();
    }

    public void setCurrentPassword(int password) {
        currentPassword = password;
        updateCurrentPasswordToDigits();
    }

    public void findAllValid() {
        // Find all the valid passwords in the range of [lowerBound, upperBound]
        int candidate = lowerBound;
        while (candidate <= upperBound) {
            setCurrentPassword(candidate);
            if (isValid(candidate)) {
                // If this password IS valid
                // Store it
                // And add its digits to the list of known passwords
                knownValidPasswords.add(currentPasswordDigits.clone());
                // Then increment and keep going
                candidate++;
            } else {
                // candidate is NOT valid
                // so jump to the next valid password.
                // then continue
                candidate = findNextValid();
            }
            // There's a special case at '456789'
            // If that happens, just add 1 to get '456790' and continue
            if (candidate == 456789) {
                candidate++;
            }
        }
        // At this point the knownValidPasswords array should be filled with all valid passwords
    }

    private void updateCurrentPasswordToDigits() {
        Scanner s = new Scanner(Integer.toString(currentPassword)).useDelimiter("");
        int i = 0;
        while (s.hasNextInt() && i < currentPasswordDigits.length) {
            currentPasswordDigits[i] = s.nextInt();
            i++;
        }
        s.close();
    }

    public int findNextValid() {
        // If this password is already valid, just return it
        if (isValid(currentPassword)) return currentPassword;

        // Otherwise, jump to the next valid password.
        // To do that, scan forward through the password until we find a _decreased_ digit.
        // After finding that, fill the decreased digit and all subsequent digits with the previous one.
        //  e.g.    36035
        //          36666
        //            ^ set to 6 because it's the previous digit, then fill forward with more 6s.

        // Scan through the digits to find the decreasing one.
        int prevInt = -1;
        for (int i = 0; i < currentPasswordDigits.length; i++) {

            int currentInt = currentPasswordDigits[i];
            /******* Check for only increasing digits *******/
            if (currentInt < prevInt) {
                // If this digit is LESS than the previous
                // then fill from here to the end with copies of prevInt

                for (int j = i; j < currentPasswordDigits.length; j++) {
                    currentPasswordDigits[j] = prevInt;
                }
                break;  // Stop evaluating – we just jumped the digit array to the next valid password.
            }
            // Set current to previous and check the next digit
            prevInt = currentInt;
        }
        return getDigitArrayAsInt();
    }

    public int countValidPasswords() {
//        int validCount = 0;
//        // Iterate from the lower bound to the upper bound and count all the valid passwords
//        for (int i = lowerBound; i <= upperBound; i++) {
//            if (isValid(i)) {
//                validCount++;
//            }
//        }
//        return validCount;
        return knownValidPasswords.size();
    }

    public int countPart2ValidPasswords() {
        // Assume we already have the list of knownValidPasswords from Part 1
        // Iterate over all those passwords and count the ones that meet the new criteria:
        //   - the two adjacent matching digits are not part of a larger group of matching digits.

        int validPasswordCount = 0;
        for (int passwordDigits[] : knownValidPasswords) {
            if (hasGroupOfExactly2(passwordDigits)) {
                validPasswordCount++;
            }
        }
        return validPasswordCount;
    }

    public boolean hasGroupOfExactly2(int[] passwordDigits) {
        // Reset the counters
        int[] digitGroupCounters = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        int prevDigit = -1;
        int count = 1;
        for (int digit : passwordDigits) {
            if (digit == prevDigit) {
                // If this digit is the same as the previous one
                // Increment our count of the running digits
                count++;
            } else {
                // If this digit is NOT the same as the previous one
                // Store the count of the *previous digit* in the digit counter array

                // If it's anything but 2, store the new count.
                if (prevDigit == -1) {
                    // on the first digit, do nothing -- prevDigit is -1 anyway so it will blow up.
                } else if (digitGroupCounters[prevDigit] == 2) {
                    // If the count is EXACTLY 2, the 2 takes priority.
                    // Leave it in place
                } else {
                    digitGroupCounters[prevDigit] = count;
                }
                count = 1;  // reset the counter
            }
            prevDigit = digit;
        }
        // After we finish iterating, clean up the last count
        if (digitGroupCounters[prevDigit] == 2) {
            // If the count is EXACTLY 2, the 2 takes priority.
            // Leave it in place
        } else {
            digitGroupCounters[prevDigit] = count;
        }

        // Now check the digit counters to see if any of them had a group of exactly 2.
        for (int digitGroupCounter : digitGroupCounters) {
            if (digitGroupCounter == 2) {
                return true;
            }
        }
        return false;
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

    public int getDigitArrayAsInt() {
        // Reassemble the digits array into an int.
        int passwordInt = 0;
        int exp = currentPasswordDigits.length - 1;
        for (int digit : currentPasswordDigits) {
            // first array element is MSD, so start big and count backwards
            passwordInt += digit * (Math.pow(10, exp));
            exp--;
        }
        return passwordInt;
    }


}
