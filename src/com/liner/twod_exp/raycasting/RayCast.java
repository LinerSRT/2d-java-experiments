package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.core.Engine;
import com.liner.twod_exp.engine.core.Memory;
import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class RayCast {
    private final LinkedList<Ray> rays;
    private Vector2 position;
    private int rayCount;
    private double distance;
    private double angle;
    private double fov;


    final int darkestValue = 12, brightestValue = 200;
    final Color darkestColor = new Color(darkestValue, darkestValue, darkestValue), brightestColor = new Color(brightestValue, brightestValue, brightestValue);


    public RayCast(Vector2 position, int rayCount, double distance, double angle, double fov) {
        this.position = position;
        this.rayCount = rayCount;
        this.distance = distance;
        this.angle = angle;
        this.fov = fov;
        this.rays = new LinkedList<>();
        for (int i = 0; i < rayCount; i++) {
            rays.add(new Ray(
                            position,
                            map(i, new double[]{0, rayCount}, new double[]{angle - fov / 2, angle + fov / 2}),
                            distance
                    )
            );
        }
    }

    public void draw(ECore eCore, List<Line> lineList) {
        long start = System.currentTimeMillis();
        int intersectionCount = 0;

        double renderWidth = Engine.getWindowWidth();
        double renderHeight = Engine.getWindowHeight();
        drawSky(eCore, (int) renderWidth, (int) renderHeight);
        drawFloor(eCore, (int) renderWidth, (int) renderHeight);
        for (int i = 0; i < rayCount; i++) {
            double rayAngle;
            rayAngle = map(i, new double[]{0, rayCount}, new double[]{angle - fov / 2, angle + fov / 2});
            Vector2 intersection = null;
            Color color = new Color(0xFFFFFF);
            for (Line line : lineList) {
                Vector2 vector2 = line.intersects(new Node(position, rayAngle, distance));
                if (vector2 == null)
                    continue;
                if (intersection == null || position.distance(vector2) < position.distance(intersection)) {
                    intersection = vector2;
                    color = line.getColor();
                }
            }
            intersectionCount = intersection == null ? intersectionCount : intersectionCount + 1;
            Ray ray = new Ray(
                    position,
                    rayAngle,
                    intersection == null ? distance : position.distance(intersection),
                    color
            );

            double width = renderWidth / rayCount;
            double rayDistance = ray.length;
            if (rayDistance >= distance)
                continue;
            rayDistance *= Math.cos(Math.toRadians(ray.getAngle() - angle));
            double x = i * width;
            double y = renderWidth / 2;
            double height = map(rayDistance, new double[]{0, distance}, new double[]{renderHeight, 20});
            float colorFactor = (float) map(rayDistance, new double[]{0, distance * 1.2}, new double[]{1, 0});
            Rectangle2D.Double line = new Rectangle2D.Double(
                    x,
                    y - height / 2,
                    width,
                    height);
            eCore.setColor(new Color(makeColorDarker(ray.color.getRGB(), colorFactor)));
            eCore.fillShape(line);
        }
        eCore.restore();
        long end = System.currentTimeMillis() - start;
        eCore.setColor(new Color(0x9F000000, true));
        eCore.fillRect(1, 1, 220, 170);
        eCore.setColor(Color.WHITE);
        eCore.drawString("Rays: " + rayCount, 10, 20);
        eCore.drawString("Fov: " + fov, 10, 40);
        eCore.drawString("Distance: " + distance, 10, 60);
        eCore.drawString("Position: " + Math.round(position.x) + ", " + Math.round(position.y), 10, 80);
        eCore.drawString("Direction: " + angle, 10, 100);
        eCore.drawString("Raycast delay: " + end + " ms", 10, 120);
        eCore.drawString("Intersections: " + intersectionCount, 10, 140);
        eCore.drawString(Memory.allString(), 10, 160);
        eCore.restore();
//
//
//        for (int i = 0; i < rays.size(); i++) {
//            Ray ray = rays.get(i);
//            //double rayDistance = ray.getDistance() * Math.cos(Math.toRadians(ray.getAngle()));
//            double rayDistance = RayMath.correctDistance(ray, angle);
//            if (ray.getLength() == distance) {
//                continue;
//            }
//
//
//            double width = renderWidth / rayCount;
//            double x = i * width;
//            double y = renderWidth / 2;
//            //double height = (renderHeight) * (rayDistance / distance);
//            double height = map(rayDistance, new double[]{0, distance}, new double[]{renderHeight, 0});
//            float colorFactor = (float) map(rayDistance, new double[]{0, distance}, new double[]{1, 0});
//            Rectangle2D.Double line = new Rectangle2D.Double(
//                    x,
//                    y - height / 2,
//                    width,
//                    height);
//            eCore.setColor(new Color(makeColorDarker(ray.color.getRGB(), colorFactor)));
//            eCore.fillShape(line);
//        }


    }


    private void drawSky(ECore eCore, int width, int height) {
        GradientPaint gp = new GradientPaint(
                (int) width / 2f,
                0,
                new Color(0x0773F8),
                (int) width / 2f,
                (int) height / 2f,
                darkestColor);
        eCore.getGraphics().setPaint(gp);
        eCore.fillShape(new Rectangle((int) width, (int) height / 2));
    }

    private void drawFloor(ECore eCore, int width, int height) {
        GradientPaint gp = new GradientPaint(
                (int) width / 2f,
                (int) height / 2f,
                darkestColor,
                (int) width / 2f,
                (int) height,
                new Color(0x6F2000)
        );
        eCore.getGraphics().setPaint(gp);
        eCore.fillShape(new Rectangle(0, (int) height / 2, (int) width, (int) height / 2));
    }


    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setX(double x) {
        this.position.x = x;
    }

    public double getX() {
        return position.x;
    }

    public void setY(double y) {
        this.position.y = y;
    }

    public double getY() {
        return position.y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }

    public double getFov() {
        return fov;
    }

    public void setRayCount(int rayCount) {
        this.rayCount = rayCount;
    }

    public int getRayCount() {
        return rayCount;
    }

    public static double map(double value, double[] from, double[] to) {
        return (to[1] - to[0]) / (from[1] - from[0]) * (value - from[0]) + to[0];
    }


    private int makeColorDarker(int color, double brightness) {
        if (brightness < 1.0) {
            int a = (color/* >> 24*/) & 0xFF;
            if (a > 0 && a < 255) {
                a += brightness;
            }
            int r = (int) (((color >> 24) & 0xFF) * brightness);
            int g = (int) (((color >> 16) & 0xFF) * brightness);
            int b = (int) (((color >> 8) & 0xFF) * brightness);

            return (a/* << 24*/) | (r << 24) | (g << 16) | (b << 8);
            //return (color & 0xFF000000) | (r << 16) | (g << 8) | b;
            //return (0xFF000000) | (r << 16) | (g << 8) | b;
        }
        return color;
    }
}
