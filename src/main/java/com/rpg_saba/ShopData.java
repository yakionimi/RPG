package com.rpg_saba;

import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class ShopData {

    public static final List<ShopItem> WEAPON = List.of(

            // 欠片 → エメラルド
            new ShopItem(
                    named("ゾンビの欠片", new ItemStack(Items.GOLD_NUGGET, 10)),
                    new ItemStack(Items.AIR, 0),
                    named("§aゾンビの宝石", new ItemStack(Items.GOLD_INGOT, 1))
            ),

            // 欠片 → 鉄インゴット
            new ShopItem(
                    named("ゾンビの宝石", new ItemStack(Items.GOLD_INGOT, 9)),
                    named("ゾンビの歯", new ItemStack(Items.POINTED_DRIPSTONE, 1)),
                    unbreakable(named("§aゾンビの剣", new ItemStack(Items.STONE_SWORD, 1)))
            ),

            // 欠片 → 金インゴット
            new ShopItem(
                    named("スケルトンの欠片", new ItemStack(Items.IRON_NUGGET, 10)),
                    new ItemStack(Items.AIR, 0),
                    named("§sスケルトンの宝石", new ItemStack(Items.IRON_INGOT, 1))
            ),

            // 欠片 → 鉄の剣（壊れない）
            new ShopItem(
                    named("スケルトンの宝石", new ItemStack(Items.IRON_INGOT, 9)),
                    named("スケルトンの頭骨", new ItemStack(Items.SKELETON_SKULL, 1)),
                    unbreakable(named("§sスケルトン制の剣", new ItemStack(Items.IRON_SWORD, 1)))
            ),
            new ShopItem(
                    named("ドラウンドの欠片", new ItemStack(Items.SCUTE, 9)),
                    new ItemStack(Items.AIR,0),
                    named("§dドラウンドの宝石", new ItemStack(Items.TURTLE_HELMET, 1))
            ),
            new ShopItem(
                    named("よく見る瓶", new ItemStack(Items.SCUTE, 9)),
                    named("ドラウンドの骨", new ItemStack(Items.BONE, 1)),
                    named("回復の書物", new ItemStack(Items.FLOWER_BANNER_PATTERN, 1))
            )
    );

    public static final List<ShopItem> FOOD = List.of(
            new ShopItem(
                    named("エメラルド", new ItemStack(Items.EMERALD, 2)),
                    new ItemStack(Items.AIR, 0),
                    named("パン", new ItemStack(Items.BREAD, 5))
            )
    );

    public static final List<ShopItem> MOB = List.of(
            new ShopItem(
                    named("ゾンビの欠片", new ItemStack(Items.IRON_NUGGET, 5)),
                    new ItemStack(Items.AIR, 0),
                    named("エメラルド", new ItemStack(Items.EMERALD, 1))
            ),
            new ShopItem(
                    named("スケルトンの欠片", new ItemStack(Items.GOLD_NUGGET, 5)),
                    new ItemStack(Items.AIR, 0),
                    named("エメラルド", new ItemStack(Items.EMERALD, 1))
            ),
            new ShopItem(
                    named("ドラウンドの欠片", new ItemStack(Items.SCUTE, 4)),
                    new ItemStack(Items.AIR, 0),
                    named("エメラルド", new ItemStack(Items.EMERALD, 1))
            )
    );

    // ⭐ 名前だけ変える（判定には影響なし）
    private static ItemStack named(String name, ItemStack stack) {
        stack.setCustomName(Text.literal(name));
        return stack;
    }

    // ⭐ 壊れない
    private static ItemStack unbreakable(ItemStack stack) {
        stack.getOrCreateNbt().putBoolean("Unbreakable", true);
        return stack;
    }
}