package org.jgoeres.adventofcode2019.Day22;

public class ShuffleNewStack extends AbstractShuffle {

    public ShuffleNewStack(long deckSize) {
        this.deckSize = deckSize;
    }

    @Override
    public long newPositionOfCard(long currentPositionOfCard) {
        // Dealing into a new stack just reverses the order of the deck
        // so new position of any card is given by...
        long newPos = (deckSize - 1) - currentPositionOfCard;
        return newPos;
    }

    @Override
    public long oldPositionOfCard(long currentPositionOfCard) {
        // To undo a "new stack" shuffle is actually the same
        // as doing it in the first place. New stack is symmetrical
        long oldPos = newPositionOfCard(currentPositionOfCard);
        return oldPos;
    }

    @Override
    public String toString() {
        return "deal into new stack";
    }
}

