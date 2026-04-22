package com.rpg_saba;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class SkillSystem {

    private static final HashMap<UUID, Integer> points = new HashMap<>();
    private static final HashMap<UUID, Set<String>> skills = new HashMap<>();

    public static int getPoints(ServerPlayerEntity player) {
        return points.getOrDefault(player.getUuid(), 0);
    }

    public static void addPoints(ServerPlayerEntity player, int amount) {
        points.put(player.getUuid(), getPoints(player) + amount);
    }

    public static boolean unlock(ServerPlayerEntity player, String skill, int cost) {

        int sp = getPoints(player);
        if (sp < cost) return false;

        points.put(player.getUuid(), sp - cost);
        skills.computeIfAbsent(player.getUuid(), k -> new HashSet<>()).add(skill);

        return true;
    }

    public static boolean hasSkill(ServerPlayerEntity player, String skill) {
        return skills.getOrDefault(player.getUuid(), Collections.emptySet()).contains(skill);
    }
}