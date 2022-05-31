package com.liner.twod_exp.engine.math;

import java.awt.geom.Point2D;

public class Vector2 {
    public double x;
    public double y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(double x) {
        this(x, 0);
    }

    public Vector2(Vector2 vector2) {
        this(vector2.getX(), vector2.getY());
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double angle(Vector2 point) {
        double xDistance = x - point.x;
        double yDistance = y - point.y;
        double angle = Math.toDegrees(Math.atan(yDistance / xDistance));
        double rotation = xDistance > 0 ? angle - 180 : angle;
        if (Math.abs(angle) == 90)
            return -angle;
        return rotation;
    }

    public int directionInt(Vector2 vector2) {
        double angle = angle(vector2);
        if (angle < 45 && angle > -45) {
            return 1;
        } else if (angle < -45 && angle > -135) {
            return 0;
        } else if (angle < -135 && angle > -225) {
            return 3;
        } else {
            return 2;
        }
    }

    public Vector2 direction(double angle, double length) {
        double theta = Math.toRadians(angle);
        float dx = (float) (x + Math.cos(theta) * length);
        float dy = (float) (y + Math.sin(theta) * length);
        return new Vector2(dx, dy);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getFloorX() {
        return (int) Math.floor(this.x);
    }

    public int getFloorY() {
        return (int) Math.floor(this.y);
    }

    public Vector2 add(double x) {
        return this.add(x, x);
    }

    public Vector2 add(double x, double y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 add(Vector2 x) {
        return this.add(x.getX(), x.getY());
    }

    public Vector2 subtract(double x) {
        return this.subtract(x, 0);
    }

    public Vector2 subtract(double x, double y) {
        return this.add(-x, -y);
    }

    public Vector2 subtract(Vector2 x) {
        return this.add(-x.getX(), -x.getY());
    }

    public Vector2 ceil() {
        return new Vector2((int) (this.x + 1), (int) (this.y + 1));
    }

    public Vector2 floor() {
        return new Vector2((int) Math.floor(this.x), (int) Math.floor(this.y));
    }

    public Vector2 round() {
        return new Vector2(Math.round(this.x), Math.round(this.y));
    }

    public Vector2 abs() {
        return new Vector2(Math.abs(this.x), Math.abs(this.y));
    }

    public Vector2 multiply(double number) {
        return new Vector2(this.x * number, this.y * number);
    }

    public Vector2 divide(double number) {
        return new Vector2(this.x / number, this.y / number);
    }

    public double distance(double x) {
        return this.distance(x, 0);
    }

    public double distance(double x, double y) {
        return Math.sqrt(this.distanceSquared(x, y));
    }

    public double distance(Vector2 vector) {
        return Math.sqrt(this.distanceSquared(vector.getX(), vector.getY()));
    }

    public double distanceSquared(double x) {
        return this.distanceSquared(x, 0);
    }

    public double distanceSquared(double x, double y) {
        return Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2);
    }

    public double distanceSquared(Vector2 vector) {
        return this.distanceSquared(vector.getX(), vector.getY());
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public Vector2 normalize() {
        double len = this.lengthSquared();
        if (len != 0) {
            return this.divide(Math.sqrt(len));
        }
        return new Vector2(0, 0);
    }


    public double dot(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }

    public Vector2 copy() {
        return new Vector2(this);
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    @Override
    public String toString() {
        return "Vector2(x=" + this.x + ",y=" + this.y + ")";
    }

    public Point2D.Double toPoint() {
        return new Point2D.Double(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2 vector2 = (Vector2) o;

        if (Double.compare(vector2.x, x) != 0) return false;
        return Double.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
