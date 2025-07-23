package com.manifestors.shinrai.mixins.minecraft.client.gui.screen;

import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(TextFieldWidget.class)
public class MixinTextFieldWidget {
    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V"))
    private void drawShinraiButtons(DrawContext instance, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, int width, int height) {
        TextFieldWidget widget = (TextFieldWidget)(Object)this;

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
