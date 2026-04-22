package com.rpg_saba;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.network.PacketByteBuf;
import io.netty.buffer.Unpooled;

public class HpSync {

    public static final Identifier ID = new Identifier("rpg_saba", "hp_sync");

    public static void send(ServerPlayerEntity player, int hp) {

        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(hp);

        ServerPlayNetworking.send(player, ID, buf);
    }
}