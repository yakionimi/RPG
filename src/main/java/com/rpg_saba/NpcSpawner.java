package com.rpg_saba;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class NpcSpawner {

    public static void init() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (!(world instanceof ServerWorld)) return;

            for (var player : world.getPlayers()) {
                BlockPos pos = player.getBlockPos();

                if (world.getBlockState(pos.down()).isOf(Blocks.EMERALD_BLOCK)) {

                    // 近くにNPCいなければ生成
                    if (world.getEntitiesByClass(
                            ArmorStandEntity.class,
                            new Box(pos).expand(3),
                            e -> true
                    ).isEmpty()) {

                        spawnNpc((ServerWorld) world, pos);
                    }
                }
            }
        });
    }

    private static void spawnNpc(ServerWorld world, BlockPos pos) {
        ArmorStandEntity npc = EntityType.ARMOR_STAND.create(world);

        if (npc != null) {
            npc.refreshPositionAndAngles(
                    pos.getX() + 0.5,
                    pos.getY() + 1,
                    pos.getZ() + 0.5,
                    0, 0
            );

            npc.setCustomName(Text.literal("武器屋"));
            npc.setCustomNameVisible(true);

            npc.setNoGravity(true);
            npc.setInvulnerable(true);
            npc.setInvisible(false);

            world.spawnEntity(npc);
        }
    }
}