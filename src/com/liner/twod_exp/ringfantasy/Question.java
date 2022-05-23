package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public abstract class Question {
    private String text;
    private int startX;
    private int startY;
    private boolean isShowing;
    private boolean isSelected;
    private int selection = 0;

    public Question(String text, int startX, int startY) {
        this.text = text;
        this.startX = startX;
        this.startY = startY;
    }

    public void draw(ECore core) {
        if (!isShowing)
            return;
        int availableWidth = 0;
        String[] lines = new String[10];
        Arrays.fill(lines, "");
        String[] words = text.split(" ");
        int processedLine = 0;
        for (String word : words) {
            if (word.equals("")) {
                break;
            } else if (word.equals("\\n")) {
                processedLine++;
            } else {
                if (processedLine == 0)
                    availableWidth = RingRender.tileSize * 13 - 32;
                else
                    availableWidth = RingRender.tileSize * 13 - 16;
                String line = lines[processedLine];
                if (line.length() > 0)
                    line = line + " ";
                if (core.getGraphics().getFontMetrics().stringWidth(line + word) > availableWidth) {
                    processedLine++;
                    lines[processedLine] = word;
                } else {
                    lines[processedLine] = line + word;
                }
            }
        }

        FontMetrics fontMetrics = core.getGraphics().getFontMetrics();
        double lineHeight = fontMetrics.getHeight();
        int totalHeight = (int) (lineHeight * (processedLine + 2)) + 8;
        core.setColor(new Color(0, 0, 0, 218));
        core.getGraphics().fillRoundRect(startX, startY, RingRender.tileSize * 13, totalHeight, 8,8);
        core.getGraphics().setColor(new Color(3, 105, 217));
        core.getGraphics().drawRoundRect(startX + 1, startY + 1, RingRender.tileSize * 13 - 4, totalHeight - 4, 8, 8);
        core.setColor(new Color(210, 210, 210));
        core.draw(Resource.objectImages[48], startX + 6, startY + 2);
        for (int i = 0; i <= processedLine; i++) {
            core.getGraphics().drawString(lines[i], startX + 8 + (i == 0 ? 18 : 0), (int) (startY + (lineHeight * i) + lineHeight / 2 + 4) + 4);
        }

        double okWidth = core.getTextBounds("Yes").getWidth();
        double cancelWidth = core.getTextBounds("No").getWidth();
        if (selection == 0) {
            drawSelector(core.getGraphics(), startX + 8, (int) ((startY + totalHeight - lineHeight) - 3));
        } else if(selection == 1){
            drawSelector(core.getGraphics(), (int) (availableWidth - cancelWidth), (int) ((startY + totalHeight - lineHeight) - 3));
        }
        core.setColor(new Color(128, 128, 128));
        core.drawString("Yes", startX + 16, startY + ((int) (totalHeight - (lineHeight - 6))));
        core.drawString("No", (int) (availableWidth - cancelWidth) + 8, startY + ((int) (totalHeight - (lineHeight - 6))));
    }

    void drawSelector(Graphics2D graphics, int x, int y) {
        graphics.setColor(new Color(3, 105, 217));
        graphics.drawLine(x, y, x, y + 9);
        graphics.drawLine(x + 1, y + 1, x + 1, y + 8);
        graphics.drawLine(x + 2, y + 2, x + 2, y + 7);
        graphics.drawLine(x + 3, y + 3, x + 3, y + 6);
        graphics.drawLine(x + 4, y + 4, x + 4, y + 5);
    }


    public void show() {
        isShowing = true;
    }

    public void hide() {
        isShowing = false;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void keyEvent(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> selection = Math.max(0, selection - 1);
            case KeyEvent.VK_RIGHT -> selection = Math.min(1, selection + 1);
            case KeyEvent.VK_ENTER -> onSelected(selection);
        }
    }

    public abstract void onSelected(int selection);


}
