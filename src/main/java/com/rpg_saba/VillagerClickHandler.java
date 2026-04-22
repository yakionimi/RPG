package com.rpg_saba;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

import java.util.List;

public class VillagerClickHandler {

    public static void init() {

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            if (world.isClient) return ActionResult.PASS;
            if (!(entity instanceof VillagerEntity villager)) return ActionResult.PASS;

            ServerPlayerEntity sp = (ServerPlayerEntity) player;

            String name = villager.getCustomName().getString();

            List<ShopItem> shop = null;

            if (name.equals("weapon")) {
                shop = ShopData.WEAPON;
            } else if (name.equals("food")) {
                shop = ShopData.FOOD;
            } else if (name.equals("mob")) {
                shop = ShopData.MOB;
            }

            if (shop == null) return ActionResult.PASS;

            ShopState.set(sp, shop);

            // 👇 表示をShopViewerに任せる
            ShopViewer.show(sp);

            return ActionResult.SUCCESS;
        });
    }
}