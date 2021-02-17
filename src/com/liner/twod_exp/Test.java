package com.liner.twod_exp;

import com.liner.twod_exp.engine.Engine;
import com.liner.twod_exp.engine.EngineConfig;
import com.liner.twod_exp.engine.ImageEntity;
import com.liner.twod_exp.engine.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Test extends Engine {
    private ImageEntity imageEntity;

    @Override
    public void init() {
        imageEntity = new ImageEntity(0,0, 20, 20, Resources.get().image("liner.png"));
    }

    @Override
    public void render(Graphics2D graphics2D) {
        imageEntity.render(graphics2D);
    }

    @Override
    public void tick(double updatesPerSecond) {
        imageEntity.tick(updatesPerSecond);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

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
