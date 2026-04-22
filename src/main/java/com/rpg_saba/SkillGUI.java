package com.rpg_saba;

import net.minecraft.server.network.ServerPlayerEntity;

public class SkillGUI {

    public static void check(ServerPlayerEntity player) {

        if (SkillSystem.hasSkill(player, "heal")) {
            // 取得済み
        }

        if (!SkillSystem.hasSkill(player, "heal")) {
            // 未取得
        } else if (SkillSystem.hasSkill(player, "heal2")) {
            // 次段階
        }
    }
}