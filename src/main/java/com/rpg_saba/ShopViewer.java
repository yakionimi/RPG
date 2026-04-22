package com.rpg_saba;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.item.Items;

import java.util.List;

public class ShopViewer {

    public static void show(ServerPlayerEntity player) {

        List<ShopItem> shop = ShopState.get(player);
        if (shop == null) {
            player.sendMessage(Text.literal("§cショップ未選択"), false);
            return;
        }

        int page = ShopState.getPage(player);
        int perPage = 5;

        int start = page * perPage;
        int end = Math.min(start + perPage, shop.size());

        // ===== タイトル =====
        player.sendMessage(Text.literal("§6=== ショップ Page " + (page + 1) + " ==="), false);
        player.sendMessage(Text.literal("§7クリックで購入できます！"), false);

        // ===== アイテム一覧 =====
        for (int i = start; i < end; i++) {

            ShopItem item = shop.get(i);
            int displayId = i + 1;

            // ⭐ 日本語名（なければバニラ）
            String sellName = (item.displayName != null)
                    ? item.displayName
                    : item.sellItem.getName().getString();

            String costName1 = (item.costName1 != null)
                    ? item.costName1
                    : item.costItem1.getName().getString();

            String costName2 = (item.costName2 != null)
                    ? item.costName2
                    : item.costItem2.getName().getString();

            Text line = Text.literal(
                    "§e[" + displayId + "] §f" +
                    sellName + " x" + item.sellItem.getCount() +
                    " §7← §b" +
                    costName1 + " x" + item.costItem1.getCount() +
                    (item.costItem2.getItem() != Items.AIR
                            ? " + " + costName2 + " x" + item.costItem2.getCount()
                            : "")
            ).setStyle(Style.EMPTY.withClickEvent(
                    new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/s " + displayId)
            ));

            player.sendMessage(line, false);
        }

        // ===== ページナビ =====
        Text nav = Text.literal("§7");

        if (page > 0) {
            nav = nav.copy().append(
                    Text.literal("§a[← 前へ] ")
                            .setStyle(Style.EMPTY.withClickEvent(
                                    new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p")
                            ))
            );
        }

        if (end < shop.size()) {
            nav = nav.copy().append(
                    Text.literal("§a[次へ →]")
                            .setStyle(Style.EMPTY.withClickEvent(
                                    new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/n")
                            ))
            );
        }

        player.sendMessage(nav, false);

        // ===== 操作説明 =====
        player.sendMessage(Text.literal("§7[数字]クリック → 購入"), false);
        player.sendMessage(Text.literal("§7[前へ/次へ]クリック → ページ移動"), false);
    }
}