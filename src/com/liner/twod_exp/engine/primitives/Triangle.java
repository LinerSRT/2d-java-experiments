package com.liner.twod_exp.engine.primitives;

public class Triangle extends Shape{
    public Triangle(Vector2 point1, Vector2 point2, Vector2 points3) {
        super(point1, point2, points3);
    }
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        super(new Vector2(x1, y1), new Vector2(x2,y2), new Vector2(x3, y3));
    }
}
