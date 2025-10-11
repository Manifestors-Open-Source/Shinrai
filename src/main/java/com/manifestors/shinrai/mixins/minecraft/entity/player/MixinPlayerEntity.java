package com.manifestors.shinrai.mixins.minecraft.entity.player;

import com.manifestors.shinrai.client.module.modules.combat.BackToOldPVP;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    public void onGetAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> cir) {
        if (BackToOldPVP.INSTANCE.getEnabled()) {
            cir.setReturnValue(1f);
        }
    }
}