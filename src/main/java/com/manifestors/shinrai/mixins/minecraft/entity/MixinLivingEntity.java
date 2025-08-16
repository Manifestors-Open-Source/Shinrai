package com.manifestors.shinrai.mixins.minecraft.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.movement.NoJumpDelay;
import com.manifestors.shinrai.client.module.modules.movement.SilkFall;
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
        return Shinrai.INSTANCE.getModuleManager().isModuleEnabled(NoJumpDelay.class) &&
                entity == MinecraftClient.getInstance().player ? 0 : original;
    }

        @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
        private void cancelFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
            if(Shinrai.INSTANCE.getModuleManager().isModuleEnabled(SilkFall.class)) {
                cir.setReturnValue(false);
            }
        }
    }



