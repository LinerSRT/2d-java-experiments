package com.liner.twod_exp;


import com.liner.twod_exp.engine.core.Engine;
import com.liner.twod_exp.pirates.PirateGame;
import com.liner.twod_exp.ringfantasy.Battle;
import com.liner.twod_exp.ringfantasy.RingRender;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Engine.init((int) ((16*13 + 6) * 3f), (int)((16*22 + 6) * 1.5f));
        RingRender ringRender = new RingRender();
        Engine.register(ringRender);
        Engine.show(ringRender);
        Engine.displayWindow(ringRender.getIdentifier());
        Engine.start();
        Engine.setScale(1.5f);
        int[] entries = new int[100];
        Arrays.fill(entries, 0);

    }



}
