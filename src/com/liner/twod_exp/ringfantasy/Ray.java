package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;
import org.joml.Vector2f;

public class Ray {
    private int index;
    private Vector2 position;
    private Vector2 direction;

    public Ray(Vector2 position, double angle, int index) {
        this.position = position;
        this.direction = new Vector2();
        this.index = index;
        setAngle(angle);
    }


    public Vector2 cast(Node wall) {
        double x1 = wall.getPoint1().x;
        double y1 = wall.getPoint1().y;
        double x2 = wall.getPoint2().x;
        double y2 = wall.getPoint2().y;

        double x3 = position.x;
        double y3 = position.y;
        double x4 = position.x + direction.x;
        double y4 = position.y + direction.y;

        if ((x1 == x2 && y1 == y2) || (x3 == x4 && y3 == y4)) {
            return null;
        }

        double denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

        if (denominator == 0) {
            return null;
        }

        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        if (t > 0 && t < 1 && u > 0) {
            return new Vector2(x1 + t * (x2 - x1), y1 + t * (y2 - y1));
        }

        return null;
    }

    public void setAngle(double angle) {
        direction.set((double) Math.cos(angle), (double) Math.sin(angle));
        direction.normalize();
    }

    public double getAngle() {
        return (double) Math.atan2(direction.y, direction.x);
    }

    public Vector2 getDirection() {
        return direction;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getIndex() {
        return index;
    }
}
