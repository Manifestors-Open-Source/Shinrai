package com.manifestors.shinrai.mixins.minecraft.client.gui;

import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LogoDrawer.class)
public class MixinLogoDrawer {

    /**
     * @author Manifestor, meto1558
     * @reason Draw Shinrai's logo
     */
    @Overwrite
    public void draw(DrawContext context, int screenWidth, float alpha, int y) {
        Identifier logo = ShinraiAssets.getTextureId("smooth_logo");
        int logoWidth = 256;
        int logoHeight = 64;
        int logoX = screenWidth / 2 - logoWidth / 2;
        context.drawTexture(RenderLayer::getGuiTextured, logo, logoX, 10, 0F, 0F, logoWidth, logoHeight, logoWidth, logoHeight);
    }

}
