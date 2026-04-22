package com.rpg_saba.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.MinecraftClient;

public class RpgDeathScreen extends Screen {

    public RpgDeathScreen() {
        super(Text.literal("You Died"));
    }

    @Override
    protected void init() {

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("リスポーン"),
                button -> {

                    if (client != null && client.player != null) {

                        client.player.requestRespawn();

                        // サーバー側HPリセット（雑だけど動く）
                        client.player.setHealth(client.player.getMaxHealth());

                        client.setScreen(null);
                    }

                }).dimensions(this.width / 2 - 50, this.height / 2, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        this.renderBackground(context);

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                "YOU DIED",
                this.width / 2,
                this.height / 2 - 40,
                0xFF0000
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}