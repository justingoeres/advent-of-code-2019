package org.jgoeres.adventofcode2019.Day06;

public class Orbiter {
    Orbiter parentOrbit;

    // Create orbiter with no parent
    public Orbiter() {
    }

    // Create orbiter with parent
    public Orbiter(Orbiter parentOrbit) {
        this.parentOrbit = parentOrbit;
    }

    public void setParentOrbit(Orbiter parentOrbit) {
        this.parentOrbit = parentOrbit;
    }

    public Orbiter getParentOrbit() {
        return parentOrbit;
    }

}
