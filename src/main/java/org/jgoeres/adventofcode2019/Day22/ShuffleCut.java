package org.jgoeres.adventofcode2019.Day22;

public class ShuffleCut extends AbstractShuffle {
    int cutSize;

    public ShuffleCut(int cutSize, int deckSize) {
        this.cutSize = cutSize;
        this.deckSize = deckSize;
    }

    @Override
    public int newPositionOfCard(int currentPositionOfCard) {
        // Cutting moves N cards from the top of the deck to the bottom without changing their order.
        // In terms of the position of any given card, this means...
        int newPos = (currentPositionOfCard + deckSize - cutSize) % deckSize;
        return newPos;
    }

    @Override
    public String toString() {
        return "cut " + cutSize;
    }
}

