package com.rpg_saba.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.UUID;

public class HpSystem {

    public static final int MAX_HP = 100;

    public static HashMap<UUID, Integer> hp = new HashMap<>();
    public static HashMap<UUID, BlockPos> spawn = new HashMap<>();
    public static HashMap<UUID, StatusEffect> status = new HashMap<>();

    public static void tick(ServerPlayerEntity player) {

        UUID id = player.getUuid();
        int current = hp.getOrDefault(id, MAX_HP);

        // 💀 死亡処理
        if (current <= 0) {

            BlockPos pos = spawn.get(id);

            if (pos == null) {
                pos = player.getSpawnPointPosition();
            }
            if (pos == null) {
                pos = player.getServerWorld().getSpawnPos();
            }

            current = MAX_HP;

            player.teleport(
                    player.getServerWorld(),
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    player.getYaw(),
                    player.getPitch()
            );

            player.getHungerManager().setFoodLevel(20);
        }

        // ❤️ リジェネ
        if (player.age % 20 == 0) {

            StatusEffect st = status.getOrDefault(id, StatusEffect.NONE);

            if (st == StatusEffect.REGEN) {
                current += 3;
            } else if (current < MAX_HP) {
                current += 1;
            }

            // 毒
            if (st == StatusEffect.POISON) {
                current -= 2;
            }

            // 火傷
            if (st == StatusEffect.BURN) {
                current -= 1;
            }
        }

        if (current > MAX_HP) current = MAX_HP;

        hp.put(id, current);

        // 🔥 バニラHP固定（超重要）
        player.setHealth(player.getMaxHealth());
    }

    public static void setSpawn(UUID id, BlockPos pos) {
        spawn.put(id, pos);
    }

    public static StatusEffect getStatus(UUID id) {
        return status.getOrDefault(id, StatusEffect.NONE);
    }

    public static void setStatus(UUID id, StatusEffect effect) {
        status.put(id, effect);
    }
}