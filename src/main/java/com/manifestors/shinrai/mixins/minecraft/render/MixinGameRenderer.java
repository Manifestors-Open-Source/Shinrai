package com.manifestors.shinrai.mixins.minecraft.render;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.visuals.NoHurtCam;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void hookNoHurtCam(MatrixStack matrices, float tickProgress, CallbackInfo ci) {
        if (NoHurtCam.INSTANCE.getEnabled())
            ci.cancel();
    }

}
