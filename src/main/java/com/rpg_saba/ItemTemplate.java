package com.rpg_saba;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.nbt.NbtCompound;

public class ItemTemplate {

    public static ItemStack create(String name, String id, ItemStack base, int count) {

        ItemStack stack = new ItemStack(base.getItem(), count);

        stack.setCustomName(Text.literal(name));

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putBoolean("rpg_item", true);
        nbt.putString("item_id", id);

        return stack;
    }
}