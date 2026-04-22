package com.rpg_saba.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class ClientInit implements ClientModInitializer {

    public static final Identifier OPEN_SKILL =
            new Identifier("rpg_saba", "open_skill");

    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(OPEN_SKILL,
                (client, handler, buf, responseSender) -> {

                    client.execute(() -> {
                        MinecraftClient.getInstance().setScreen(new SkillScreen());
                    });

                });
    }
}