package com.liner.twod_exp.ringfantasy;


import java.awt.image.BufferedImage;

public class Player {
    public int x;
    public int y;
    public int direction;
    public BufferedImage[] playerImages;

    public String name = "Elvin";
    public int keys = 0;
    public int coins = 0;
    public int hp = 20;
    public int maxHP = 20;
    public int hpMultiplier = 0;
    public int mp = 10;
    public int maxMP = 10;
    public int mpMultiplier = 0;
    public int strengthMultiplier = 0;
    public int attackMultiplier = 0;
    public int level = 1;
    public int xp = 0;
    public int nextLevelXP = 0;
    public int attack = 10;
    public int defence = 10;
    public int strength = 10;
    public int equippedSword = 0;
    public int equippedArmor = 0;
    public int equippedRing = 0;
    public int score = 0;


    public Player(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.playerImages = new BufferedImage[4];
        System.arraycopy(Resource.objectImages, 40, playerImages, 0, 4);
        update();
    }

    public void update(){
        level = GetHeroLevel();
        nextLevelXP = GetNextLevelExp();
        maxHP = GetHeroMaxHP();
        maxMP = GetHeroMaxMP();
        attack = GetHeroATT() * 10000;
        defence= GetHeroDEF();
        strength = GetHeroSTR();
    }

    private int GetHeroLevel() {
        int i = this.xp;
        int j = 1;
        for (int k = 600; i >= k; k += 200) {
            j++;
            i -= k;
        }
        return j;
    }

    private int GetNextLevelExp() {
        int i = this.xp;
        int j = 1;
        int k = 600;
        while (i >= k) {
            j++;
            i -= k;
            k += 200;
        }
        return k - i;
    }

    private int GetHeroMaxHP() {
        return 20 + ((GetHeroLevel() - 1) * 4) + (8 * this.strengthMultiplier);
    }

    private int GetHeroMaxMP() {
        return 10 + ((GetHeroLevel() - 1) * 2) + (8 * this.mpMultiplier);
    }


    public int GetHeroSTR() {
        int i = 10 + (GetHeroLevel() - 1) + (2 * strengthMultiplier);
        if (equippedRing == 5) {
            i += GetHeroLevel();
        }
        return i;
    }

    public int GetHeroATT() {
        int i = GetHeroSTR();
        if (equippedSword > 0) {
            i += Resource.weaponAgility[equippedSword];
        }
        if (equippedRing == 4) {
            i += GetHeroLevel();
        }
        return i;
    }

    public int GetHeroDEF() {
        int i = GetHeroSTR();
        if (this.equippedArmor > 0) {
            i += Resource.armorAgility[this.equippedArmor];
        }
        if (equippedRing == 3) {
            i += GetHeroLevel();
        }
        return i;
    }




//
//    public int GetHeroSTR() {
//        int i = 10 + (getPlayerLevel() - 1) + (2 * playerStrengthMultiplier) + (4 * playerAttackMultiplier);
//        if (equippedRing == 5) {
//            i += getPlayerLevel();
//        }
//        return i;
//    }
//
//    public int GetHeroATT() {
//        int i = GetHeroSTR();
//        if (this.equippedSword > 0) {
//            i += Resource.weaponAgility[equippedSword];
//        }
//        if (equippedRing == 4) {
//            i += getPlayerLevel();
//        }
//        return i;
//    }
//
//    public int GetHeroDEF() {
//        int i = GetHeroSTR();
//        if (this.equippedArmor > 0) {
//            i += Resource.armorAgility[equippedArmor];
//        }
//        if (this.equippedRing == 3) {
//            i += getPlayerLevel();
//        }
//        return i;
//    }

}
