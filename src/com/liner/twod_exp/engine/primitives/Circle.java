package com.liner.twod_exp.engine.primitives;


import java.util.ArrayList;
import java.util.List;

public class Circle extends Shape {
    private Vector2 position;
    private int sides;
    private int radius;

    public Circle(Vector2 position, int sides, int radius) {
        super(getVertices(position, sides, radius));
        this.position = position;
        this.sides = sides;
        this.radius = radius;
    }

    public void setRadius(int radius) {
        this.radius = radius <= 0 ? 1 : radius;
        setVertices(getVertices(position, sides, radius));
    }

    public int getRadius() {
        return radius;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        setVertices(getVertices(position, sides, radius));
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setSides(int sides) {
        this.sides = sides <= 1 ? 2 : sides;
        setVertices(getVertices(position, sides, radius));
    }

    public int getSides() {
        return sides;
    }

    private static List<Vector2> getVertices(Vector2 position, int sides, int radius) {
        List<Vector2> vertices = new ArrayList<>();
        for (int i = 0; i < 360; i += 360 / sides) {
            double heading = Math.toRadians(i);
            vertices.add(new Vector2(position.x + Math.cos(heading) * radius, position.y + Math.sin(heading) * radius));
        }
        return vertices;
    }
}
