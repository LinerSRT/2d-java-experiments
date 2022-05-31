package com.liner.twod_exp;


import com.liner.twod_exp.engine.core.Engine;
import com.liner.twod_exp.ringfantasy.RingRender;

public class Main {

    public static void main(String[] args) {
RingFantasyGame();

    }


    private static void RingFantasyGame() {
        Engine.init((int) ((16 * 13 + 6) * 3f), (int) ((16 * 22 + 6) * 1.5f));
        RingRender ringRender = new RingRender();
        Engine.register(ringRender);
        Engine.show(ringRender);
        Engine.displayWindow(ringRender.getIdentifier());
        Engine.start();
        Engine.setScale(1.5f);
    }


}
