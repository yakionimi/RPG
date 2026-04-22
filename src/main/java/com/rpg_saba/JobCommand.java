package com.rpg_saba;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class JobCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("job")
                .then(CommandManager.literal("warrior").executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    JobSystem.setJob(player, "warrior");
                    player.sendMessage(Text.literal("職業: warrior"), false);

                    return 1;
                }))
                .then(CommandManager.literal("mage").executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    JobSystem.setJob(player, "mage");
                    player.sendMessage(Text.literal("職業: mage"), false);

                    return 1;
                }))
        );
    }
}