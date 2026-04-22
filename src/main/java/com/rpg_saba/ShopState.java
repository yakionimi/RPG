package com.rpg_saba;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopState {

    private static final Map<ServerPlayerEntity, List<ShopItem>> shopMap = new HashMap<>();
    private static final Map<ServerPlayerEntity, Integer> pageMap = new HashMap<>();

    public static void set(ServerPlayerEntity player, List<ShopItem> shop) {
        shopMap.put(player, shop);
        pageMap.put(player, 0);
    }

    public static List<ShopItem> get(ServerPlayerEntity player) {
        return shopMap.get(player);
    }

    public static int getPage(ServerPlayerEntity player) {
        return pageMap.getOrDefault(player, 0);
    }

    public static void setPage(ServerPlayerEntity player, int page) {
        pageMap.put(player, page);
    }

    // ✅ 次ページ
    public static void next(ServerPlayerEntity player) {
        int page = getPage(player);
        pageMap.put(player, page + 1);
    }

    // ✅ 前ページ
    public static void prev(ServerPlayerEntity player) {
        int page = getPage(player);
        if (page > 0) {
            pageMap.put(player, page - 1);
        }
    }
}