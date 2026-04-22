package com.rpg_saba.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class HpHUD {

    private static int hp = 100;

    public static void setHp(int value) {
        hp = value;
    }

    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null) return;

            drawContext.drawText(
                    client.textRenderer,
                    Text.literal("HP: " + hp),
                    10,
                    10,
                    0xFF5555,
                    true
            );
        });
    }
}