package com.rpg_saba;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class BlackWingSkill {

    public static void activate(ServerPlayerEntity player) {

        // ON
        SkillState.setActive(player, true);

        player.sendMessage(Text.literal("黒翼スキル発動！"), false);
    }
}