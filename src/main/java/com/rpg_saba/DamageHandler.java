package com.rpg_saba.system;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class DamageHandler {

    public static void init() {

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (!(entity instanceof ServerPlayerEntity player)) return true;

            applyDamage(player, (int) amount, DamageType.NORMAL);

            return false; // バニラダメージ無効
        });
    }

    public static void applyDamage(ServerPlayerEntity player, int damage, DamageType type) {

        UUID id = player.getUuid();

        int current = HpSystem.hp.getOrDefault(id, HpSystem.MAX_HP);

        float defense = getDefense(player);

        // 種類補正
        switch (type) {
            case FALL -> damage *= 1.2;
            case FIRE -> damage *= 1.1;
            case POISON -> damage *= 0.8;
        }

        // 防御軽減
        damage = (int)(damage * (1.0f - defense));

        current -= damage;

        HpSystem.hp.put(id, current);
    }

    private static float getDefense(ServerPlayerEntity player) {
        int armor = player.getArmor();
        return Math.min(armor * 0.04f, 0.8f);
    }
}