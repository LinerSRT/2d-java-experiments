package com.liner.twod_exp.tetris;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.*;

public class Block {
    private static final int cellsWidth = 10;
    private static final int cellsHeight = 20;
    private static final int CELL_SIZE = 32;
    private int x;
    private int y;
    private Color color;

    public Block(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void draw(ECore eCore) {
        eCore.setColor(color);
        eCore.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
    }

    public void moveDown() {
            y += 1;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void moveLeft() {
            x -= 1;
    }

    public void moveRight() {
            x += 1;
    }
}
