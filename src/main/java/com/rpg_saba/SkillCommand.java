package com.rpg_saba;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SkillCommand {

    public static final Identifier OPEN_SKILL =
            new Identifier("rpg_saba", "open_skill");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("skill")
            .executes(ctx -> {

                ServerPlayerEntity player = ctx.getSource().getPlayer();

                // クライアントにGUI開けと送信
                ServerPlayNetworking.send(player, OPEN_SKILL, PacketByteBufs.create());

                return 1;
            })
        );
    }
}