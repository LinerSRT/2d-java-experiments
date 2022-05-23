package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.util.List;

public class Ray extends Node {
    public Color color;
    public Vector2 position;
    public double angle;
    public double length;

    public Ray(Vector2 position, double angle, double length, Color color) {
        super(position, angle, length);
        this.position = position;
        this.angle = angle;
        this.length = length;
        this.color = color;
    }

    public Ray(Vector2 position, double angle, double length) {
        super(position, angle, length);
        this.position = position;
        this.angle = angle;
        this.length = length;
        this.color = Color.WHITE;
    }

    public Vector2 intersection(List<Line> lineList, double length) {
        Vector2 closest = null;
        for (Line line : lineList) {
            Vector2 intersection = intersects(line);
            if (intersection != null)
                if (closest == null || getPoint1().distance(intersection) < getPoint1().distance(closest))
                    closest = intersection;
        }
        return closest;
    }

    public void draw(ECore eCore) {
        eCore.setColor(color);
        eCore.drawShape(getPath());
    }
}
