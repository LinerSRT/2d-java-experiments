package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Hud {
    private Player player;
    private int height;
    private int availableWidth;

    public Hud(Player player) {
        this.player = player;
        this.height = getY(48) ;
        this.availableWidth = RingRender.tileSize * 13 - 16;
    }


    private void drawProgress(ECore core, BufferedImage icon, String text, String textEnd, int value, int total, int startX, int startY, Color color) {
        core.setColor(new Color(210, 210, 210));
        core.getGraphics().setFont(new Font("Arial", Font.PLAIN, 10));
        core.draw(Resource.resize(icon, 10, 10), startX, startY);
        Rectangle2D.Double startRect = new Rectangle2D.Double(startX + 14, startY, core.getTextBounds(text).getWidth(), 10);
        Rectangle2D.Double endRect = new Rectangle2D.Double((int) (availableWidth - core.getTextBounds(textEnd).getWidth() + 8), startY, 16, 10);
        core.drawCenterVerticalString(text, startRect);
        core.drawCenterVerticalString(textEnd, endRect);
        startX += startRect.x + startRect.width;
        float percentage = ((float) value / total);
        if (percentage > 100)
            percentage = 100;
        core.setColor(new Color(32, 34, 45));
        core.getGraphics().fillRoundRect(startX + 4, startY + 2, (int) Math.abs(startX - (availableWidth - core.getTextBounds(textEnd).getWidth())), 6, 8, 8);
        core.setColor(color);
        core.getGraphics().fillRoundRect(startX + 4, startY + 2, (int) (Math.abs(startX - (availableWidth - core.getTextBounds(textEnd).getWidth())) * percentage), 6, 8, 8);
    }

    private int drawWithIcon(ECore core, BufferedImage icon, String text, int positionX, int positionY) {
        core.draw(icon, positionX, positionY);
        core.setColor(new Color(210, 210, 210));
        Rectangle2D rectangle2D = core.getTextBounds(text);
        Rectangle2D.Double textBounds = new Rectangle2D.Double(positionX + icon.getWidth() * 1.1f, positionY, rectangle2D.getWidth(), rectangle2D.getHeight());
        core.drawCenterVerticalString(text, textBounds);
        return (int) (icon.getWidth()+ textBounds.getWidth()) + 4;
    }

    public void draw(ECore core) {
        core.setColor(new Color(0, 0, 0, 218));
        core.getGraphics().fillRoundRect(0, getY(0), RingRender.tileSize * 13, 48, 16, 16);
        core.getGraphics().setColor(new Color(3, 105, 217));
        core.getGraphics().drawRoundRect(1, getY(1), RingRender.tileSize * 13 - 3, 45, 16, 16);
        drawProgress(core, Resource.objectImages[46], "HP", player.hp + "/" + player.maxHP, player.hp, player.maxHP, getX(4), getY(4), new Color(190, 21, 21));
        drawProgress(core, Resource.objectImages[44], "MP", player.mp + "/" + player.maxMP, player.mp, player.maxMP, getX(4), getY(16), new Color(3, 105, 217));
        int startX = getX(4);
        startX += drawWithIcon(core, Resource.resize(Resource.objectImages[50], 10, 10), player.attack + "", startX, getY(28));
        startX += drawWithIcon(core, Resource.resize(Resource.objectImages[52], 10, 10), player.strength + "", startX, getY(28));
        startX += drawWithIcon(core, Resource.resize(Resource.objectImages[51], 10, 10), player.defence + "", startX, getY(28));
        startX += drawWithIcon(core, Resource.resize(Resource.objectImages[47], 10, 10), player.keys + "", startX, getY(28));
        startX += drawWithIcon(core, Resource.resize(Resource.objectImages[45], 10, 10), player.coins + "", startX, getY(28));
        startX += drawWithIcon(core, Resource.resize(Resource.objectImages[49], 10, 10), player.level + "", startX, getY(28));
        startX += drawWithIcon(core, Resource.resize(Resource.objectImages[53], 10, 10), player.nextLevelXP + "", startX, getY(28));


        //core.draw(Resource.resize(Resource.objectImages[50], 10, 10), getX(4), getY(28));
        //core.setColor(new Color(210, 210, 210));
        //core.drawCenterVerticalString(player.attack + "", new Rectangle2D.Double(getX(4) + 12, getY(28), 10, 10));
//
//
//        int startY = 4;
//        int space = 14;
//        drawInfo(core, Resource.resize(Resource.objectImages[43], 16, 16), player.playerName, 4, startY, 12);
//        startY += space + 4;
//        drawInfo(core, Resource.resize(Resource.objectImages[45], 10, 10), String.format("%s", player.playerCoins), 6, startY, 11);
//        startY += space;
//        drawInfo(core, Resource.resize(Resource.objectImages[47], 10, 10), String.format("%s", player.playerKeyCount), 6, startY, 11);
//        startY += space;
//        drawInfo(core, Resource.resize(Resource.objectImages[46], 10, 10), String.format("%s", player.playerHP), 6, startY, 11);
//        startY += space;
//        drawInfo(core, Resource.resize(Resource.objectImages[44], 10, 10), String.format("%s", player.playerMP), 6, startY, 11);
//        startY += space;
    }

    private void drawInfo(ECore core, BufferedImage icon, String text, int x, int y) {
        core.draw(icon, getX(x), getY(y));
        core.setColor(Color.WHITE);
        Rectangle2D.Double rectangle2D = new Rectangle2D.Double(getX(x) + icon.getWidth() + 4, getY(y), Math.abs((getX(x) + icon.getWidth() + 4) - RingRender.tileSize), icon.getHeight());
        core.drawCenterVerticalString(text, rectangle2D);
    }

    private void drawInfo(ECore core, BufferedImage icon, String text, int x, int y, int fontSize) {
        core.getGraphics().setFont(new Font("Arial", Font.PLAIN, fontSize));
        drawInfo(core, icon, text, x, y);
    }

    private int getX(int x) {
        return 4 + x;
    }

    private int getY(int y) {
        return RingRender.tileSize * 13+ y;
    }

    public int getHeight() {
        return height;
    }
}
