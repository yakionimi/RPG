package com.rpg_saba;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class PrevCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(literal("p")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    ShopState.prev(player);

                    // 👇 修正ポイント
                    ShopViewer.show(player);

                    return 1;
                })
        );
    }
}