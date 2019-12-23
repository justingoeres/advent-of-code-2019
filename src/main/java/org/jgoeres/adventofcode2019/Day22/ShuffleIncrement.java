package org.jgoeres.adventofcode2019.Day22;

public class ShuffleIncrement extends AbstractShuffle {
    int incSize;

    public ShuffleIncrement(int incSize, long deckSize) {
        this.incSize = incSize;
        this.deckSize = deckSize;
    }

    @Override
    public long newPositionOfCard(long currentPositionOfCard) {
        // Dealing by an increment of incSize mean that
        // the new position of any card is given by...
//        int newPos = (currentPositionOfCard * incSize) % deckSize;
        long newPos = Math.floorMod(currentPositionOfCard * incSize, deckSize);
        return newPos;
    }

    @Override
    public long oldPositionOfCard(long currentPositionOfCard) {
        // Undoing a deal by increment means that
        // the old position of a card is given by...
//        int oldPos = (-1 * incSize * currentPositionOfCard) % deckSize;
//        int oldPos = (-1 * incSize * currentPositionOfCard) % deckSize;
//        if (oldPos < 0) oldPos = Math.abs(oldPos);
////                int oldPos = Math.floorMod(-1 * incSize * currentPositionOfCard, deckSize);
//
//        // Find the old position by adding up multiples of 'increment'
//        // until we get currentPos as a remainder?
//        int i = 0;
//        while ((i % deckSize) != currentPositionOfCard) {
//            i += incSize;
//        }
//        System.out.println(i);

        // We only have the remainder of the raw new position from the original shuffle
        // To get the entire raw "new position" we need to find a where
        //  (a * deckSize + new Pos) % incSize == 0;
        // Then
        //  oldPos = (a * deckSize + newPos) / incSize;
        long rawCurrentPositionOfCard = 0;
        for (int a = 0; a < incSize; a++) {
            rawCurrentPositionOfCard = a * deckSize + currentPositionOfCard;
            if ((rawCurrentPositionOfCard) % incSize == 0) {
                break;
            }
        }
        long oldPositionOfCard = rawCurrentPositionOfCard / incSize;

        return oldPositionOfCard;
    }

    @Override
    public String toString() {
        return "deal with increment " + incSize;
    }
}

