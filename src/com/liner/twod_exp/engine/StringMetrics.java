package com.liner.twod_exp.engine;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class StringMetrics {
    private final Font font;
    private final FontRenderContext context;

    public StringMetrics(Graphics2D g2) {
        font = g2.getFont();
        context = g2.getFontRenderContext();
    }

    public Rectangle2D getBounds(String test) {
        return font.getStringBounds(test, context);
    }

    public double getWidth(String test) {
        Rectangle2D bounds = getBounds(test);
        return bounds.getWidth();
    }

    public double getHeight(String test) {
        Rectangle2D bounds = getBounds(test);
        return bounds.getHeight();
    }
}
