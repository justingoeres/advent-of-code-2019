package org.jgoeres.adventofcode2019.Day22;

public class ShuffleIncrement extends AbstractShuffle {
    int incSize;

    public ShuffleIncrement(int incSize, int deckSize) {
        this.incSize = incSize;
        this.deckSize = deckSize;
    }

    @Override
    public int newPositionOfCard(int currentPositionOfCard) {
        // Dealing by an increment of incSize mean that
        // the new position of any card is given by...
        int newPos = (currentPositionOfCard * incSize) % deckSize;
        return newPos;
    }

    @Override
    public String toString() {
        return "deal with increment " + incSize;
    }
}

