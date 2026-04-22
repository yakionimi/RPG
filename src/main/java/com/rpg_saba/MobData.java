package com.rpg_saba.mob;

public class MobData {

    public String name;
    public int damage;
    public int maxHp;
    public boolean isBoss;

    public MobData(String name, int damage, int maxHp, boolean isBoss) {
        this.name = name;
        this.damage = damage;
        this.maxHp = maxHp;
        this.isBoss = isBoss;
    }
}