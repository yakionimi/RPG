package com.rpg_saba;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class MPSystem {

    private static final Map<UUID, Integer> mpMap = new HashMap<>();
    public static final int MAX_MP = 100;

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                UUID id = player.getUuid();

                mpMap.putIfAbsent(id, MAX_MP);

                int mp = mpMap.get(id);

                // 自然回復
                if (player.age % 20 == 0 && mp < MAX_MP) {
                    mpMap.put(id, mp + 1);
                }

                // 同期（なくてもOK）
                // MpSync.send(player, mpMap.get(id));
            }
        });
    }

    // ⭐ MP取得
    public static int getMP(net.minecraft.entity.player.PlayerEntity player) {
        return mpMap.getOrDefault(player.getUuid(), MAX_MP);
    }

    // ⭐ MP消費
    public static boolean useMp(ServerPlayerEntity player, int cost) {
        UUID id = player.getUuid();
        int mp = mpMap.getOrDefault(id, MAX_MP);

        if (mp < cost) return false;

        mpMap.put(id, mp - cost);
        return true;
    }
}