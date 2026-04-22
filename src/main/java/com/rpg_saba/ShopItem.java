package com.rpg_saba;

import net.minecraft.item.ItemStack;

public class ShopItem {

    public final ItemStack costItem1;
    public final ItemStack costItem2;
    public final ItemStack sellItem;

    // ⭐ 追加（日本語表示用）
    public String displayName;
    public String costName1;
    public String costName2;

    public ShopItem(ItemStack costItem1, ItemStack costItem2, ItemStack sellItem) {
        this.costItem1 = costItem1;
        this.costItem2 = costItem2;
        this.sellItem = sellItem;
    }
}