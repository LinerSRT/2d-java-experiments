package com.liner.twod_exp.ray_casting;

import com.liner.twod_exp.engine.Math2;
import com.liner.twod_exp.engine.primitives.Shape;
import com.liner.twod_exp.engine.primitives.Vector2;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Camera3D extends Camera {
    private List<Shape> shapeList;
    private int rayCount;
    private boolean enableFishEyeCorrection = false;

    public Camera3D(int screenWidth, int screenHeight, int cameraWidth, int cameraHeight, Vector2 cameraPosition, RayCast rayCast, List<Shape> shapeList) {
        super(screenWidth, screenHeight, cameraWidth, cameraHeight, cameraPosition, rayCast);
        this.shapeList = shapeList;
        this.rayCount = rayCast.getCount();
    }

    @Override
    public void display(Graphics2D graphics2D) {
        //graphics2D.setColor(Color.BLACK);
        //graphics2D.fillRect(0, 0, (int) screenWidth, (int) screenHeight);
        for (int i = 0; i < rayCast.getCount(); i++) {
            Ray ray = rayCast.getRayList().get(i);
            if (ray != null) {
                Vector2 intersection = ray.getClosestIntersection();
                if (intersection != null) {
                    fillScreenLine(graphics2D, rayCast.getPosition().distance(intersection), i, ray);
                }
            }
        }
    }

    public void setEnableFishEyeCorrection(boolean enableFishEyeCorrection) {
        this.enableFishEyeCorrection = enableFishEyeCorrection;
    }

    private void fillScreenLine(Graphics2D graphics2D, double distance, int index, Ray ray) {
        if (enableFishEyeCorrection)
            distance *= Math.cos(Math.toRadians(ray.getAngle() - rayCast.getAngle()));
        double width = screenWidth / rayCount;
        double position = index * width;
        double lineHeight = Math2.map(distance, new double[]{0, rayCast.getLength()}, new double[]{screenHeight/2, 0});
        Rectangle2D.Double line = new Rectangle2D.Double(position, screenHeight / 2 - lineHeight / 2, width, lineHeight);
        float factor = (float) (Math2.map(distance, new double[]{0, rayCast.getLength()}, new double[]{1,0}));
        graphics2D.setColor(blend(new Color(1f,0f,0f), new Color(0x000000), factor));
        graphics2D.fill(line);
    }

    private Color blend(Color original, Color color, double ratio){
        float r = (float) ratio;
        float ir = (float) 1.0 - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        original.getColorComponents(rgb1);
        color.getColorComponents(rgb2);
        return new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
    }
}
