package org.jgoeres.adventofcode2019.Day14;

public class Reagent {
    private Long quantity;
    private String name;

    public Reagent(Long quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
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
