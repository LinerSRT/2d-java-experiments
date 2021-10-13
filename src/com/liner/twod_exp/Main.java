package com.liner.twod_exp;


import com.liner.twod_exp.engine.core.Engine;
import com.liner.twod_exp.tetris.TetrisRender;

public class Main {

    public static void main(String[] args) {
        Engine.init(320, 640);
        TetrisRender tetrisRender = new TetrisRender();
        Engine.register(tetrisRender);
        Engine.show(tetrisRender);
        Engine.displayWindow(tetrisRender.getIdentifier());
        Engine.start();
    }
}
