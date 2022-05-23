package com.liner.twod_exp.pirates;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Random;

public class Boat {
    private float x;
    private float y;
    private float angle;
    private float speed;
    private Color color;

    public Boat(float x, float y) {
        this.x = x;
        this.y = y;
        this.angle = new Random().nextFloat(360);
        this.speed = new Random().nextFloat(2);
        this.color = new Color(new Random().nextInt(255), new Random().nextInt(255), 0);
    }

    public void tick() {
        setSpeed(new Random().nextFloat() > .5f ? 0.2f : -0.2f);
        setAngle(new Random().nextFloat() > .5f ? 1.5f : -1.5f);
        if (x < 0)
            x = 500;
        if (y < 0)
            y = 500;
        if (x > 500)
            x = 0;
        if (y > 500)
            y = 0;
        this.x = (float) (x + Math.cos(Math.toRadians(angle)) * speed);
        this.y = (float) (y + Math.sin(Math.toRadians(angle)) * speed);
    }

    public void render(ECore eCore) {
        eCore.setColor(color);
        Path2D path2D = new Path2D.Float();
        path2D.moveTo((x + Math.cos(Math.toRadians(angle - 135)) * 8), ((y + Math.sin(Math.toRadians(angle - 135)) * 8)));
        path2D.lineTo(x, y);
        path2D.lineTo((x + Math.cos(Math.toRadians(angle + 135)) * 8), ((y + Math.sin(Math.toRadians(angle + 135)) * 8)));
        path2D.lineTo((x + Math.cos(Math.toRadians(angle)) * 16), ((y + Math.sin(Math.toRadians(angle)) * 16)));
        path2D.closePath();
        eCore.fillShape(path2D);
    }

    public void setSpeed(float speed) {
        this.speed += speed;
        this.speed = Math.max(0, Math.min(this.speed, 2));
    }

    public void setAngle(float angle) {
        this.angle += angle;
    }
}
