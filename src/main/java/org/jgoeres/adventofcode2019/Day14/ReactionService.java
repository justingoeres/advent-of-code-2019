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

    public ReactionService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public ReactionService(String pathToFile) {
        loadInputs(pathToFile);
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
