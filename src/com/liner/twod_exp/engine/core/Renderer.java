package com.liner.twod_exp.engine.core;

import java.awt.event.*;

public abstract class Renderer implements InputListener{
    private final String identifier;

    public Renderer(String identifier) {
        this.identifier = identifier;
    }

    public abstract void render(ECore eCore);

    public abstract void tick(ECore eCore, long frameTime);

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void keyPress(ECore eCore, int keyCode) {

    }

    @Override
    public void mousePress(ECore eCore, int keyCode) {

    }

    @Override
    public void keyRelease(ECore eCore, int keyCode) {

    }

    @Override
    public void mouseRelease(ECore eCore, int keyCode) {

    }

    @Override
    public void mouseClick(ECore eCore, int keyCode) {

    }

    @Override
    public void mouseMove(ECore eCore, double x, double y) {

    }

    @Override
    public void mouseDrag(ECore eCore, double x, double y) {

    }

    @Override
    public void mouseWheelMove(ECore eCore, int direction, double amount) {

    }
}
