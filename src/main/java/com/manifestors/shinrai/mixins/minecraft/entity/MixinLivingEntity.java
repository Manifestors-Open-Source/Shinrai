package com.manifestors.shinrai.mixins.minecraft.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.movement.NoJumpDelay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;jumpingCooldown:I", ordinal = 3))
    private int removeJumpCooldown(int original) {
        var entity = (LivingEntity) (Object) this;
        return Shinrai.INSTANCE.getModuleManager().isModuleEnabled(NoJumpDelay.class) &&
                entity == MinecraftClient.getInstance().player ? 0 : original;
    }

}
