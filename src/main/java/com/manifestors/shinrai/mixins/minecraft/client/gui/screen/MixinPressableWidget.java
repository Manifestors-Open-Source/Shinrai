package com.manifestors.shinrai.mixins.minecraft.client.gui.screen;

import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(PressableWidget.class)
public class MixinPressableWidget{

    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIIII)V"))
    private void drawShinraiTextFields(DrawContext instance, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, int width, int height, int color) {
        PressableWidget widget = (PressableWidget)(Object)this;

        Identifier textureId = ShinraiAssets.getTextureId(
                widget.isHovered() ? "button_highlighted" : "button"
        );

        instance.drawTexture(
                renderLayers,
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