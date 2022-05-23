package com.liner.twod_exp.ringfantasy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {

    private final int[] pixels;
    private final int width;
    private final int height;

    public Texture(BufferedImage bufferedImage) throws IOException {
        this.pixels = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null, 0, bufferedImage.getWidth());
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
    }

    public int getPixel(int u, int v) {
        return pixels[v * width + u];
    }

    public int getPixel(double u, double v) {
        return getPixel((int)(u * width) % width, (int)(v * height) % height);
    }

    public int getPixelWithFiltering(double u, double v) {
        u *= width;
        v *= height;
        int x = (int) Math.floor(u);
        int y = (int) Math.floor(v);
        double uRatio = u - x;
        double vRatio = v - y;
        double uOpposite = 1 - uRatio;
        double vOpposite = 1 - vRatio;

        // TODO optimize! do we really need to calculate each RGB component separately?
        Color c11 = new Color(getPixelClipping(x, y));
        Color c12 = new Color(getPixelClipping(x, y + 1));
        Color c21 = new Color(getPixelClipping(x + 1, y));
        Color c22 = new Color(getPixelClipping(x + 1, y + 1));

        int r = (int) ((c11.getRed() * uOpposite + c21.getRed() * uRatio) * vOpposite
                + (c12.getRed() * uOpposite + c22.getRed() * uRatio) * vRatio);
        int g = (int) ((c11.getGreen() * uOpposite + c21.getGreen() * uRatio) * vOpposite
                + (c12.getGreen() * uOpposite + c22.getGreen() * uRatio) * vRatio);
        int b = (int) ((c11.getBlue() * uOpposite + c21.getBlue() * uRatio) * vOpposite
                + (c12.getBlue() * uOpposite + c22.getBlue() * uRatio) * vRatio);

        Color texel = new Color(r, g, b, 255);
        return texel.getRGB();
    }

    private int getPixelClipping(int u, int v) {
        return getPixel(u % width, v % height);
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
