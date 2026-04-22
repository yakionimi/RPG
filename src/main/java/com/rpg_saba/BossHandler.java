package com.rpg_saba;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class BossHandler {

    public static void init() {

        ServerTickEvents.END_WORLD_TICK.register(world -> {

            for (var player : world.getPlayers()) {

                BlockPos pos = player.getBlockPos().down();

                if (world.getBlockState(pos).isOf(Blocks.WHITE_GLAZED_TERRACOTTA)) {

                    boolean exists = !world.getEntitiesByClass(
                            SkeletonEntity.class,
                            new Box(pos).expand(2),
                            e -> e.getCustomName() != null &&
                                    e.getCustomName().getString().equals("boss2")
                    ).isEmpty();

                    if (!exists) {
                        SkeletonEntity boss = EntityType.SKELETON.create(world);
                        if (boss != null) {
                            boss.setCustomName(net.minecraft.text.Text.literal("boss2"));
                            boss.refreshPositionAndAngles(
                                    pos.getX() + 0.5,
                                    pos.getY() + 1,
                                    pos.getZ() + 0.5,
                                    0, 0
                            );
                            world.spawnEntity(boss);
                        }
                    }
                }
            }
        });
    }
}