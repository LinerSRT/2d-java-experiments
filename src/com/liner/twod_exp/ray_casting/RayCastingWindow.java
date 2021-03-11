package com.liner.twod_exp.ray_casting;

import com.liner.twod_exp.engine.Engine;
import com.liner.twod_exp.engine.EngineConfig;
import com.liner.twod_exp.engine.primitives.Circle;
import com.liner.twod_exp.engine.primitives.Rectangle;
import com.liner.twod_exp.engine.primitives.Shape;
import com.liner.twod_exp.engine.primitives.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class RayCastingWindow extends Engine {
    private static final int SCREEN_W = 600;
    private static final int SCREEN_H = 600;
    private List<Shape> shapes;
    private RayCast rayCast;
    private Camera2D camera2D;
    private Camera3D camera3D;

    private int[] keys;
    private int[] mouse;
    private Robot robot;

    @Override
    public void setup() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        keys = new int[5];
        mouse = new int[2];
        shapes = new ArrayList<>();
        shapes.add(new Rectangle(new Vector2(100, 100), 50, 50));
        shapes.add(new Rectangle(new Vector2(200, 100), 50, 50));
        shapes.add(new Rectangle(new Vector2(100, 200), 50, 50));
        shapes.add(new Rectangle(new Vector2(200, 200), 50, 50));
        shapes.add(new Circle(new Vector2(SCREEN_W / 2d + 80, SCREEN_H / 2d), 15, 80));
        rayCast = new RayCast(new Vector2(0, 0), 1000, 45, 45, 600);
        camera2D = new Camera2D(SCREEN_W, SCREEN_H, 200, 200, new Vector2(), rayCast, shapes);
        camera3D = new Camera3D(SCREEN_W, SCREEN_H, SCREEN_W, SCREEN_H, new Vector2(), rayCast, shapes);
        addRenderObject(camera3D);
        addRenderObject(camera2D);

        new RayCastSettings(rayCast, camera3D);
    }


    @Override
    public void update() {
        if (keys[0] == 1) {
            //W
            float lineX = (float) (rayCast.getPosition().x + Math.cos(Math.toRadians(rayCast.getAngle())) * 1);
            float lineY = (float) (rayCast.getPosition().y + Math.sin(Math.toRadians(rayCast.getAngle())) * 1);
            rayCast.setPosition(new Vector2(lineX, lineY));
        }
        if (keys[1] == 1) {
            //A
            rayCast.setAngle(rayCast.getAngle() - 2);
        }
        if (keys[2] == 1) {
            //S
            float lineX = (float) (rayCast.getPosition().x + Math.cos(Math.toRadians(rayCast.getAngle())) * -1);
            float lineY = (float) (rayCast.getPosition().y + Math.sin(Math.toRadians(rayCast.getAngle())) * -1);
            rayCast.setPosition(new Vector2(lineX, lineY));
        }
        if (keys[3] == 1) {
            //D
            rayCast.setAngle(rayCast.getAngle() + 2);
        }

        rayCast.castRays(shapes);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_W) keys[0] = 1;
        if (keyEvent.getKeyCode() == KeyEvent.VK_A) keys[1] = 1;
        if (keyEvent.getKeyCode() == KeyEvent.VK_S) keys[2] = 1;
        if (keyEvent.getKeyCode() == KeyEvent.VK_D) keys[3] = 1;
        if (keyEvent.getKeyCode() == KeyEvent.VK_CONTROL) keys[4] = 1;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_W) keys[0] = 0;
        if (keyEvent.getKeyCode() == KeyEvent.VK_A) keys[1] = 0;
        if (keyEvent.getKeyCode() == KeyEvent.VK_S) keys[2] = 0;
        if (keyEvent.getKeyCode() == KeyEvent.VK_D) keys[3] = 0;
        if (keyEvent.getKeyCode() == KeyEvent.VK_CONTROL) keys[4] = 0;
    }

    @Override
    public EngineConfig getConfig() {
        return new EngineConfig.Builder()
                .setName("2D Ray-cast")
                .setScreenWidth(SCREEN_W)
                .setScreenHeight(SCREEN_H)
                .setFramesPerSecond(60)
                .setTicksPerSecond(60)
                .build();
    }


}
