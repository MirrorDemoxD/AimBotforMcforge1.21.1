package com.example.aimbot;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    private static final int LEFT_MARGIN = 10;
    private static final int TOP_MARGIN = 10;

    @SubscribeEvent
    public void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;
        GuiGraphics gui = event.getGuiGraphics();
        boolean enabled = AimBotHandler.isEnabled();
        String text = "AimBot: " + (enabled ? "§aON" : "§cOFF");
        int color = enabled ? 0x55FF55 : 0xFF5555;
        gui.drawString(mc.font, text, LEFT_MARGIN + 1, TOP_MARGIN + 1, 0x000000);
        gui.drawString(mc.font, text, LEFT_MARGIN, TOP_MARGIN, color);
        RenderSystem.enableBlend();
        gui.fill(LEFT_MARGIN + mc.font.width(text) + 8, TOP_MARGIN - 2,
                LEFT_MARGIN + mc.font.width(text) + 14, TOP_MARGIN + mc.font.lineHeight + 2,
                enabled ? 0xFF00FF00 : 0xFFFF0000);
        RenderSystem.disableBlend();
    }
}
