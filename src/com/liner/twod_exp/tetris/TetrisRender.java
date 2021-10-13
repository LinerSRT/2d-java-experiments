package com.liner.twod_exp.tetris;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.core.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TetrisRender extends Renderer {
    private static final int CELL_SIZE = 32;
    private static final int cellsWidth = 10;
    private static final int cellsHeight = 20;
    private List<Shape> shapeList;

    public TetrisRender() {
        super("Tetris");
        shapeList = new ArrayList<>();
        shapeList.add(Shape.random(4, 0));
    }

    @Override
    public void render(ECore eCore) {
        eCore.setColor(Color.DARK_GRAY);
        for (int x = 0; x != cellsWidth + 1; x++) {
            eCore.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, eCore.getHeight());
            for (int y = 0; y != cellsHeight + 1; y++) {
                eCore.drawLine(0, y * CELL_SIZE, eCore.getWidth(), y * CELL_SIZE);
            }
        }
        for (Shape shape : shapeList)
            shape.draw(eCore);
    }

    long timer = System.currentTimeMillis();

    @Override
    public void tick(ECore eCore, long frameTime) {
        eCore.addListener(this);
        if (System.currentTimeMillis() - timer > 500) {
            boolean allShapesCantMove = false;
            for (Shape shape : shapeList) {
                if (shape.canMoveDown(shapeList)) {
                    allShapesCantMove = true;
                    shape.moveDown(shapeList);
                }
            }
            if (!allShapesCantMove)
                shapeList.add(Shape.random(4, 0));
            timer += 500;
        }
    }

    @Override
    public void keyPress(ECore eCore, int keyCode) {
        for (Shape shape : shapeList) {
            if (keyCode == KeyEvent.VK_S)
                shape.moveDown(shapeList);
            if (keyCode == KeyEvent.VK_A)
                if (shape.canMoveDown(shapeList))
                    shape.moveLeft(shapeList);
            if (keyCode == KeyEvent.VK_D)
                if (shape.canMoveDown(shapeList))
                    shape.moveRight(shapeList);
        }
    }
}
