package com.manifestors.shinrai.mixins.minecraft.entity;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.extras.NoPortalCooldown;
import com.manifestors.shinrai.client.module.modules.movement.NoJumpDelay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void onTickMovement(CallbackInfo ci) {
        if (Shinrai.INSTANCE.getModuleManager().isModuleEnabled(NoJumpDelay.class)) {
            ((MixinLivingEntityAccessor) (Object) this).setJumpingCooldown(0);


        }
        LivingEntity entity = MinecraftClient.getInstance().player;
        MixinLivingEntityAccessor accessor = (MixinLivingEntityAccessor)(Object) entity;
        int currentCooldown = accessor.getJumpingCooldown();
        System.out.println("JumpingCooldown: " + currentCooldown);
    }
}
