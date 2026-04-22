package com.rpg_saba;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ItemUtil {

    public static ItemStack unbreakable(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putBoolean("Unbreakable", true);
        stack.setNbt(nbt);
        return stack;
    }
}