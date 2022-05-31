package com.liner.twod_exp.engine.math;


import java.awt.geom.Line2D;
import java.util.Objects;

public class Node {
    public Vector2 point1, point2;

    public Node(Vector2 point1, Vector2 point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Node(double x1, double y1, double x2, double y2) {
        this(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    public Node(Vector2 point1, double angle, double length) {
        rotateLine(point1, angle, 0, length);
    }

    public Node(Vector2 point1, double angle, double offset, double length) {
        rotateLine(point1, angle, offset, length);
    }

    public Vector2 intersects(Node toCheck) {
        Vector2 intersection = null;
        double r_x = point2.x - point1.x;
        double r_y = point2.y - point1.y;
        double s_x = toCheck.point2.x - toCheck.point1.x;
        double s_y = toCheck.point2.y - toCheck.point1.y;
        double denominator = r_x * s_y - r_y * s_x;
        double u = ((toCheck.point1.x - point1.x) * r_y - (toCheck.point1.y - point1.y) * r_x) / denominator;
        double t = ((toCheck.point1.x - point1.x) * s_y - (toCheck.point1.y - point1.y) * s_x) / denominator;
        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            intersection = new Vector2((float) (point1.x + t * r_x), (float) (toCheck.point1.y + u * s_y));
        }
        return intersection;
    }

    public static double getAngleBetween(Vector2 point1, Vector2 point2) {
        double xDistance = point1.x - point2.x;
        double yDistance = point1.y - point2.y;
        double angle = Math.toDegrees(Math.atan(yDistance / xDistance));
        double rotation = xDistance > 0 ? angle - 180 : angle;
        if (Math.abs(angle) == 90)
            return -angle;
        return rotation;
    }

    public double getLength() {
        return point1.distance(point2);
    }

    public Vector2 getPoint1() {
        return point1;
    }

    public void setPoint1(Vector2 point1) {
        this.point1 = point1;
    }

    public Vector2 getPoint2() {
        return point2;
    }

    public void setPoint2(Vector2 point2) {
        this.point2 = point2;
    }

    public double getAngle() {
        return getAngleBetween(point1, point2);
    }

    public Line2D.Double getPath() {
        return new Line2D.Double(point1.x, point1.y, point2.x, point2.y);
    }

    public Line2D.Double getScaledPath(int scaleFactor) {
        return new Line2D.Double(scaleFactor * point1.x, scaleFactor * point1.y, scaleFactor * point2.x, scaleFactor * point2.y);
    }

    public static Node getScaled(int scaleFactor, Node node) {
        return new Node(scaleFactor * node.point1.x, scaleFactor * node.point1.y, scaleFactor * node.point2.x, scaleFactor * node.point2.y);
    }

    public void rotateLine(Vector2 origin, double angle, double offset, double length) {
        double theta = Math.toRadians(angle + offset);
        float lineX = (float) (origin.x + Math.cos(theta) * length);
        float lineY = (float) (origin.y + Math.sin(theta) * length);
        this.point1 = origin;
        this.point2 = new Vector2(lineX, lineY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!Objects.equals(point1, node.point1)) return false;
        return Objects.equals(point2, node.point2);
    }

    @Override
    public int hashCode() {
        int result = point1 != null ? point1.hashCode() : 0;
        result = 31 * result + (point2 != null ? point2.hashCode() : 0);
        return result;
    }

    public Vector2 getClosest(Vector2 other) {
        double distance = point1.distance(other);
        return distance < point2.distance(other) ? point1 : point2;
    }
}
