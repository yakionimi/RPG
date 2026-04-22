package com.rpg_saba;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockSpawnVillager {

    // ✅ 連続スポーン防止
    private static final Map<UUID, Long> cooldown = new HashMap<>();

    public static void init() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                BlockPos pos = player.getBlockPos().down();

                if (player.getWorld().getBlockState(pos).isOf(Blocks.EMERALD_BLOCK)) {
                    spawn(player, "weapon");
                }

                if (player.getWorld().getBlockState(pos).isOf(Blocks.LAPIS_BLOCK)) {
                    spawn(player, "food");
                }
                if (player.getWorld().getBlockState(pos).isOf(Blocks.REDSTONE_BLOCK)) {
                    spawn(player, "mob");
                }
            }
        });
    }

    private static void spawn(ServerPlayerEntity player, String type) {

        UUID id = player.getUuid();
        long now = System.currentTimeMillis();

        // ✅ 3秒クールタイム
        if (cooldown.containsKey(id) && now - cooldown.get(id) < 3000) return;

        cooldown.put(id, now);

        VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, player.getWorld());

        villager.refreshPositionAndAngles(
                player.getX(),
                player.getY(),
                player.getZ(),
                0,
                0
        );

        // 名前で識別
        villager.setCustomName(net.minecraft.text.Text.literal(type));
        villager.setCustomNameVisible(true);

        // ✅ 完全固定NPC化
        villager.setAiDisabled(true);
        villager.setNoGravity(true);
        villager.setInvulnerable(true);
        villager.setSilent(true);

        player.getWorld().spawnEntity(villager);
    }
}