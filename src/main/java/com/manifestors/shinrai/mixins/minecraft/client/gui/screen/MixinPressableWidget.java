package com.manifestors.shinrai.mixins.minecraft.client.gui.screen;

import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PressableWidget.class)
public class MixinPressableWidget{

    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIIII)V"))
    private void drawShinraiButtons(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, int color) {
        PressableWidget widget = (PressableWidget)(Object)this;

        Identifier textureId = ShinraiAssets.getIdFromTexturesFolder(
                widget.isHovered() ? "button_highlighted.png" : "button.png"
        );


        instance.drawTexture(
                pipeline,
                textureId,
                x,
                y,
                0F,
                0F,
                width,
                height,
                width,
                height
        );
    }

}