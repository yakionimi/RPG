package com.rpg_saba;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.item.Item;
import net.minecraft.util.math.Box;

import java.util.*;

public class MobSpawner {

    private static final Map<BlockPos, SpawnerData> spawners = new HashMap<>();
    private static final Map<UUID, Boolean> active = new HashMap<>();
    private static final Map<UUID, String> mobTypes = new HashMap<>();
    private static final Set<UUID> alreadyDead = new HashSet<>();

    private static boolean bossSpawned = false;

    public static void init() {

        ServerTickEvents.END_WORLD_TICK.register(world -> {

            if (!(world instanceof ServerWorld)) return;
            ServerWorld serverWorld = (ServerWorld) world;

            for (ServerPlayerEntity player : serverWorld.getPlayers()) {

                UUID id = player.getUuid();
                BlockPos playerPos = player.getBlockPos();
                BlockPos under = playerPos.down();

                // ON
                if (serverWorld.getBlockState(under).isOf(Blocks.DIAMOND_BLOCK)) {
                    active.put(id, true);
                }

                // RESET
                if (serverWorld.getBlockState(under).isOf(Blocks.NETHERITE_BLOCK)) {
                    active.put(id, false);
                    bossSpawned = false;
                    spawners.clear();
                }

                if (!active.getOrDefault(id, false)) continue;

                // ===== スポナー処理 =====
                for (int x = -7; x <= 7; x++) {
                    for (int y = -5; y <= 5; y++) {
                        for (int z = -7; z <= 7; z++) {

                            BlockPos base = playerPos.add(x, y, z);

                            if (!isSpawnerBlock(serverWorld, base)) continue;

                            BlockPos spawnPos = base.up();

                            spawners.putIfAbsent(base, new SpawnerData());
                            SpawnerData data = spawners.get(base);

                            Entity current = null;
                            if (data.currentMob != null) {
                                current = serverWorld.getEntity(data.currentMob);
                            }

                            // 初回
                            if (data.firstSpawn) {
                                Entity e = spawnMob(serverWorld, base, spawnPos);
                                if (e != null) data.currentMob = e.getUuid();
                                data.firstSpawn = false;
                                data.timer = 0;
                                continue;
                            }

                            // 生きてる
                            if (current != null && current.isAlive()) {
                                data.timer = 0;
                                continue;
                            }

                            // 復活
                            data.timer++;
                            if (data.timer < 100) continue;

                            Entity e = spawnMob(serverWorld, base, spawnPos);
                            if (e != null) data.currentMob = e.getUuid();

                            data.timer = 0;
                        }
                    }
                }

                // ===== ドロップ =====
                for (LivingEntity entity : serverWorld.getEntitiesByClass(
                        LivingEntity.class,
                        new Box(playerPos).expand(20),
                        e -> true
                )) {

                    UUID uuid = entity.getUuid();

                    if (entity.getHealth() > 0) {
                        alreadyDead.remove(uuid);
                        continue;
                    }

                    if (alreadyDead.contains(uuid)) continue;
                    alreadyDead.add(uuid);

                    String type = mobTypes.get(uuid);
                    if (type == null) continue;

                    ItemStack drop = null;

                    switch (type) {
                        case "スケルトン兵":
                            drop = named("スケルトンの欠片", Items.IRON_NUGGET);
                            break;
                        case "ドラウンド兵":
                            drop = named("ドラウンドの欠片", Items.SCUTE);
                            break; 
                        
                            case "ウィッチ兵":
                            drop = named("よく見る瓶", Items.GLASS_BOTTLE);
                            break; 

                        case "ボスゾンビ":
                            drop = named("ゾンビの歯", Items.POINTED_DRIPSTONE);
                            break;case "ゾンビ兵":
                            drop = named("ゾンビの欠片", Items.GOLD_NUGGET);
                            break;
                        
                            case "ボスドラウンド":
                            drop = named("ドラウンドの骨", Items.BONE);
                            break;    

                        case "ボススケルトン":
                            drop = named("スケルトンの頭骨", Items.SKELETON_SKULL);
                            break;
                    }

                    if (drop != null) {
                        serverWorld.spawnEntity(new ItemEntity(
                                serverWorld,
                                entity.getX(),
                                entity.getY(),
                                entity.getZ(),
                                drop
                        ));
                    }
                }

                // バニラ削除
               serverWorld.getEntitiesByClass(
                ItemEntity.class,
                    new Box(playerPos).expand(20),
                    item -> !item.getStack().hasCustomName() // ←名前なし＝バニラだけ消す
                    ).forEach(Entity::discard);
            }
        });
    }

    private static boolean isSpawnerBlock(ServerWorld world, BlockPos pos) {
        return world.getBlockState(pos).isOf(Blocks.WHITE_CONCRETE)
                || world.getBlockState(pos).isOf(Blocks.LIGHT_GRAY_CONCRETE)
                || world.getBlockState(pos).isOf(Blocks.CYAN_CONCRETE)
                || world.getBlockState(pos).isOf(Blocks.BLUE_CONCRETE)
                || world.getBlockState(pos).isOf(Blocks.LIGHT_BLUE_CONCRETE)
                || world.getBlockState(pos).isOf(Blocks.BLUE_CONCRETE)
                || world.getBlockState(pos).isOf(Blocks.BLUE_CONCRETE_POWDER)
                || world.getBlockState(pos).isOf(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA)
                || world.getBlockState(pos).isOf(Blocks.WHITE_GLAZED_TERRACOTTA);

    }

    private static Entity spawnMob(ServerWorld world, BlockPos base, BlockPos spawnPos) {

        EntityType<?> type;
        String name;

        if (world.getBlockState(base).isOf(Blocks.WHITE_CONCRETE)) {
            type = EntityType.SKELETON;
            name = "スケルトン兵";

        } else if (world.getBlockState(base).isOf(Blocks.LIGHT_GRAY_CONCRETE)) {
            type = EntityType.ZOMBIE;
            name = "ゾンビ兵";

        } else if (world.getBlockState(base).isOf(Blocks.CYAN_CONCRETE)) {
            type = EntityType.DROWNED;
            name = "ドラウンド兵";

        } else if (world.getBlockState(base).isOf(Blocks.BLUE_CONCRETE)) {
            type = EntityType.WITCH;
            name = "ウィッチ兵";

        } else if (world.getBlockState(base).isOf(Blocks.LIGHT_BLUE_CONCRETE)) {
            type = EntityType.GLOW_SQUID;
            name = "イカチャン";

        } else if (world.getBlockState(base).isOf(Blocks.BLUE_CONCRETE_POWDER)) {
            if (bossSpawned) return null;
            type = EntityType.DROWNED;
            name = "ボスドラウンド";
            bossSpawned = true;

        } else if (world.getBlockState(base).isOf(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA)) {
            if (bossSpawned) return null;
            type = EntityType.ZOMBIE;
            name = "ボスゾンビ";
            bossSpawned = true;

        } else if (world.getBlockState(base).isOf(Blocks.WHITE_GLAZED_TERRACOTTA)) {
            if (bossSpawned) return null;
            type = EntityType.SKELETON;
            name = "ボススケルトン";
            bossSpawned = true;

        } else {
            return null;
        }

        Entity entity = type.create(world);

        if (entity != null) {

            entity.refreshPositionAndAngles(
                    spawnPos.getX() + 0.5,
                    spawnPos.getY(),
                    spawnPos.getZ() + 0.5,
                    0, 0
            );

            entity.setCustomName(Text.literal(name));
            entity.setCustomNameVisible(true);

            world.spawnEntity(entity);

            // ⭐ イカ攻撃（安全版）
            if (entity instanceof LivingEntity mob && name.equals("イカチャン")) {

                ServerTickEvents.END_WORLD_TICK.register(w -> {

                    if (mob.isRemoved() || mob.isDead()) return;

                    PlayerEntity target = mob.getWorld().getClosestPlayer(mob, 10);
                    if (target == null) return;

                    if (mob.age % 40 != 0) return;

                    InkProjectile proj = new InkProjectile(mob.getWorld(), mob);

                    double dx = target.getX() - mob.getX();
                    double dy = target.getBodyY(0.5) - proj.getY();
                    double dz = target.getZ() - mob.getZ();

                    proj.setVelocity(dx, dy, dz, 1.2f, 0);

                    // ⭐ 弾道パーティクル
                    for (int i = 0; i < 10; i++) {
                    double px = mob.getX() + dx * (i * 0.1);
                    double py = mob.getY() + 1 + dy * (i * 0.1);
                    double pz = mob.getZ() + dz * (i * 0.1);

                    ((ServerWorld) mob.getWorld()).spawnParticles(
                     ParticleTypes.CLOUD,
                     px, py, pz,
                     1,
                     0, 0, 0,
                     0
                );
            }           

mob.getWorld().spawnEntity(proj);
                });
            }

            mobTypes.put(entity.getUuid(), name);

            return entity;
        }

        return null;
    }

    private static ItemStack named(String name, Item item) {
        ItemStack stack = new ItemStack(item, 1);
        stack.setCustomName(Text.literal(name));
        return stack;
    }

    private static class SpawnerData {
        UUID currentMob = null;
        int timer = 0;
        boolean firstSpawn = true;
    }
}