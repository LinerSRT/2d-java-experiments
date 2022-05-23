package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Message {
    private final String[][] wordsChunks;
    private final int startX;
    private final int startY;
    private boolean isShowing;

    public Message(String text, int startX, int startY) {
        this.wordsChunks = splitArray(text.split(" "), 25);
        this.startX = startX;
        this.startY = startY;
    }

    private int position = 0;

    private String composeText(String[] words) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String text : words) {
            stringBuilder.append(text).append(" ");
            //stringBuilder.append(text).append("\n");
        }
        return stringBuilder.toString();
    }

    public void draw(ECore core) {
        if (!isShowing)
            return;
        int availableWidth = 0;
        String[] lines = new String[200];
        Arrays.fill(lines, "");
        String[] words = composeText(wordsChunks[position]).split(" ");
        int processedLine = 0;
        for (String word : words) {
            if (word.equals("") || processedLine >= words.length) {
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
        core.getGraphics().fillRoundRect(startX, startY, RingRender.tileSize * 13, totalHeight, 16, 16);
        core.getGraphics().setColor(new Color(3, 105, 217));
        core.getGraphics().drawRoundRect(startX + 1, startY + 1, RingRender.tileSize * 13 - 4, totalHeight - 4, 16, 16);
        core.setColor(new Color(210, 210, 210));
        core.draw(Resource.objectImages[48], startX + 6, startY + 2);
        for (int i = 0; i <= processedLine; i++) {
            core.getGraphics().drawString(lines[i], startX + 8 + (i == 0 ? 18 : 0), (int) (startY + (lineHeight * i) + lineHeight / 2 + 4) + 4);
        }
        core.setColor(new Color(128, 128, 128));
        core.drawString("Hit 'enter' to continue", (availableWidth / 2f) - (core.getTextBounds("Hit 'enter' to continue").getWidth() / 2f), startY + ((int) (totalHeight - (lineHeight - 6))));
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
        if (keyCode == KeyEvent.VK_ENTER) {
            position = Math.min(wordsChunks.length - 1, position + 1);
            if (position == wordsChunks.length - 1)
                hide();
        }
    }

    public static String[][] splitArray(String[] arrayToSplit, int chunkSize) {
        if (chunkSize <= 0)
            return null;
        int rest = arrayToSplit.length % chunkSize;
        int chunks = arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0);
        String[][] arrays = new String[chunks][];
        for (int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++)
            arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
        if (rest > 0)
            arrays[chunks - 1] = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
        return arrays;
    }
}
