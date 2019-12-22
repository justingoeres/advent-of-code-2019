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
//        int newPos = (currentPositionOfCard + deckSize - cutSize) % deckSize;
        int newPos = Math.floorMod(currentPositionOfCard + deckSize - cutSize, deckSize);
        return newPos;
    }

    @Override
    public int oldPositionOfCard(int currentPositionOfCard) {
        // Undoing a is the same as cutting the opposite way with the "other" part of the deck
//        int oldPos = (currentPositionOfCard + cutSize) % deckSize;
        int oldPos = Math.floorMod((currentPositionOfCard + cutSize), deckSize);
        return oldPos;
    }

    @Override
    public String toString() {
        return "cut " + cutSize;
    }
}

