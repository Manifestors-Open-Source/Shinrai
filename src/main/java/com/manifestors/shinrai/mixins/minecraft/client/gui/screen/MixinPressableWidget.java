package com.manifestors.shinrai.mixins.minecraft.client.gui.screen;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PressableWidget.class)
public class MixinPressableWidget{
    @Unique
    private static final ButtonTextures TEXTURES = new ButtonTextures(Identifier.of("shinrai","textures/gui/sprites/widget"), Identifier.ofVanilla("widget/button_disabled"), Identifier.ofVanilla("widget/button_highlighted"));

   @Redirect(method = "renderWidget",at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/widget/PressableWidget;TEXTURES:Lnet/minecraft/client/gui/screen/ButtonTextures;",ordinal = 0))
   private static ButtonTextures butnMan(){
       return TEXTURES;
   }

}



