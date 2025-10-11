package com.manifestors.shinrai.mixins.minecraft.gui;

import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SplashOverlay.class)
public class MixinSplashOverlay {
    
    @Unique
    private final Identifier mOpenSourceImage = ShinraiAssets.INSTANCE.getTextureId("mopensource");

    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper;getArgb(IIII)I",ordinal = 0))
    private static int manifestorsBlack(int alpha, int red, int green, int blue){
        return ColorHelper.getArgb(0,0,0);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIFFIIIIIII)V", ordinal = 0))
    private void changeSplash1(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color) {
        instance.drawTexture(RenderPipelines.GUI_TEXTURED, mOpenSourceImage, MinecraftClient.getInstance().getWindow().getScaledWidth()/2 - 64, MinecraftClient.getInstance().getWindow().getScaledHeight()/2 - 64 , 0, 0, 128, 128, 128, 128);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIFFIIIIIII)V", ordinal = 1))
    private void changeSplash2(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color) {
        instance.drawTexture(RenderPipelines.GUI_TEXTURED, mOpenSourceImage, MinecraftClient.getInstance().getWindow().getScaledWidth()/2 - 64, MinecraftClient.getInstance().getWindow().getScaledHeight()/2 - 64, 0, 0, 128, 128, 128, 128);
    }

}
