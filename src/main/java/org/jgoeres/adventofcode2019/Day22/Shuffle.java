package org.jgoeres.adventofcode2019.Day22;

public interface Shuffle {
    long newPositionOfCard(long currentPositionOfCard);

    long oldPositionOfCard(long currentPositionOfCard);
}
