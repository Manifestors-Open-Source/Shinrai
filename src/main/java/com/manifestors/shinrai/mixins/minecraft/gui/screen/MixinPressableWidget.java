/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.mixins.minecraft.gui.screen;

import com.manifestors.shinrai.client.file.ShinraiAssets;
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
    private void drawShinraiTextFields(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, int color) {
        PressableWidget widget = (PressableWidget)(Object)this;

        Identifier textureId = ShinraiAssets.INSTANCE.getTextureId(
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