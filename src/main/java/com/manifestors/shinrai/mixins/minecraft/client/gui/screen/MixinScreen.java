package com.manifestors.shinrai.mixins.minecraft.client.gui.screen;

import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class MixinScreen {

    @Inject(method = "renderBackgroundTexture", at = @At("HEAD"), cancellable = true)
    private static void showShinraiBackground(DrawContext context, Identifier texture, int x, int y, float u, float v, int width, int height, CallbackInfo ci) {
        BackgroundDrawer.drawBackgroundTexture(context);
        ci.cancel();
    }



}
