package com.rpg_saba.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class FoodHUD {

    private static int food = 20;

    public static void setFood(int value) {
        food = value;
    }

    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null) return;

            drawContext.drawText(
                    client.textRenderer,
                    Text.literal("ST: " + food),
                    10,
                    25,
                    0x55FF55,
                    true
            );
        });
    }
}