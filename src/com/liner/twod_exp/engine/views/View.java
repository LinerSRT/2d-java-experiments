package com.liner.twod_exp.engine.views;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class View {
    protected int width;
    protected int height;
    protected int positionX;
    protected int positionY;
    private boolean measured;

    public View(int positionX, int positionY) {
        this.width = 0;
        this.height = 0;
        this.positionX = positionX;
        this.positionY = positionY;
        this.measured = false;
    }

    public void drawInternal(ECore core){
        Graphics2D graphics2D = core.getGraphics();
        if(!measured){
            onMeasure(graphics2D);
            measured = true;
        }
        AffineTransform transform = graphics2D.getTransform();
        graphics2D.translate(positionX, positionY);
        onDraw(graphics2D);
        graphics2D.setTransform(transform);
    }
    public abstract void onDraw(Graphics2D graphics2D);
    public abstract void onMeasure(Graphics2D graphics2D);
    public void requestMeasure(){
        measured = false;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
