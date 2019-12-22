package org.jgoeres.adventofcode2019.Day22;

public interface Shuffle {
    int newPositionOfCard(int currentPositionOfCard);

    int oldPositionOfCard(int currentPositionOfCard);
}
