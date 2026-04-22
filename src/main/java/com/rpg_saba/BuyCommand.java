package com.rpg_saba;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static net.minecraft.server.command.CommandManager.*;

import java.util.List;

public class BuyCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(literal("s")
                .then(argument("id", IntegerArgumentType.integer(1))
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            int id = IntegerArgumentType.getInteger(ctx, "id") - 1;

                            List<ShopItem> shop = ShopState.get(player);

                            if (shop == null || id >= shop.size()) {
                                player.sendMessage(Text.literal("選択されてない"), false);
                                return 0;
                            }

                            ShopItem item = shop.get(id);

                            // 素材チェック
                            if (!has(player, item.costItem1) ||
                                (item.costItem2.getItem() != Items.AIR && !has(player, item.costItem2))) {

                                player.sendMessage(Text.literal("素材不足"), false);
                                return 0;
                            }

                            // 削除
                            remove(player, item.costItem1);
                            if (item.costItem2.getItem() != Items.AIR) {
                                remove(player, item.costItem2);
                            }

                            // 付与
                            player.giveItemStack(item.sellItem.copy());
                            player.sendMessage(Text.literal("購入成功"), false);

                            return 1;
                        })
                ));
    }

    // ✅ ちゃんとクラスの中に入れる！！
    private static boolean has(ServerPlayerEntity player, ItemStack target) {

        int need = target.getCount();
        int total = 0;

        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() == target.getItem()) {
                total += stack.getCount();
            }
        }

        return total >= need;
    }

    // ✅ これも中！！
    private static void remove(ServerPlayerEntity player, ItemStack target) {

        int remaining = target.getCount();

        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() == target.getItem()) {

                int remove = Math.min(stack.getCount(), remaining);
                stack.decrement(remove);
                remaining -= remove;

                if (remaining <= 0) break;
            }
        }
    }
}