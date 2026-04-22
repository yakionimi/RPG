package com.rpg_saba;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import com.rpg_saba.system.HpSystem;

import static net.minecraft.server.command.CommandManager.literal;

public class SetSpawnCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(literal("setspawn")
            .executes(context -> {

                ServerPlayerEntity player = context.getSource().getPlayer();

                BlockPos pos = player.getBlockPos();

                HpSystem.setSpawn(player.getUuid(), pos);

                player.sendMessage(Text.literal("リスポーン地点を設定した！"), false);

                return 1;
            })
        );
    }
}