package com.rpg_saba;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.item.ItemStack;

public class SkillClickHandler {

    public static void register() {

        UseItemCallback.EVENT.register((player, world, hand) -> {

            ItemStack stack = player.getStackInHand(hand);

            if (world.isClient) return TypedActionResult.pass(stack);

            if (!(player instanceof ServerPlayerEntity sp))
                return TypedActionResult.pass(stack);

            if (stack.getItem() == Items.FEATHER) {

                if (SkillSystem.unlock(sp, "wing", 1)) {
                    SkillState.setActive(sp, true);
                    player.sendMessage(net.minecraft.text.Text.literal("羽スキル解放！"), false);
                } else {
                    player.sendMessage(net.minecraft.text.Text.literal("SP足りない"), false);
                }
            }

            return TypedActionResult.pass(stack);
        });
    }
}