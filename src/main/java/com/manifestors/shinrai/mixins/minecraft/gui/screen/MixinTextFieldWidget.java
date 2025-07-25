package com.manifestors.shinrai.mixins.minecraft.gui.screen;

import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TextFieldWidget.class)
public class MixinTextFieldWidget {
    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V"))
    private void drawShinraiButtons(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
        TextFieldWidget widget = (TextFieldWidget)(Object)this;

        Identifier textureId = ShinraiAssets.getTextureId(
                widget.isHovered() ? "button_highlighted" : "button"
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
