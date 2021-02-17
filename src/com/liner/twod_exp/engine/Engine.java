package com.liner.twod_exp.engine;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Engine {
    protected Engine() {
        init();
        new Renderer<>(this);
    }

    public abstract void init();

    public abstract void render(Graphics2D graphics2D);

    public abstract void tick(double updatesPerSecond);

    public abstract void keyPressed(KeyEvent keyEvent);

    public abstract EngineConfig getConfig();
}
