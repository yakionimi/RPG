package com.rpg_saba.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class RpgSabaClient implements ClientModInitializer {

    public static final Identifier HP_SYNC =
            new Identifier("rpg_saba", "hp_sync");

    public static final Identifier FOOD_SYNC =
            new Identifier("rpg_saba", "food_sync");

    @Override
    public void onInitializeClient() {

        HpHUD.register();
        FoodHUD.register();

        ClientPlayNetworking.registerGlobalReceiver(HP_SYNC,
                (client, handler, buf, responseSender) -> {
                    int hp = buf.readInt();
                    client.execute(() -> HpClient.setHp(hp));
                });

        ClientPlayNetworking.registerGlobalReceiver(FOOD_SYNC,
                (client, handler, buf, responseSender) -> {
                    int food = buf.readInt();
                    client.execute(() -> FoodClient.setFood(food));
                });
    }
}