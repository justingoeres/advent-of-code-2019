package org.jgoeres.adventofcode2019.Day14;

public class Reagent {
    private int quantity;
    private String name;

    public Reagent(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return quantity + " " + name;
    }
}
