package com.manifestors.shinrai.mixins.minecraft.client.gui.screen;

import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class MixinScreen {

    @Inject(method = "renderPanoramaBackground", at = @At(value = "HEAD"), cancellable = true)
    private void showShinraiBackground(DrawContext context, float deltaTicks, CallbackInfo ci) {
        BackgroundDrawer.drawBackground(context);
        ci.cancel();
    }

}
