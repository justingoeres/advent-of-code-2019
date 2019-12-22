package org.jgoeres.adventofcode2019.Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpaceCardService {
    private final String DAY = "22";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";
    private int deckSize;
    private final int DEFAULT_DECK_SIZE = 10007;

    private ArrayList<Shuffle> shuffles = new ArrayList<>();

    public SpaceCardService() {
        deckSize = DEFAULT_DECK_SIZE;
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public SpaceCardService(String pathToFile, int deckSize) {
        this.deckSize = deckSize;
        loadInputs(pathToFile);
    }

    public int doAllShuffles(int currentCardPosition) {
        // Execute all the shuffles on the given card and return its final position
        for (Shuffle shuffle : shuffles) {
            currentCardPosition = shuffle.newPositionOfCard(currentCardPosition);
        }
        return currentCardPosition;
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
            Integer nextInt = 0;
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
