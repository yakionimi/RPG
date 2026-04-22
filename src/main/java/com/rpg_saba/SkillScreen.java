package com.rpg_saba.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class SkillScreen extends Screen {

    // ドラッグ
    private double offsetX = 0;
    private double offsetY = 0;
    private boolean dragging = false;
    private double lastMouseX;
    private double lastMouseY;

    private ButtonWidget rootBtn;
    private ButtonWidget healBtn;
    private ButtonWidget heal2Btn;

    public SkillScreen() {
        super(Text.literal("スキルツリー"));
    }

    @Override
    protected void init() {
        updateButtons();
    }

    private void updateButtons() {
        clearChildren();

        int baseX = width / 2 + (int)offsetX;
        int baseY = 80 + (int)offsetY; // 上スタート

        // 🎯 ノード座標（縦ツリー）
        int rootX = baseX - 50;
        int rootY = baseY;

        int healX = baseX - 50;
        int healY = baseY + 100;

        int heal2X = baseX - 50;
        int heal2Y = baseY + 200;

        // START
        rootBtn = ButtonWidget.builder(
                Text.literal("START"),
                b -> {}
        ).dimensions(rootX, rootY, 100, 20).build();

        // 回復Ⅰ
        healBtn = ButtonWidget.builder(
                Text.literal("回復Ⅰ (1SP)"),
                button -> ClientSkillData.unlock("heal", 1)
        ).dimensions(healX, healY, 100, 20).build();

        // 回復Ⅱ
        heal2Btn = ButtonWidget.builder(
                Text.literal("回復Ⅱ (2SP)"),
                button -> {
                    if (ClientSkillData.has("heal")) {
                        ClientSkillData.unlock("heal2", 2);
                    }
                }
        ).dimensions(heal2X, heal2Y, 100, 20).build();

        addDrawableChild(rootBtn);
        addDrawableChild(healBtn);
        addDrawableChild(heal2Btn);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        int baseX = width / 2 + (int)offsetX;
        int baseY = 80 + (int)offsetY;

        // ノード中心座標
        int rootX = baseX;
        int rootY = baseY;

        int healX = baseX;
        int healY = baseY + 100;

        int heal2X = baseX;
        int heal2Y = baseY + 200;

        // 🔥 線（状態で色変化）
        int color1 = 0xFFFFFFFF;
        int color2 = ClientSkillData.has("heal") ? 0xFF00FF00 : 0xFF888888;

        // root → heal
        drawLine(context, rootX, rootY + 20, healX, healY, color1);

        // heal → heal2
        drawLine(context, healX, healY + 20, heal2X, heal2Y, color2);

        // UI
        context.drawCenteredTextWithShadow(textRenderer, "スキルツリー", width / 2, 20, 0xFFFFFF);
        context.drawText(textRenderer,
                "SP: " + ClientSkillData.getPoints(),
                20, 20, 0xFFFF00, false);

        super.render(context, mouseX, mouseY, delta);
    }

    // 線描画
    private void drawLine(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 0; i <= steps; i++) {
            int x = x1 + dx * i / steps;
            int y = y1 + dy * i / steps;
            context.fill(x, y, x + 2, y + 2, color);
        }
    }

    // クリック開始
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    // ドラッグ
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            offsetX += mouseX - lastMouseX;
            offsetY += mouseY - lastMouseY;

            lastMouseX = mouseX;
            lastMouseY = mouseY;

            updateButtons();
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    // 離す
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}