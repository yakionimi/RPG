package com.rpg_saba;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.UUID;

public class SkillState {

    private static final HashMap<UUID, Boolean> active = new HashMap<>();

    public static void setActive(ServerPlayerEntity player, boolean value) {
        active.put(player.getUuid(), value);
    }

    public static boolean isActive(PlayerEntity player) {
        return active.getOrDefault(player.getUuid(), false);
    }
}