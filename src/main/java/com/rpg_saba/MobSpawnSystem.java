package com.rpg_saba;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobSpawnSystem {

    // ⭐ 連続湧き防止
    private static Map<UUID, Boolean> triggered = new HashMap<>();

    public static void init() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                ServerWorld world = (ServerWorld) player.getWorld(); // ←これが重要

                BlockPos pos = player.getBlockPos().down();
                UUID id = player.getUuid();

                boolean isOnDiamond = world.getBlockState(pos).getBlock() == Blocks.DIAMOND_BLOCK;

                // ⭐ 初めて踏んだ時だけ発動
                if (isOnDiamond && !triggered.getOrDefault(id, false)) {

                    triggered.put(id, true);

                    EntityType.ZOMBIE.spawn(
                            world,
                            null,
                            null,
                            pos.up(),
                            net.minecraft.entity.SpawnReason.TRIGGERED,
                            true,
                            false
                    );
                }

                // ⭐ 離れたらリセット
                if (!isOnDiamond) {
                    triggered.put(id, false);
                }
            }
        });
    }
}