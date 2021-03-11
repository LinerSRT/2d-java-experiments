package com.liner.twod_exp.ray_casting;

import com.liner.twod_exp.engine.Renderable;
import com.liner.twod_exp.engine.primitives.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public abstract class Camera implements Renderable, ICamera {
    protected double screenWidth;
    protected double screenHeight;
    protected double cameraWidth;
    protected double cameraHeight;
    protected double scaleWidth;
    protected double scaleHeight;
    protected Vector2 cameraPosition;
    protected RayCast rayCast;
    private AffineTransform transformation;

    public Camera(int screenWidth, int screenHeight, int cameraWidth, int cameraHeight, Vector2 cameraPosition, RayCast rayCast) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.cameraPosition = cameraPosition;
        this.scaleWidth = (double) cameraWidth / screenWidth;
        this.scaleHeight = (double) cameraHeight / screenHeight;
        this.cameraWidth = screenWidth;
        this.cameraHeight = screenHeight;
        this.rayCast = rayCast;
    }

    @Override
    public double getWidth() {
        return cameraWidth;
    }

    @Override
    public double getHeight() {
        return cameraHeight;
    }

    @Override
    public Vector2 getPosition() {
        return cameraPosition;
    }

    public abstract void display(Graphics2D graphics2D);

    public void saveTransform(Graphics2D graphics2D) {
        transformation = graphics2D.getTransform();
    }

    public void restoreTransform(Graphics2D graphics2D) {
        graphics2D.setTransform(transformation);
    }

    @Override
    public void draw(Graphics2D graphics2D, int screenHeight, int screenWidth) {
        saveTransform(graphics2D);
        AffineTransform transform = new AffineTransform();
        transform.scale(scaleWidth, scaleHeight);
        transform.translate(cameraPosition.x, cameraPosition.y);
        graphics2D.setTransform(transform);
        Rectangle2D.Double clipRect = new Rectangle2D.Double(0, 0, cameraWidth, cameraHeight);
        graphics2D.setClip(clipRect);
        display(graphics2D);
        restoreTransform(graphics2D);
    }

    @Override
    public void update(double delta) {

    }
}
