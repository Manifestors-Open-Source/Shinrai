package com.manifestors.shinrai.mixins.minecraft.entity.player;


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
        if (NoFOV.INSTANCE.getEnabled()) {
            cir.setReturnValue(1.0F);
            cir.cancel();
        }
    }

}