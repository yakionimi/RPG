package com.rpg_saba;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import com.rpg_saba.system.HpSystem;
import com.rpg_saba.system.FoodSystem;

public class RpgSaba implements ModInitializer {

    @Override
    public void onInitialize() {

        MobSpawner.init();
        ModEntities.register();
        BossHandler.init();
        BlockSpawnVillager.init();
        VillagerClickHandler.init();
        ModItems.register();
        MPSystem.init();

        SkillClickHandler.register();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            BuyCommand.register(dispatcher);
            NextCommand.register(dispatcher);
            PrevCommand.register(dispatcher);
            SetSpawnCommand.register(dispatcher);
            SkillCommand.register(dispatcher);
            JobCommand.register(dispatcher);
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                HpSystem.tick(player);
                FoodSystem.tick(player);

            }
        });

        System.out.println("RPG Saba Started");
    }
}