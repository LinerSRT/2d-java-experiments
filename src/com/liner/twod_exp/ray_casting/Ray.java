package com.liner.twod_exp.ray_casting;

import com.liner.twod_exp.engine.primitives.Line;
import com.liner.twod_exp.engine.primitives.Node;
import com.liner.twod_exp.engine.primitives.Shape;
import com.liner.twod_exp.engine.primitives.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class Ray extends Node {
    private Vector2 closestIntersection;
    private double length;

    public Ray(Vector2 point1, double angle, double length) {
        super(point1, angle, length);
        this.length = length;
        closestIntersection = getPoint2();
    }



    public void cast(List<Shape> shapes){
        List<Vector2> intersections = getIntersections(shapes);
        closestIntersection = getClosestIntersection(intersections);
    }

    public Vector2 getClosestIntersection() {
        return closestIntersection;
    }

    public Vector2 getClosestIntersection(List<Vector2> intersections) {
        Vector2 closest = null;
        for (Vector2 vector2 : intersections) {
            if (closest == null || getPoint1().distance(vector2) < getPoint1().distance(closest))
                closest = vector2;
        }
        return closest;
    }

    public List<Vector2> getIntersections(List<Shape> shapes) {
        List<Vector2> intersections = new ArrayList<>();
        for (Shape shape : shapes)
            intersections.addAll(getIntersections(shape));
        return intersections;
    }

    public List<Vector2> getIntersections(Shape shape) {
        List<Vector2> intersections = new ArrayList<>();
        for (Node node : shape.getLines()) {
            Vector2 intersection = node.intersects(this);
            if (intersection != null) {
                intersections.add(intersection);
            }
        }
        return intersections;
    }

    public void setPosition(double x, double y) {
        double angle = getAngle();
        getPoint1().x = x;
        getPoint1().y = y;
        setAngle(angle);
    }

    public void setPosition(Vector2 position){
        setPosition(position.x, position.y);
    }

    public Vector2 getPosition() {
        return getPoint1();
    }

    public void setAngle(double angle) {
        rotateLine(getPoint1(), angle, 0, length);
    }

    public void setLength(double length) {
        double angle = getAngle();
        rotateLine(getPoint1(), angle, 0, length);
    }
}
