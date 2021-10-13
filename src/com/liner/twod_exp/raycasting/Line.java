package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends Node {
    private Color color;

    public Line(Vector2 point1, Vector2 point2, Color color) {
        super(point1, point2);
        this.color = color;
    }

    public Line(double x1, double y1, double x2, double y2, Color color) {
        super(x1, y1, x2, y2);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void draw(ECore eCore){
        eCore.setColor(color);
        eCore.drawShape(getPath());
    }
}
