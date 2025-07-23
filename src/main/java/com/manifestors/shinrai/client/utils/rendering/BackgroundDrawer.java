package com.manifestors.shinrai.client.utils.rendering;

import com.manifestors.shinrai.client.utils.MinecraftInstance;
import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;

public class BackgroundDrawer implements MinecraftInstance {

    public static void drawBackground(DrawContext context, String backgroundName) {
        var backgroundImage = ShinraiAssets.getBackgroundId(backgroundName);
        context.drawTexture(
                RenderLayer::getGuiTextured,
                backgroundImage,
                0,
                0,
                0,
                0,
                mc.getWindow().getScaledWidth(),
                mc.getWindow().getScaledHeight(),
                mc.getWindow().getScaledWidth(),
                mc.getWindow().getScaledHeight()
        );
    }

    public static void drawBackground(DrawContext context) {
        var backgroundImage = ShinraiAssets.getBackgroundId("background");
        context.drawTexture(
                RenderLayer::getGuiTextured,
                backgroundImage,
                0,
                0,
                0,
                0,
                mc.getWindow().getScaledWidth(),
                mc.getWindow().getScaledHeight(),
                mc.getWindow().getScaledWidth(),
                mc.getWindow().getScaledHeight()
        );
    }

}