package com.liner.twod_exp.engine;

import java.awt.*;

public interface Renderable {
    void draw(Graphics2D graphics2D, int screenHeight, int screenWidth);
    void update(double delta);
}
