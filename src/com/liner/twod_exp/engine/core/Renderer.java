package com.liner.twod_exp.engine.core;

import java.awt.*;
import java.awt.event.*;

public abstract class Renderer implements InputListener{
    private final String identifier;
    protected ECore core;

    public Renderer() {
        this.identifier = Renderer.this.getClass().getSimpleName();
    }

    public void setCore(ECore core) {
        this.core = core;
        create();
    }

    public abstract void create();
    public abstract void render(ECore core);

    public abstract void tick(ECore core, long frameTime);

    public abstract int getWindowWidth();
    public abstract int getWindowHeight();


    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void keyPress(ECore core, int keyCode) {

    }

    @Override
    public void mousePress(ECore core, int keyCode) {

    }

    @Override
    public void keyRelease(ECore core, int keyCode) {

    }

    @Override
    public void mouseRelease(ECore core, int keyCode) {

    }

    @Override
    public void mouseClick(ECore core, int keyCode) {

    }

    @Override
    public void mouseMove(ECore core, double x, double y) {

    }

    @Override
    public void mouseDrag(ECore core, double x, double y) {

    }

    @Override
    public void mouseWheelMove(ECore core, int direction, double amount) {

    }

    public static void setLocation(int x, int y){
        Engine.setLocation(x, y);
    }

    public static <R extends Renderer> void start(R renderer) {
        start(renderer, 1);
    }
    public static <R extends Renderer> void start(R renderer, float scale) {
        Engine.init((int) (renderer.getWindowWidth()), (int) (renderer.getWindowHeight()), scale);
        Engine.register(renderer);
        Engine.show(renderer);
        Engine.displayWindow(renderer.getIdentifier());
        Engine.start();
        Engine.setScale(scale);
    }
}
