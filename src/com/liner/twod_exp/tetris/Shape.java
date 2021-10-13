package com.liner.twod_exp.tetris;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Shape {
    private static final int cellsWidth = 10;
    private static final int cellsHeight = 20;
    private static final int CELL_SIZE = 32;
    private List<Block> blockList;
    private Color color;

    public static Shape random(int x, int y) {
        Color[] colors = new Color[8];
        colors[0] = Color.GREEN;
        colors[1] = Color.CYAN;
        colors[2] = Color.RED;
        colors[3] = Color.MAGENTA;
        colors[4] = Color.YELLOW;
        colors[5] = Color.PINK;
        colors[6] = Color.BLUE;
        colors[7] = Color.BLUE;
        Color color = colors[new Random().nextInt(colors.length)];
        switch (new Random().nextInt(7)) {
            case 0:
                return iPiece(color, x, y);
            case 1:
                return jPiece(color, x, y);
            case 2:
                return lPiece(color, x, y);
            case 3:
                return squarePiece(color, x, y);
            case 4:
                return tPiece(color, x, y);
            case 5:
                return zPiece(color, x, y);
            case 6:
                return sPiece(color, x, y);
        }
        return sPiece(color, x, y);
    }

    public static Shape iPiece(Color color, int x, int y) {
        return new Shape(
                new Block(color, x, y),
                new Block(color, x, y + 1),
                new Block(color, x, y + 2),
                new Block(color, x, y + 3)
        );
    }

    public static Shape jPiece(Color color, int x, int y) {
        return new Shape(
                new Block(color, x + 1, y),
                new Block(color, x + 1, y + 1),
                new Block(color, x + 1, y + 2),
                new Block(color, x, y + 2)
        );
    }

    public static Shape lPiece(Color color, int x, int y) {
        return new Shape(
                new Block(color, x, y),
                new Block(color, x, y + 1),
                new Block(color, x, y + 2),
                new Block(color, x + 1, y + 2)
        );
    }

    public static Shape squarePiece(Color color, int x, int y) {
        return new Shape(
                new Block(color, x, y),
                new Block(color, x + 1, y),
                new Block(color, x + 1, y + 1),
                new Block(color, x, y + 1)
        );
    }

    public static Shape tPiece(Color color, int x, int y) {
        return new Shape(
                new Block(color, x + 1, y),
                new Block(color, x, y + 1),
                new Block(color, x + 1, y + 1),
                new Block(color, x + 2, y + 1)
        );
    }

    public static Shape zPiece(Color color, int x, int y) {
        return new Shape(
                new Block(color, x, y),
                new Block(color, x + 1, y),
                new Block(color, x + 1, y + 1),
                new Block(color, x + 2, y + 1)
        );
    }

    public static Shape sPiece(Color color, int x, int y) {
        return new Shape(
                new Block(color, x + 1, y),
                new Block(color, x + 2, y),
                new Block(color, x + 1, y + 1),
                new Block(color, x, y + 1)
        );
    }

    public Shape(List<Block> blockList) {
        this.blockList = blockList;
    }

    public Shape(Block... blocks) {
        this.blockList = Arrays.asList(blocks);
    }

    public Shape(Shape another) {
        this.blockList = another.blockList;
    }

    public void setColor(Color color) {
        this.color = color;
        for(Block block:blockList)
            block.setColor(color);
    }

    public void draw(ECore eCore) {
        for (Block block : blockList) {
            block.draw(eCore);
        }
    }


    public List<Block> getBlockList() {
        return blockList;
    }

    public boolean intersects(Shape other) {
        for (Block block : blockList) {
            for (Block otherBlock : other.getBlockList()) {
                if (block.getY() == otherBlock.getY() && block.getX() == otherBlock.getX())
                    return true;
            }
        }
        return false;
    }


    public boolean canMoveDown(List<Shape> shapes) {
        for (Block block : blockList)
            if (block.getY() + 1 >= cellsHeight)
                return false;
        if (shapes.size() == 1)
            return true;

        List<Block> allBlocks = new ArrayList<>();
        shapes.forEach(shape -> {
            if(shape != Shape.this){
                allBlocks.addAll(shape.getBlockList());
            }
        });
        Shape copy = new Shape(this);
        copy.moveDownF();
        for (Block otherBlock : allBlocks) {
            for (Block block : copy.getBlockList()) {
                if (block.getY() == otherBlock.getY() && block.getX() == otherBlock.getX())
                    return true;
            }
        }
        return true;
    }

    public boolean canMoveDown() {
        for (Block block : blockList)
            if (block.getY() + 1 >= cellsHeight)
                return false;
        return true;
    }

    public boolean canMoveLeft() {
        for (Block block : blockList)
            if (block.getX() - 1 < 0)
                return false;
        return true;
    }

    public boolean canMoveRight() {
        for (Block block : blockList)
            if (block.getX() + 1 >= cellsWidth)
                return false;
        return true;
    }

    public void moveDown(List<Shape> shapes) {
        if (!canMoveDown(shapes))
            return;
        for (Block block : blockList)
            block.moveDown();
    }

    public void moveDownF() {
        for (Block block : blockList)
            block.moveDown();
    }

    public void moveLeft() {
        if (!canMoveLeft())
            return;
        for (Block block : blockList)
            block.moveLeft();
    }

    public void moveRight() {
        if (!canMoveRight())
            return;
        for (Block block : blockList)
            block.moveRight();
    }
}
