package com.rpg_saba.client;

import java.util.HashSet;
import java.util.Set;

public class ClientSkillData {

    private static int points = 5; // 初期SP
    private static final Set<String> skills = new HashSet<>();

    public static int getPoints() {
        return points;
    }

    public static boolean has(String skill) {
        return skills.contains(skill);
    }

    public static void unlock(String skill, int cost) {

        if (skills.contains(skill)) return;

        if (points < cost) {
            System.out.println("SP不足");
            return;
        }

        points -= cost;
        skills.add(skill);

        System.out.println(skill + " 解放！");
    }
}