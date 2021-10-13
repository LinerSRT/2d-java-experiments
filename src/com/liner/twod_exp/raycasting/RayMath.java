package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

public class RayMath {

    public static double correctDistance(Node ray, double viewAngle) {
        double distance = ray.getPoint1().distance(ray.getPoint2());
        Node cameraPlane;
        Node node;
        if (ray.getAngle() < viewAngle) {
            //LEFT
            cameraPlane = new Node(ray.getPoint1(), viewAngle - 90, distance);
            node = new Node(ray.getPoint2(), ray.getAngle() + Math.abs(ray.getAngle() - viewAngle), distance);
        } else {
            //RIGHT
            cameraPlane = new Node(ray.getPoint1(), viewAngle + 90, distance);
            node = new Node(ray.getPoint2(), ray.getAngle() - Math.abs(ray.getAngle() - viewAngle), distance);
        }
        Vector2 intersection = cameraPlane.intersects(node);
        if (intersection != null) {
            return node.getPoint1().distance(intersection);
        } else {
            return distance;
        }
    }

}
