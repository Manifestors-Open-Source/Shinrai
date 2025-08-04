package com.manifestors.shinrai.client.utils.rendering;

import com.manifestors.shinrai.client.utils.MinecraftInstance;
import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class BackgroundDrawer implements MinecraftInstance {

    public static boolean isCustomBgActivated = false;
    public static Identifier customBgId;

    public static void drawBackground(DrawContext context, String backgroundName) {
        var backgroundImage = isCustomBgActivated ? customBgId :
                ShinraiAssets.getBackgroundId(backgroundName);
        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
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
        var backgroundImage = isCustomBgActivated ? customBgId :
                ShinraiAssets.getBackgroundId("background");

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
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