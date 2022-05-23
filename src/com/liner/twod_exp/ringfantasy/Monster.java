package com.liner.twod_exp.ringfantasy;

public class Monster {
    public String name;
    public int hp;
    public int attack;
    public int defence;
    public int fireSpellResistance;
    public int waterSpellResistance;
    public int critAttack;
    public int rewardXP;
    public int rewardCoins;

    public Monster(String name, int hp, int attack, int defence, int fireSpellResistance, int waterSpellResistance, int critAttack, int rewardXP, int rewardCoins) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
        this.fireSpellResistance = fireSpellResistance;
        this.waterSpellResistance = waterSpellResistance;
        this.critAttack = critAttack;
        this.rewardXP = rewardXP;
        this.rewardCoins = rewardCoins;
    }
}
