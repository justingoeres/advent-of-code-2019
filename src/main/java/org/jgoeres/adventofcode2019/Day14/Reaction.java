package org.jgoeres.adventofcode2019.Day14;

import java.util.ArrayList;

public class Reaction {
    Reagent product;
    ArrayList<Reagent> reagents;

    public Reaction(Reagent product, ArrayList<Reagent> reagents) {
        this.product = product;
        this.reagents = reagents;
    }

    public Reagent getProduct() {
        return product;
    }

    public ArrayList<Reagent> getReagents() {
        return reagents;
    }

    public String getName() {
        return product.getName();
    }
}
