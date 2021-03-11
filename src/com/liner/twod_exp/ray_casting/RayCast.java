package com.liner.twod_exp.ray_casting;

import com.liner.twod_exp.engine.Math2;
import com.liner.twod_exp.engine.primitives.Shape;
import com.liner.twod_exp.engine.primitives.Vector2;

import java.util.ArrayList;
import java.util.List;

public class RayCast {
    private Vector2 position;
    private int count;
    private double angle;
    private double fov;
    private double length;
    private List<Ray> rayList;

    public RayCast(Vector2 position, int count, double angle, double fov, double length) {
        this.position = position;
        this.count = count;
        this.angle = angle;
        this.fov = fov;
        this.length = length;
        this.rayList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            rayList.add(
                    new Ray(
                            new Vector2(),
                            Math2.map(i, new double[]{0, count}, new double[]{angle-fov/2, angle+fov/2}),
                            length
                    )
            );
        }
    }

    public void castRays(List<Shape> shapeList){
        for (int i = 0; i < rayList.size(); i++) {
            Ray ray = rayList.get(i);
            if(ray != null)
                ray.cast(shapeList);
        }
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        for (int i = 0; i < rayList.size(); i++) {
            Ray ray = rayList.get(i);
            if(ray != null)
                ray.setAngle(Math2.map(i, new double[]{0, count}, new double[]{angle-fov/2, angle+fov/2}));
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        for (int i = 0; i < rayList.size(); i++) {
            Ray ray = rayList.get(i);
            if(ray != null)
                ray.setPosition(position);
        }
    }

    public void setPosition(double x, double y) {
        this.position = new Vector2(x, y);
        for (int i = 0; i < rayList.size(); i++) {
            Ray ray = rayList.get(i);
            if(ray != null)
                ray.setPosition(x, y);
        }
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
        for (int i = 0; i < rayList.size(); i++) {
            Ray ray = rayList.get(i);
            if(ray != null)
                ray.setLength(length);
        }
    }

    public List<Ray> getRayList() {
        return rayList;
    }

    public double getFov() {
        return fov;
    }

    public void setFov(double fov) {
        this.fov = fov;
        for (int i = 0; i < rayList.size(); i++) {
            Ray ray = rayList.get(i);
            if(ray != null)
                ray.setAngle(Math2.map(i, new double[]{0, count}, new double[]{angle-fov/2, angle+fov/2}));
        }
    }

    public int getCount() {
        return count;
    }
}
