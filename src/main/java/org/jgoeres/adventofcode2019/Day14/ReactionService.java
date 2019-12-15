package org.jgoeres.adventofcode2019.Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReactionService {
    private final String DAY = "14";
    private final String DEFAULT_INPUTS_PATH = "data/day" + DAY + "/input.txt";

    private HashMap<String, Reaction> reactionMap = new HashMap<>();    // Key is product name
    HashMap<String, Integer> totalRequirements = new HashMap<>(); // track the reagents needed, and how much of each

    HashMap<String, Integer> reagentsCreated = new HashMap<>();
    HashMap<String, Integer> reagentsAvailable = new HashMap<>();
    ArrayList<Reagent> reagentsRequired = new ArrayList<>();

    public ReactionService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public ReactionService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public void manufacture(String item, int quantityNeeded) {
        // This function creates the specified item
        // and all its required precursors, by calling itself recursively
        if (item.equals("ORE")) {
            // Ore gets manufactured by magic, and has no precursors, so just make it and return
            addToOre(quantityNeeded);
            return;
        }

        Reaction reaction = reactionMap.get(item);

        // Do we already have enough of 'item'?
        int amountAvailable = getAmountAvailable(item);
        if (amountAvailable > 0) {
            // If we have some available, use it up first
            int availableUsed = Math.min(quantityNeeded, amountAvailable);    // Take only what we need, or all of it if there's not enough.
            quantityNeeded -= availableUsed;
            subtractFromAvailable(item, availableUsed);
        }
        if (quantityNeeded > 0) {
            // If we still need more of item after using up what's available
            // Manufacture some
            // What is the manufacturing increment of item?
            int mfgIncrement = reaction.getProduct().getQuantity();
            // How many increments do we need to make?
            int incrementsRequired = Math.toIntExact((long) Math.ceil(quantityNeeded * 1.0 / mfgIncrement));
            // So we have to make *this much* of item...
            int amountToManufacture = incrementsRequired * mfgIncrement;
            // What are the precursors of this item?
            ArrayList<Reagent> precursors = reaction.getReagents();
            // Make enough of each precursor to fulfill our need for item
            for (Reagent reagent : precursors) {
                // The precursor amounts in the reaction are per *increment* of the item we want
                // So make the number of increments * amount of precursor per increment
                String precursorItem = reagent.getName();
                int precursorQuantityNeeded = reagent.getQuantity() * incrementsRequired;
                // manufacture it!
                manufacture(precursorItem, precursorQuantityNeeded);
            }
            // Once we get here, we have manufactured enough of the precursors of this item to
            // account for what we need to make, so add the amount we made to what's available
            addToAvailable(item, amountToManufacture);
            // and subtract out what we needed to use in the first place
            subtractFromAvailable(item, quantityNeeded);
            // And we're done?
            return;
        }
    }

    public int getAmountAvailable(String item) {
        if (reagentsAvailable.containsKey(item)) {
            // return the quantity available
            return reagentsAvailable.get(item);
        } else {
            // if not found, then we just haven't made any yet.
            // so return zero
            return 0;
        }
    }

    public void subtractFromAvailable(String item, int numberToSubtract) {
        if (reagentsAvailable.containsKey(item)) {
            // subtract the specified number from what's available
            int amountAvailable = getAmountAvailable(item);
            int newAmount = amountAvailable - numberToSubtract;
            reagentsAvailable.put(item, newAmount);
            return;
        } else {
            // if the list of available items doesn't have any of this
            // just do nothing and return.
            // (this should never happen)
            return;
        }
    }

    public void addToAvailable(String item, int numberToAdd) {
        int amountAvailable = 0;
        if (reagentsAvailable.containsKey(item)) {
            // add the specified number to what's available
            amountAvailable = getAmountAvailable(item);
        }
        int newAmount = amountAvailable + numberToAdd;
        reagentsAvailable.put(item, newAmount);

    }

    public int getOreCreated() {
        String item = "ORE";
        int oreCreated = 0;
        if (reagentsCreated.containsKey(item)) {
            oreCreated = reagentsCreated.get(item);
        }
        return oreCreated;
    }

    public void addToOre(int numberToAdd) {
        String item = "ORE";
        int amountAvailable = 0;
        if (reagentsCreated.containsKey(item)) {
            amountAvailable = reagentsCreated.get(item);
        }
        int newAmount = amountAvailable + numberToAdd;
        reagentsCreated.put(item, newAmount);
        return;
    }

    private void loadInputs(String pathToFile) {
        // e.g.
        //  2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
        //  17 NVRVD, 3 JNWZP => 8 VPVL
        //  53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
        reactionMap.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                String[] sides = line.split("=>");
//                nextInt = Integer.parseInt(line);
                String[] reagentsStrings = sides[0].split(",");
                ArrayList<Reagent> reagents = new ArrayList<>();
                for (String reagentString : reagentsStrings) {
                    reagents.add(reagentFromString(reagentString));
                }
                String productString = sides[1];
                Reagent product = reagentFromString(productString);

                // Create a reaction from these reagents and product
                Reaction reaction = new Reaction(product, reagents);
                // Stick it in the reactions map with the product name as the key
                reactionMap.put(reaction.getName(), reaction);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private Reagent reagentFromString(String itemString) {
        // Parse the amount and symbol of this item
        //  e.g. 2 VPVL
        Pattern p = Pattern.compile("(\\d+) (\\w+)");
        Matcher m = p.matcher(itemString);
        Reagent reagent = null;
        if (m.find()) {
            // If this item matches the pattern (it always should!)
            // Make it into a reagent
            int quantity = Integer.parseInt(m.group(1));
            String name = m.group(2);
            reagent = new Reagent(quantity, name);
        }
        return reagent;
    }
}
