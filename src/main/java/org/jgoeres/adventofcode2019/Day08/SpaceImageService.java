package org.jgoeres.adventofcode2019.Day08;

import java.io.BufferedReader;
import java.io.FileReader;

public class SpaceImageService {
    private final String XX = "08";
    private final String DEFAULT_INPUTS_PATH = "data/day" + XX + "/input.txt";
    private final int IMAGE_WIDTH = 25;
    private final int IMAGE_HEIGHT = 6;
    private int numLayers = 0;

    char[][][] imageData = null;

    private char BLACK = '0';
    private char WHITE = '1';
    private char TRANSPARENT = '2';
    private char SPACE = ' ';
    private char ASTERISK = '*';
    private char PIPE = '|';

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

    private char calculatePixelColor(int x, int y) {
        // Drill down the layers of this pixel until we find one that is not transparent
        int layer = 0;
        char pixelColor;
        while ((pixelColor = imageData[x][y][layer]) == TRANSPARENT) {
            layer++;
        }
        // When we get here, the current pixel will be BLACK or WHITE
        return pixelColor;
    }

    public void renderFinalImage() {
        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                // calculate the color of each pixel and render it out
                char pixel = calculatePixelColor(x, y);
                pixel = (pixel == BLACK) ? SPACE : PIPE;
                System.out.printf("%s", pixel);
            }
            // start a new line
            System.out.printf("\n");
        }
    }


    private void loadInputs(String pathToFile) {
//        Images are sent as a series of digits that each represent the color of a single pixel.
//        The digits fill each row of the image left-to-right, then move downward to the next row,
//        filling rows top-to-bottom until every pixel of the image is filled.
//
//         The image you received is 25 pixels wide and 6 pixels tall.
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            // There's only one line in this input file
            line = br.readLine();

            // Initialize the imageData structure.
            int inputLength = line.length();
            numLayers = inputLength / (IMAGE_HEIGHT * IMAGE_WIDTH);
            imageData = new char[IMAGE_WIDTH][IMAGE_HEIGHT][numLayers];

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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
