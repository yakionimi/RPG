package com.rpg_saba.system;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class HpSystem {

    public static final int MAX_HP = 100;

    public static HashMap<UUID, Integer> hp = new HashMap<>();
    public static HashMap<UUID, BlockPos> spawn = new HashMap<>();

    public static void tick(ServerPlayerEntity player) {

        UUID id = player.getUuid();
        int current = hp.getOrDefault(id, MAX_HP);

        float vanillaMax = player.getMaxHealth();

        // 🔥 絶対に0にさせない（最優先）
        if (player.getHealth() <= 0.5f) {
            player.setHealth(1.0f);
        }

        // バニラ → RPG変換
        int converted = (int)((player.getHealth() / vanillaMax) * MAX_HP);

        // ダメージ反映
        if (converted < current) {
            current = converted;
        }

        // 💀 RPG死亡処理
        if (current <= 0) {

            BlockPos pos = spawn.get(id);

            current = MAX_HP;

            // 完全回復
            player.setHealth(vanillaMax);
            player.getHungerManager().setFoodLevel(20);

            // テレポート
            if (pos != null) {
                player.teleport(
                        player.getServerWorld(),
                        pos.getX() + 0.5,
                        pos.getY(),
                        pos.getZ() + 0.5,
                        player.getYaw(),
                        player.getPitch()
                );
            }
        }

        // 自然回復
        if (player.age % 20 == 0 && current < MAX_HP) {
            current++;
        }

        hp.put(id, current);

        // 🔥 バニラに反映（0禁止）
        float scaled = (current / (float) MAX_HP) * vanillaMax;
        if (scaled <= 0) scaled = 1.0f;

        player.setHealth(scaled);
    }

    public static void setSpawn(UUID id, BlockPos pos) {
        spawn.put(id, pos);
    }
}