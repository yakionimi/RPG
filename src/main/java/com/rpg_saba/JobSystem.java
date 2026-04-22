package com.rpg_saba;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobSystem {

    private static final Map<UUID, String> jobs = new HashMap<>();

    public static void setJob(PlayerEntity player, String job) {
        jobs.put(player.getUuid(), job);
    }

    public static String getJob(PlayerEntity player) {
        return jobs.getOrDefault(player.getUuid(), "none");
    }
}