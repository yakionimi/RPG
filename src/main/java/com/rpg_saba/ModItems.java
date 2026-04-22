package com.rpg_saba;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.particle.ParticleTypes;
import com.rpg_saba.system.HpSystem;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import java.util.List;

public class ModItems {

    // ⭐ 登録しない（普通の紙を使う）
    public static void register() {}

    // ⭐ これをイベントで呼ぶ
    public static TypedActionResult<ItemStack> useSkill(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getStackInHand(hand);

        // 紙以外は無視
        if (stack.getItem() != Items.PAPER) {
            return TypedActionResult.pass(stack);
        }

        // 名前なし無視
        if (!stack.hasCustomName()) {
            return TypedActionResult.pass(stack);
        }

        String name = stack.getName().getString();

        // =========================
        // ⭐ 回復の書物
        // =========================
        if (name.equals("回復の書物")) {

            // クールタイム
            if (player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                return TypedActionResult.fail(stack);
            }

            if (world.isClient) {
                return TypedActionResult.success(stack);
            }

            ServerPlayerEntity sp = (ServerPlayerEntity) player;

            // MP消費
            if (!MPSystem.useMp(sp, 10)) {
                player.sendMessage(Text.literal("MPが足りない！"), true);
                return TypedActionResult.fail(stack);
            }

            // ===== 範囲回復 =====
            double radius = 5.0;

            List<PlayerEntity> players = world.getEntitiesByClass(
                    PlayerEntity.class,
                    new Box(
                            player.getX() - radius,
                            player.getY() - radius,
                            player.getZ() - radius,
                            player.getX() + radius,
                            player.getY() + radius,
                            player.getZ() + radius
                    ),
                    p -> true
            );

            for (PlayerEntity target : players) {

                int current = HpSystem.hp.getOrDefault(target.getUuid(), HpSystem.MAX_HP);
                int max = HpSystem.MAX_HP;

                int heal = 20;

                current += heal;
                if (current > max) current = max;

                HpSystem.hp.put(target.getUuid(), current);
            }

            // ===== パーティクル =====
            ServerWorld sw = (ServerWorld) world;

            sw.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    50,
                    1, 1, 1,
                    0.1
            );

            // ⭐ クールタイム（30秒）
            player.getItemCooldownManager().set(stack.getItem(), 600);

            player.sendMessage(Text.literal("範囲回復！"), true);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }
}