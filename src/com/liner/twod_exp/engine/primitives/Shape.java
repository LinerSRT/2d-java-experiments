package com.liner.twod_exp.engine.primitives;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shape {
    private final List<Line> lines = new ArrayList<>();
    private List<Vector2> vertices;

    public Shape(List<Vector2> points) {
        this.vertices = points;
        setVertices(points);
    }

    public Shape(Vector2... points) {
        this(Arrays.asList(points));
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Vector2> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector2> vertices) {
        this.vertices = vertices;
        for (int i = 0; i < vertices.size() - 1; i++)
            lines.add(new Line(vertices.get(i), vertices.get(i + 1)));
        lines.add(new Line(vertices.get(vertices.size() - 1), vertices.get(0)));
    }

    public Path2D getPath() {
        Path2D path2D = new Path2D.Double();
        path2D.moveTo(vertices.get(0).x, vertices.get(0).y);
        for (int i = 1; i < vertices.size() - 1; i++) {
            path2D.lineTo(vertices.get(i).x, vertices.get(i).y);
        }
        path2D.lineTo(vertices.get(vertices.size() - 1).x, vertices.get(vertices.size() - 1).y);
        path2D.closePath();
        return path2D;
    }
}
