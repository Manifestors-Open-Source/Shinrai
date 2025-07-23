package com.manifestors.shinrai.mixins.minecraft.client.gui;

import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(SplashOverlay.class)
public class MixinSplashOverlay {

    @Unique
    private final Identifier mOpenSourceImage = ShinraiAssets.getTextureId("mopensource");

    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper;getArgb(IIII)I",ordinal = 0))
    private static int manifestorsBlack(int alpha, int red, int green, int blue){
        return ColorHelper.getArgb(0,0,0);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIFFIIIIIII)V", ordinal = 0))
    private void changeSplash1(DrawContext instance, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color) {
        drawSplash(instance);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIFFIIIIIII)V", ordinal = 1))
    private void changeSplash2(DrawContext instance, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color) {
        drawSplash(instance);
    }

    @Unique
    private void drawSplash(DrawContext context) {
        context.drawTexture(RenderLayer::getGuiTextured, mOpenSourceImage, MinecraftClient.getInstance().getWindow().getScaledWidth()/2 - 64, MinecraftClient.getInstance().getWindow().getScaledHeight()/2 - 64, 0, 0, 128, 128, 128, 128);
    }

}
