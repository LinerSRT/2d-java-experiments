package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.core.Engine;
import com.liner.twod_exp.engine.core.InputListener;
import com.liner.twod_exp.engine.core.Renderer;
import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RayCastRender extends Renderer implements InputListener {
    private RayCast rayCast;
    private Random random;
    private List<Line> lineList;

    public RayCastRender() {
        super("Ray-Cast");
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        random = new Random();
        lineList = new ArrayList<>();
        rayCast = new RayCast(
                new Vector2(random.nextInt(Engine.getWindowWidth()), random.nextInt(Engine.getWindowHeight())),
                500,
                150,
                0,
                50
        );
        lineList.add(new Line(new Vector2(5, 5), new Vector2(Engine.getWindowWidth() / 2d - 5, 5), Color.WHITE));
        lineList.add(new Line(new Vector2(Engine.getWindowWidth() / 2d - 5, 5), new Vector2(Engine.getWindowWidth() / 2d - 5, Engine.getWindowHeight() - 5), Color.WHITE));
        lineList.add(new Line(new Vector2(Engine.getWindowWidth() / 2d - 5, Engine.getWindowHeight() - 5), new Vector2(5, Engine.getWindowHeight() - 5), Color.WHITE));
        lineList.add(new Line(new Vector2(5, 5), new Vector2(5, Engine.getWindowHeight() - 5), Color.WHITE));


        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(Engine.getWindowWidth() / 2);
            int y1 = random.nextInt(Engine.getWindowHeight() / 2);
            int x2 = random.nextInt(Engine.getWindowWidth() / 2);
            int y2 = random.nextInt(Engine.getWindowHeight() / 2);
            lineList.add(new Line(x1, y1, x2, y2, new Color(random.nextFloat(), random.nextFloat(), 0)));
        }
    }

    @Override
    public void render(ECore eCore) {
        eCore.addListener(this);
        rayCast.draw(eCore, lineList);
    }


    @Override
    public void tick(ECore eCore, long frameTime) {
        if (eCore.isKeyPressed(KeyEvent.VK_W) || eCore.isKeyPressed(KeyEvent.VK_UP)) {
            double theta = Math.toRadians(rayCast.getAngle());
            float x = (float) (rayCast.getX() + Math.cos(theta) * 1);
            float y = (float) (rayCast.getY() + Math.sin(theta) * 1);
            rayCast.setX(x);
            rayCast.setY(y);
        }
        if (eCore.isKeyPressed(KeyEvent.VK_S) || eCore.isKeyPressed(KeyEvent.VK_DOWN)) {
            double theta = Math.toRadians(rayCast.getAngle());
            float x = (float) (rayCast.getX() + Math.cos(theta) * -1);
            float y = (float) (rayCast.getY() + Math.sin(theta) * -1);
            rayCast.setX(x);
            rayCast.setY(y);
        }
        if (eCore.isKeyPressed(KeyEvent.VK_D) || eCore.isKeyPressed(KeyEvent.VK_RIGHT)) {
            double theta = Math.toRadians(rayCast.getAngle()+90);
            float x = (float) (rayCast.getX() + Math.cos(theta) * 1);
            float y = (float) (rayCast.getY() + Math.sin(theta) * 1);
            rayCast.setX(x);
            rayCast.setY(y);
        }
        if (eCore.isKeyPressed(KeyEvent.VK_A) || eCore.isKeyPressed(KeyEvent.VK_LEFT)) {
            double theta = Math.toRadians(rayCast.getAngle()-90);
            float x = (float) (rayCast.getX() + Math.cos(theta) * 1);
            float y = (float) (rayCast.getY() + Math.sin(theta) * 1);
            rayCast.setX(x);
            rayCast.setY(y);
        }
    }

    private Robot robot;
    private double lastPosition = 0;
    @Override
    public void mouseMove(ECore eCore, double x, double y) {
        double centerX = 500d / 2;
        if(lastPosition != x){
            double diff = x - lastPosition;
            lastPosition = x;
            rayCast.setAngle(rayCast.getAngle()+diff);
        }
        //if(robot != null){
        //    robot.mouseMove((int)eCore.getWidth()/2, (int)eCore.getHeight() / 2);
        //}
        //if(offset < 0){
        //    rayCast.setAngle(rayCast.getAngle()-1);
        //} else if(offset > 0){
        //    rayCast.setAngle(rayCast.getAngle()+1);
        //}
    }

    @Override
    public void mouseWheelMove(ECore eCore, int direction, double amount) {
        //rayCast.setAngle(rayCast.getAngle() + (direction < 0 ? -3 : 3));
    }
}
