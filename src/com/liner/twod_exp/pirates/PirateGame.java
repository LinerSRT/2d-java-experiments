package com.liner.twod_exp.pirates;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.core.InputListener;
import com.liner.twod_exp.engine.core.Renderer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PirateGame extends Renderer implements InputListener {
    private List<Boat> boats;

    public PirateGame() {
        super(PirateGame.class.getSimpleName());
        boats = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            boats.add(new Boat(new Random().nextInt(500), new Random().nextInt(500)));
        }
    }

    @Override
    public void render(ECore eCore) {
        for (int i = 0; i < boats.size(); i++) {
            boats.get(i).render(eCore);
        }
    }

    @Override
    public void tick(ECore eCore, long frameTime) {
        eCore.addListener(this);

        for (int i = 0; i < boats.size(); i++) {
            boats.get(i).tick();
        }

    }
}
