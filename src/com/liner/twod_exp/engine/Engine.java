package com.liner.twod_exp.engine;

import java.awt.*;

public abstract class Engine {
    public abstract void draw(Graphics2D graphics2D);

    public abstract void tick(double delta);

    public abstract EngineConfig getConfig();
}
