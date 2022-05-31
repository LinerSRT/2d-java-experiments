package com.liner.twod_exp.ringfantasy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {
    private final int width;
    private final int height;
    private final int[] pixelRGB;

    public Texture(BufferedImage bufferedImage) {
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        pixelRGB = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelRGB[y * width + x] = bufferedImage.getRGB(x, y);
            }
        }
    }

    public int getRGB(int x, int y) {
        x = x % width;
        y = y % height;
        return pixelRGB[y * width + x];
    }
}
