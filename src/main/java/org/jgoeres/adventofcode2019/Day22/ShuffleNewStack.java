package org.jgoeres.adventofcode2019.Day22;

public class ShuffleNewStack extends AbstractShuffle {

    public ShuffleNewStack(int deckSize) {
        this.deckSize = deckSize;
    }

    @Override
    public int newPositionOfCard(int currentPositionOfCard) {
        // Dealing into a new stack just reverses the order of the deck
        // so new position of any card is given by...
        int newPos = (deckSize - 1) - currentPositionOfCard;
        return newPos;
    }

    @Override
    public int oldPositionOfCard(int currentPositionOfCard) {
        // To undo a "new stack" shuffle is actually the same
        // as doing it in the first place. New stack is symmetrical
        int oldPos = newPositionOfCard(currentPositionOfCard);
        return oldPos;
    }

    @Override
    public String toString() {
        return "deal into new stack";
    }
}

