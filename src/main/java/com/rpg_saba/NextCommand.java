package com.rpg_saba;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class NextCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(literal("n")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    ShopState.next(player);

                    // 👇 修正ポイント
                    ShopViewer.show(player);

                    return 1;
                })
        );
    }
}