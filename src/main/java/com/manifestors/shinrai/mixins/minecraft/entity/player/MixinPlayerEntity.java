package com.manifestors.shinrai.mixins.minecraft.entity.player;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.combat.BackToOldPVP;
import com.manifestors.shinrai.client.module.modules.extras.NoPortalCooldown;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    public void onGetAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> cir) {
        if (Shinrai.INSTANCE.getModuleManager().isModuleEnabled(BackToOldPVP.class)) {
            cir.setReturnValue(1f);
        }
    }
}
