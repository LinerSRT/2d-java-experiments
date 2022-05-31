package com.liner.twod_exp.engine.geom;

import com.liner.twod_exp.engine.math.Node;

import java.awt.geom.Path2D;
import java.util.List;

public abstract class Shape {
    public List<Node> nodes;
    public int color;

    public Shape(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public Path2D getPath(){
        Path2D.Double path = new Path2D.Double();
        path.moveTo(nodes.get(0).point1.x, nodes.get(0).point1.y);
        path.lineTo(nodes.get(0).point2.x, nodes.get(0).point2.y);
        for (int i = 1; i < nodes.size(); i++) {
            path.lineTo(nodes.get(i).point2.x, nodes.get(i).point2.y);
        }
        path.closePath();
        return path;
    }
}
