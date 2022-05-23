package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Battle {
    private final Context context;
    private final Player player;
    private final Monster monster;
    private final int monsterIndex;
    private final int availableWidth;
    private boolean running = false;
    private int selection = 0;
    private Random random;
    private final int monsterFullHP;
    private int monsterHP;
    private int lastPlayerDamage = -1;
    private int lastMonsterDamage = -1;
    private boolean monsterAttacking = false;

    public Battle(Context context, int monsterIndex) {
        this.context = context;
        this.random = new Random();
        this.player = context.getPlayer();
        this.monsterIndex = monsterIndex;
        this.monster = Resource.monsters[monsterIndex];
        this.availableWidth = RingRender.tileSize * 13;
        this.monsterHP = monster.hp;
        this.monsterFullHP = monster.hp;
    }

    private void onSelected(int selection) {
        switch (selection) {
            case 0 -> {
                if (monsterAttacking)
                    break;
                lastPlayerDamage = playerTurn();
                monsterAttacking = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (isMonsterDefeated()) {
                            int mapData = context.getMap().getData(player) % 100;
                            if (mapData == 38)
                                mapData = 13;
                            if ((context.getMap().getData(player) % 10000) / 100 == 11)
                                context.getMap().setData(mapData, player);


                            if (monsterIndex == 14) {
                                context.getMap().setDataAt(8, 3, 4);
                                context.getMap().setDataAt(994408, 4, 6);
                                player.attackMultiplier = 0;
                                running = false;
                                //Rings.TextFileInfo(Rings.mapCanvas, "King Lionel", "king2.txt");
                                return;
                            }
                            running = false;
                            String text = monster.name + " is defeated. Exp: +" + monster.rewardXP + " ,Coins: +" + monster.rewardCoins;
                            player.xp += monster.rewardXP;
                            player.coins += monster.rewardCoins;
                            if (mapData == 13) {
                                player.attackMultiplier++;
                                text = text + " ,Blood Stone: STR +4";
                            }
                            Message message = new Message(text, 0, context.getHud().getHeight());
                            context.setMessage(message);
                            message.show();
                            player.attackMultiplier = 0;
                            return;
                        }
                        lastMonsterDamage = monsterTurn();
                        if (isPlayerDefeated()) {
                            player.score = Math.max(0, player.score - 200);
                            running = false;
                            return;
                        }
                        monsterAttacking = false;
                    }
                }, 1000);
            }
            case 3 -> running = false;
        }
    }




    public int playerTurn() {
        int playerAttack = Math.max(1,
                player.attack - monster.defence) +
                ((player.equippedRing == 4 || player.equippedRing == 5) ?
                        (player.level * random.nextInt(100)) / 99 :
                        0
                );
        playerAttack += Math.max(1, (playerAttack * random.nextInt(100)) / 99);
        if (playerAttack % 2 > 0)
            playerAttack++;
        if (random.nextInt(100) < 20 * (player.attackMultiplier + 1)) {
            playerAttack += (player.attackMultiplier * 2);
        } else {
            playerAttack /= 2;
        }
        monsterHP = Math.max(0, monsterHP - playerAttack);
        return playerAttack;
    }

    public int monsterTurn() {
        int monsterAttack = Math.max(1, (monster.attack - player.defence) - ((player.equippedRing == 4 || player.equippedRing == 5) ? (player.level * random.nextInt(100)) / 99 : 0));
        monsterAttack += Math.max(1, (monsterAttack * random.nextInt(100)) / 99);
        if (monsterAttack % 2 > 0)
            monsterAttack++;
        if (random.nextInt(100) > monster.critAttack)
            monsterAttack /= 2;

        player.hp = Math.max(0, player.hp - monsterAttack);
        return monsterAttack;
    }

    public boolean isMonsterDefeated() {
        return monsterHP <= 0;
    }

    public boolean isPlayerDefeated() {
        return player.hp <= 0;
    }


    public void draw(ECore core) {
        if (!isRunning()) return;
        core.setColor(new Color(0, 0, 0, 218));
        core.getGraphics().fillRoundRect(0, getY(0), availableWidth, 120, 16, 16);
        core.getGraphics().setColor(new Color(3, 105, 217));
        core.getGraphics().drawRoundRect(getX(1), getY(1), availableWidth - 3, 117, 8, 8);
        progressBar(core, Resource.monsterImages[monsterIndex], monster.name + " HP", monsterHP + "/" + monsterFullHP, monsterHP, monsterFullHP, getX(8), getY(8));
        progressBar(core, Resource.objectImages[43], player.name + " HP", player.hp + "/" + player.maxHP, player.hp, player.maxHP, getX(8), getY(32));
        if (lastMonsterDamage != -1 && !monsterAttacking) {
            textAndIcon(core, Resource.resize(Resource.objectImages[10], 10, 10), String.format("%s attacks %s width %s pt. damage", monster.name, player.name, lastMonsterDamage), getX(8), getY(56), new Color(190, 21, 21));
        } else if(lastPlayerDamage != -1){
            textAndIcon(core, Resource.resize(Resource.objectImages[28], 10, 10), String.format("%s attacks %s width %s pt. damage", player.name, monster.name, lastPlayerDamage), getX(8), getY(56), new Color(210, 210, 210));
        }
        selection(core, selection);
    }

    private void textAndIcon(ECore core, BufferedImage icon, String text, int positionX, int positionY, Color color) {
        core.draw(icon, positionX, positionY);
        core.setColor(color);
        Rectangle2D rectangle2D = core.getTextBounds(text);
        Rectangle2D.Double textBounds = new Rectangle2D.Double(positionX + icon.getWidth() * 1.1f, positionY, rectangle2D.getWidth(), rectangle2D.getHeight());
        core.drawCenterVerticalString(text, textBounds);
    }

    private void progressBar(ECore core, BufferedImage icon, String text, String textEnd, int value, int total, int startX, int startY) {
        core.setColor(new Color(210, 210, 210));
        core.getGraphics().setFont(new Font("Arial", Font.PLAIN, 10));
        core.draw(icon, startX, startY);
        Rectangle2D.Double startRect = new Rectangle2D.Double(startX + icon.getWidth() + 4, startY, core.getTextBounds(text).getWidth(), icon.getHeight());
        Rectangle2D.Double endRect = new Rectangle2D.Double((int) (availableWidth - core.getTextBounds(textEnd).getWidth() - icon.getWidth() / 2), startY, core.getTextBounds(textEnd).getWidth(), icon.getHeight());
        core.drawCenterVerticalString(text, startRect);
        core.drawCenterVerticalString(textEnd, endRect);
        startX += startRect.x + startRect.width;
        float percentage = ((float) value / total);
        if (percentage > 100) percentage = 100;
        core.setColor(new Color(32, 34, 45));
        core.getGraphics().fillRoundRect(startX + 4, startY + icon.getHeight() / 2 - 4, (int) Math.abs(startX - (availableWidth - core.getTextBounds(textEnd).getWidth() - icon.getWidth())), 8, 8, 8);
        core.setColor(new Color(190, 21, 21));
        core.getGraphics().fillRoundRect(startX + 4, startY + icon.getHeight() / 2 - 4, (int) (Math.abs(startX - (availableWidth - core.getTextBounds(textEnd).getWidth() - icon.getWidth())) * percentage), 8, 8, 8);
    }

    private void selection(ECore core, int selection) {
        FontMetrics fontMetrics = core.getGraphics().getFontMetrics();
        double lineHeight = fontMetrics.getHeight();
        int attackWidth = getWidth(core, "Attack");
        int itemWidth = getWidth(core, "Item");
        int spellWidth = getWidth(core, "Spell");
        int fleeWidth = getWidth(core, "Flee");
        int xPosition = 0;
        int yPosition = 0;
        switch (selection) {
            case 0 -> {
                xPosition = getX(12);
                yPosition = getY(82);
            }
            case 1 -> {
                xPosition = availableWidth - getX(14) * 2 - itemWidth;
                yPosition = getY(82);
            }
            case 2 -> {
                xPosition = getX(12);
                yPosition = getY((int) (82 + lineHeight));
            }
            case 3 -> {
                xPosition = availableWidth - getX(14) * 2 - fleeWidth;
                yPosition = getY((int) (82 + lineHeight));
            }
        }
        core.setColor(new Color(3, 105, 217));
        core.drawLine(xPosition, yPosition, xPosition, yPosition + 9);
        core.drawLine(xPosition + 1, yPosition + 1, xPosition + 1, yPosition + 8);
        core.drawLine(xPosition + 2, yPosition + 2, xPosition + 2, yPosition + 7);
        core.drawLine(xPosition + 3, yPosition + 3, xPosition + 3, yPosition + 6);
        core.drawLine(xPosition + 4, yPosition + 4, xPosition + 4, yPosition + 5);
        core.setColor(Color.WHITE);
        core.drawCenterVerticalString("Attack", new Rectangle2D.Double(getX(20), getY(82), attackWidth, lineHeight));
        core.drawCenterVerticalString("Item", new Rectangle2D.Double(availableWidth - getX(20) - itemWidth, getY(82), itemWidth, lineHeight));
        core.drawCenterVerticalString("Spell", new Rectangle2D.Double(getX(20), getY((int) (82 + lineHeight)), spellWidth, lineHeight));
        core.drawCenterVerticalString("Flee", new Rectangle2D.Double(availableWidth - getX(20) - fleeWidth, getY((int) (82 + lineHeight)), fleeWidth, lineHeight));
    }

    private int getWidth(ECore core, String text) {
        return (int) core.getTextBounds(text).getWidth();
    }




    public void keyEvent(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> selection = Math.max(0, selection - 1);
            case KeyEvent.VK_RIGHT -> selection = Math.min(3, selection + 1);
            case KeyEvent.VK_UP -> selection = Math.max(0, selection - 2);
            case KeyEvent.VK_DOWN -> selection = Math.min(3, selection + 2);
            case KeyEvent.VK_ENTER -> onSelected(selection);
        }
    }


    private int getX(int x) {
        return x;
    }

    private int getY(int y) {
        return (RingRender.tileSize * 13 + y);
    }

    public void start() {
        running = true;
    }

    public boolean isRunning() {
        return running;
    }
}
