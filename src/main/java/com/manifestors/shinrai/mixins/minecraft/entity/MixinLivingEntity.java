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

package com.manifestors.shinrai.mixins.minecraft.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.player.JumpFixEvent;
import com.manifestors.shinrai.client.features.module.modules.movement.NoJumpDelay;
import com.manifestors.shinrai.client.features.module.modules.movement.SilkFall;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;jumpingCooldown:I", ordinal = 3))
    private int removeJumpCooldown(int original) {
        var entity = (LivingEntity) (Object) this;
        return NoJumpDelay.INSTANCE.getEnabled() &&
                entity == MinecraftClient.getInstance().player ? 0 : original;
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!SilkFall.INSTANCE.getEnabled());
    }

    @ModifyExpressionValue(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    private float hookJumpFixEvent(float original) {
        JumpFixEvent jumpFixEvent = new JumpFixEvent();
        Shinrai.eventManager.listenEvent(jumpFixEvent);
        var entity = (LivingEntity) (Object) this;
        return jumpFixEvent.getYaw() != 0f &&
                entity == MinecraftClient.getInstance().player ? jumpFixEvent.getYaw() : original;
    }

}