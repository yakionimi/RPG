package com.rpg_saba;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class WingRenderer {

    public static void register() {

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {

            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;

            // ❌ ServerPlayerEntityにキャストしない！
            if (!SkillState.isActive(player)) return;

            // ここに描画処理（今は省略）
        });
    }
}