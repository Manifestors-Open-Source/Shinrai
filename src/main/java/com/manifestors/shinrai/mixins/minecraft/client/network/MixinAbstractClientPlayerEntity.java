package com.manifestors.shinrai.mixins.minecraft.client.network;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.visuals.NoFOV;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class MixinAbstractClientPlayerEntity {

    @Inject(method = "getFovMultiplier", at = @At(value = "HEAD"), cancellable = true)
    private void noFovHook(CallbackInfoReturnable<Float> cir) {
        if (Shinrai.INSTANCE.getModuleManager().isModuleEnabled(NoFOV.class)) {
            cir.setReturnValue(1.0F);
            cir.cancel();
        }
    }

}
