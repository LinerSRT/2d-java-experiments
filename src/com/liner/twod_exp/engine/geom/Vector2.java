package com.liner.twod_exp.engine.geom;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Vector2 {
    public final double x;
    public final double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector2) {
        this(vector2.x, vector2.y);
    }

    public Vector2() {
        this(0, 0);
    }

    public Vector2 add(double x, double y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 add(Vector2 vector2) {
        return new Vector2(this.x + vector2.x, this.y + vector2.y);
    }

    public Vector2 add(double value) {
        return add(value, value);
    }


    public Vector2 subtract(double x, double y) {
        return new Vector2(this.x - x, this.y - y);
    }

    public Vector2 subtract(Vector2 vector2) {
        return new Vector2(this.x - vector2.x, this.y - vector2.y);
    }

    public Vector2 subtract(double value) {
        return subtract(value, value);
    }

    public Vector2 multiply(double x, double y) {
        return new Vector2(this.x * x, this.y * y);
    }

    public Vector2 multiply(Vector2 vector2) {
        return new Vector2(this.x * vector2.x, this.y * vector2.y);
    }
    public Vector2 multiply(double value) {
        return multiply(value, value);
    }

    public Vector2 divide(double x, double y) {
        return new Vector2(this.x / x, this.y / y);
    }

    public Vector2 divide(Vector2 vector2) {
        return new Vector2(this.x / vector2.x, this.y / vector2.y);
    }
    public Vector2 divide(double value) {
        return divide(value, value);
    }

    public double dot(Vector2 vector2) {
        return (this.x * vector2.x) + (this.y * vector2.y);
    }

    public double cross(Vector2 vector2) {
        return (this.x * vector2.x) - (this.y * vector2.y);
    }

    public Vector2 copy() {
        return this;
    }

    public double angleDegrees(Vector2 vector2) {
        return Math.toDegrees(angleRadians(vector2));
    }

    public double angleRadians(Vector2 vector2) {
        return Math.atan2(vector2.y - this.y, vector2.x - this.x);
    }

    public double distance(Vector2 vector2) {
        return Math.sqrt(Math.pow(vector2.x - this.x, 2) + Math.pow(vector2.y - this.y, 2));
    }

    public double distance(double x, double y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }

    public Vector2 middle(List<Vector2> vector2List) {
        if (vector2List.isEmpty())
            return this;
        double x = 0;
        double y = 0;
        for (Vector2 vector2 : vector2List) {
            x += vector2.x;
            y += vector2.y;
        }
        return new Vector2(x / vector2List.size(), y / vector2List.size());
    }

    public Vector2 middle(Vector2... vector2List) {
        return middle(Arrays.asList(vector2List));
    }

    public Vector2 nearest(List<Vector2> vector2List) {
        Vector2 result = null;
        double nearestDistance = Double.MAX_VALUE;
        for (Vector2 vector2 : vector2List) {
            double candidateDistance = distance(vector2);
            if (candidateDistance < nearestDistance) {
                result = vector2;
                nearestDistance = candidateDistance;
            }
        }
        return result == null ? this : result;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
