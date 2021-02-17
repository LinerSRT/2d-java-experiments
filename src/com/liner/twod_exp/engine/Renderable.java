package com.liner.twod_exp.engine;

import java.awt.*;

public interface Renderable {
    void render(Graphics2D graphics2D);
    void tick(double delta);
}
