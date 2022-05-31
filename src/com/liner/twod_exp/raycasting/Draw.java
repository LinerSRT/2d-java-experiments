package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.math.MathUtils;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Draw {
    public static void clear(BufferedImage bufferedImage) {
        clear(bufferedImage, 0);
    }

    public static void clear(BufferedImage bufferedImage, int color) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                bufferedImage.setRGB(x, y, color);
            }
        }
    }

    public static void fill(BufferedImage bufferedImage, int color) {
        clear(bufferedImage, color);
    }

    public static void fill(BufferedImage bufferedImage, Vector2 start, Vector2 end, int color) {
        for (int y = (int) start.y; y < end.y; y++) {
            if (y < 0 || y >= bufferedImage.getHeight()) continue;
            for (int x = (int) start.x; x < end.x; x++) {
                if (x < 0 || x >= bufferedImage.getWidth()) continue;
                bufferedImage.setRGB(x, y, color);
            }
        }
    }

    public static void fill(BufferedImage bufferedImage, int x1, int y1, int x2, int y2, int color) {
        for (int y = y1; y < y2; y++) {
            if (y < 0 || y >= bufferedImage.getHeight()) continue;
            for (int x = x1; x < x2; x++) {
                if (x < 0 || x >= bufferedImage.getWidth()) continue;
                bufferedImage.setRGB(x, y, color);
            }
        }
    }

    public static void fill(BufferedImage bufferedImage, double x1, double y1, double x2, double y2, int color) {
        for (int y = (int) y1; y < y2; y++) {
            if (y < 0 || y >= bufferedImage.getHeight()) continue;
            for (int x = (int) x1; x < x2; x++) {
                if (x < 0 || x >= bufferedImage.getWidth()) continue;
                bufferedImage.setRGB(x, y, color);
            }
        }
    }

    public static int blendColorBand(int color, int colorB, double density) {
        double difColor = color - colorB;
        return (int) ((difColor / 100) * density) + colorB;
    }


    public static int blendColor(int color, int colorB, double density) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color) & 0xFF;
        int rb = (colorB >> 16) & 0xFF;
        int gb = (colorB >> 8) & 0xFF;
        int bb = (colorB) & 0xFF;

        r = blendColorBand(r, rb, density);
        g = blendColorBand(g, gb, density);
        b = blendColorBand(b, bb, density);

        return r << 16 | g << 8 | b;
    }




    public static int manipulateColor(int color, float factor) {
        int a = (color >> 24) & 0xff;
        int r = (color >> 16) & 0xff;
        int g = (color >> 8) & 0xff;
        int b = (color) & 0xff;
        int newAlpha = Math.max((int)(a * factor), 0);
        int newRed = Math.max((int)(r * factor), 0);
        int newGreen = Math.max((int)(g * factor), 0);
        int newBlue = Math.max((int)(b * factor), 0);
        return ((newAlpha & 0xFF) << 24) |
                ((newRed & 0xFF) << 16) |
                ((newGreen & 0xFF) << 8)  |
                ((newBlue & 0xFF));

    }

    public static int fadeToBlack(int color, double current, double max) {
        if (current < 0) {
            return color;
        }
        if (current >= max) {
            return Color.BLACK.getRGB();
        }
        Color c = new Color(color);
        double amount = (max - current) / max;
        int r = (int) (c.getRed() * amount);
        int g = (int) (c.getGreen() * amount);
        int b = (int) (c.getBlue() * amount);
        return new Color(r, g, b).getRGB();
    }
}
