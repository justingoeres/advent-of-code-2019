package org.jgoeres.adventofcode2019.Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.math.BigInteger.ONE;

public class SpaceCardService {
    private final String DAY = "22";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private long deckSize;
    private final long DEFAULT_DECK_SIZE = 10007;

    private ArrayList<Shuffle> shuffles = new ArrayList<>();

    public SpaceCardService() {
        deckSize = DEFAULT_DECK_SIZE;
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public SpaceCardService(long deckSize) {
        this.deckSize = deckSize;
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public SpaceCardService(String pathToFile, int deckSize) {
        this.deckSize = deckSize;
        loadInputs(pathToFile);
    }

    public long doAllShuffles(long currentCardPosition) {
        // Execute all the shuffles on the given card and return its final position
        for (Shuffle shuffle : shuffles) {
            currentCardPosition = shuffle.newPositionOfCard(currentCardPosition);
        }
        return currentCardPosition;
    }

    public long undoAllShuffles(long finalCardPosition) {
        // Undo all the shuffles by un-applying them in reverse order
        // Return the original position of the specified card
        long previousCardPosition = finalCardPosition;
        ListIterator iterator = shuffles.listIterator(shuffles.size());
        while (iterator.hasPrevious()) {
            Shuffle shuffleToUndo = (Shuffle) iterator.previous();
            previousCardPosition = shuffleToUndo.oldPositionOfCard(previousCardPosition);
        }
        return previousCardPosition;
    }

    public BigInteger redditSolution(Long currentCardPosition, Long numberOfShuffles) {
        // Reference: https://www.reddit.com/r/adventofcode/comments/ee0rqi/2019_day_22_solutions/fbnifwk/
        long x = currentCardPosition;
        // Y = f(x) where f is the reversing function
        long Y = undoAllShuffles(x); // reverse once
        long Z = undoAllShuffles(Y);    // reverse twice
        // A = (Y-Z) * modinv(X-Y+D, D) % D
        // B = (Y-A*X) % D
        // Convert everything to BigInteger so things don't blow up
        BigInteger xBig = new BigInteger(String.valueOf(x));
        BigInteger yBig = new BigInteger(String.valueOf(Y));
        BigInteger zBig = new BigInteger(String.valueOf(Z));
        BigInteger deckBig = new BigInteger(String.valueOf(deckSize));

        BigInteger modInv = (xBig.subtract(yBig)).modInverse(deckBig);
        BigInteger ABig = (yBig.subtract(zBig)).multiply(modInv).mod(deckBig);
        BigInteger BBig = (yBig.subtract(ABig.multiply(xBig))).mod(deckBig);
//        System.out.println(ABig.toString() + "\t" + BBig.toString());

//        n = 101741582076661
//       print((pow(A, n, D)*X + (pow(A, n, D)-1) * modinv(A-1, D) * B) % D)

        BigInteger nBig = new BigInteger(numberOfShuffles.toString());
        BigInteger result = ((ABig.modPow(nBig, deckBig).multiply(xBig))
                .add((ABig.modPow(nBig, deckBig).subtract(ONE))
                        .multiply(ABig.subtract(ONE).modInverse(deckBig)).multiply(BBig))).mod(deckBig);
//        System.out.println(result);
        return result;
    }

    private void loadInputs(String pathToFile) {
        shuffles.clear();
        Pattern pCut = Pattern.compile("cut (-?\\d+)");
        Pattern pDealWithIncrement = Pattern.compile("deal with increment (\\d+)");
        Pattern pDealNewStack = Pattern.compile("deal into new stack");
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            // Each line is a shuffle command. Commands look like:
            //  cut -135
            //  deal with increment 38
            //  deal into new stack
            while ((line = br.readLine()) != null) {
                Matcher mCut = pCut.matcher(line);
                Matcher mDealWithIncrement = pDealWithIncrement.matcher(line);
                Matcher mDealNewStack = pDealNewStack.matcher(line);
                // process the line.
                Shuffle newShuffle = null;
                if (mCut.matches()) {
                    int cutSize = Integer.parseInt(mCut.group(1));
                    newShuffle = new ShuffleCut(cutSize, deckSize);
                } else if (mDealWithIncrement.matches()) {
                    int incrementSize = Integer.parseInt(mDealWithIncrement.group(1));
                    newShuffle = new ShuffleIncrement(incrementSize, deckSize);
                } else if (mDealNewStack.matches()) {
                    // Deal into new stack
                    newShuffle = new ShuffleNewStack(deckSize);
                }
                shuffles.add(newShuffle);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
