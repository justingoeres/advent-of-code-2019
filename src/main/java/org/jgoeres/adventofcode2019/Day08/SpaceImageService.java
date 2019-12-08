package org.jgoeres.adventofcode2019.Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class SpaceImageService {
    private final String XX = "08";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";
    private final int IMAGE_WIDTH = 25;
    private final int IMAGE_HEIGHT = 6;
    private int numLayers = 0;

    int[][][] imageData = null;

    private ArrayList<Integer> inputList = new ArrayList<>();

    public SpaceImageService() {
        loadInputs(DEFAULT_INPUTS_PATH);
    }

    public SpaceImageService(String pathToFile) {
        loadInputs(pathToFile);
    }

    public int countInLayer(char element, int layer) {
        int count = 0;
        for (int x = 0; x < IMAGE_WIDTH; x++) {
            for (int y = 0; y < IMAGE_HEIGHT; y++) {
                if (imageData[x][y][layer] == element) count++;
            }
        }
        return count;
    }

    public int findLayerWithFewest(char element) {
        int minCount = Integer.MAX_VALUE;
        int minLayer = 0;
        for (int layer = 0; layer < numLayers; layer++) {
            int count = 0;
            // Check all the layers
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                for (int y = 0; y < IMAGE_HEIGHT; y++) {
                    if (imageData[x][y][layer] == element) count++;
                }
                if (count > minCount) {
                    // If this layer has more than the minimum we've found so far, bail out
                    break;
                }
            }
            // If this layer is a new minimum, track it
            if (count < minCount) {
                minCount = count;
                minLayer = layer;
            }
        }
        return minLayer;
    }

    private void loadInputs(String pathToFile) {
//        Images are sent as a series of digits that each represent the color of a single pixel.
//        The digits fill each row of the image left-to-right, then move downward to the next row,
//        filling rows top-to-bottom until every pixel of the image is filled.
//
//         The image you received is 25 pixels wide and 6 pixels tall.
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
//            while ((line = br.readLine()) != null) {
            // There's only one line in this input file
            line = br.readLine();

            // Initialize the imageData structure.
            int inputLength = line.length();
            numLayers = inputLength / (IMAGE_HEIGHT * IMAGE_WIDTH);
            imageData = new int[IMAGE_WIDTH][IMAGE_HEIGHT][numLayers];

            // Read all the pixels into the image structure
            int i = 0;
            for (int z = 0; z < numLayers; z++) {
                for (int y = 0; y < IMAGE_HEIGHT; y++) {
                    for (int x = 0; x < IMAGE_WIDTH; x++) {
                        imageData[x][y][z] = line.charAt(i);
//                        System.out.println(imageData[x][y][z]);
                        i++;
                    }
                }
            }


            inputList.add(nextInt);

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
