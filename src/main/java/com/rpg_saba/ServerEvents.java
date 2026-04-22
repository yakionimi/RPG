package com.rpg_saba;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerEvents {

    public static void init() {

        // ✅ コマンド登録
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {

            BuyCommand.register(
                    server.getCommandManager().getDispatcher()
            );

        });

        // ✅ 毎tick処理
        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                
            }
        });
    }
}