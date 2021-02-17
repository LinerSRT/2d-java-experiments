package com.liner.twod_exp;

import com.liner.twod_exp.engine.Engine;
import com.liner.twod_exp.engine.EngineConfig;
import com.liner.twod_exp.engine.ImageEntity;
import com.liner.twod_exp.engine.Resources;
import com.liner.twod_exp.engine.animator.AccelerateInterpolator;
import com.liner.twod_exp.engine.animator.AnimationListenerAdapter;
import com.liner.twod_exp.engine.animator.Animator;
import com.liner.twod_exp.engine.animator.LinearInterpolator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Test extends Engine {
    private ImageEntity imageEntity;
    private ImageEntity imageEntity2;

    @Override
    public void init() {
        imageEntity = new ImageEntity(0,0, 20, 20, Resources.get().image("liner.png"));
        imageEntity2 = new ImageEntity(100,0, 20, 20, Resources.get().image("liner.png"));
    }

    @Override
    public void render(Graphics2D graphics2D) {
        imageEntity.render(graphics2D);
        imageEntity2.render(graphics2D);
    }

    @Override
    public void tick(double updatesPerSecond) {
        imageEntity.tick(updatesPerSecond);
        imageEntity2.tick(updatesPerSecond);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        imageEntity2.moveTo(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public EngineConfig getConfig() {
        return new EngineConfig.Builder()
                .setTicksPerSecond(60)
                .setFramesPerSecond(60)
                .setScreenHeight(400)
                .setScreenWidth(400)
                .setName("Testing")
                .build();
    }
}
