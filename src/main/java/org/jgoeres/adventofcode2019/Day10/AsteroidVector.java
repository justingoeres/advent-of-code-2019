package org.jgoeres.adventofcode2019.Day10;

import java.util.Objects;

public class AsteroidVector {
    String slopeAsFraction;
    QuadrantEnum quadrant;

    public AsteroidVector(String slopeAsFraction, QuadrantEnum quadrant) {
        this.slopeAsFraction = slopeAsFraction;
        this.quadrant = quadrant;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AsteroidVector))
            return false;
        if (obj == this)
            return true;
        // Two points are equivalent if they have the same slope & same Quadrant
        return (this.slopeAsFraction.equals(((AsteroidVector) obj).slopeAsFraction)
                && (this.quadrant.equals(((AsteroidVector) obj).quadrant)));
    }

    @Override
    public int hashCode() {
//        return (slopeAsFraction.hashCode() + quadrant.hashCode());
        return (Objects.hash(slopeAsFraction, quadrant));

    }

}

