package com.liner.twod_exp.ray_casting;

import com.liner.twod_exp.engine.primitives.Line;
import com.liner.twod_exp.engine.primitives.Shape;
import com.liner.twod_exp.engine.primitives.Vector2;

import java.awt.*;
import java.util.List;

public class Camera2D extends Camera{
    private List<Shape> shapeList;

    public Camera2D(int screenWidth, int screenHeight, int cameraWidth, int cameraHeight, Vector2 cameraPosition, RayCast rayCast, List<Shape> shapeList) {
        super(screenWidth, screenHeight, cameraWidth, cameraHeight, cameraPosition, rayCast);
        this.shapeList = shapeList;
    }

    @Override
    public void display(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0, 0, 0, 117));
        graphics2D.fillRect(0,0, (int)cameraWidth, (int)cameraHeight);
        for (Shape shape : shapeList) {
            graphics2D.setColor(new Color(38, 255, 0, 50));
            graphics2D.fill(shape.getPath());
        }
        for(Ray ray:rayCast.getRayList()){
            Vector2 closestRayIntersection = ray.getClosestIntersection();
            graphics2D.setColor(new Color(243, 255, 0, 50));
            if (closestRayIntersection == null) {
                graphics2D.draw(ray.getPath());
            } else
                graphics2D.draw(new Line(ray.getPoint1(), closestRayIntersection).getPath());
        }
    }
}
