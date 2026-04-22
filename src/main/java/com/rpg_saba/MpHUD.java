package com.rpg_saba;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class MpHUD {

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {

            context.drawText(
                    MinecraftClient.getInstance().textRenderer,
                    "MP: 100",
                    10,
                    30,
                    0x0000FF,
                    false
            );
        });
    }
}