package com.rpg_saba.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.network.PacketByteBuf;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import com.rpg_saba.client.RpgSabaClient;

public class FoodSystem {

    public static final int MAX_FOOD = 20;

    public static void tick(ServerPlayerEntity player) {

        int food = player.getHungerManager().getFoodLevel();

        // 🏃 ダッシュで消費（毎tick）
        if (player.isSprinting()) {
            if (food > 0) {
                player.getHungerManager().setFoodLevel(food - 1);
            } else {
                player.setSprinting(false);
            }
        }

        // 🍗 自然回復（1秒ごと）
        if (player.age % 20 == 0) {
            if (food < MAX_FOOD) {
                player.getHungerManager().setFoodLevel(food + 1);
            }
        }

        // 🍗 同期（クライアント用）
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(player.getHungerManager().getFoodLevel());
        ServerPlayNetworking.send(player, RpgSabaClient.FOOD_SYNC, buf);
    }
}