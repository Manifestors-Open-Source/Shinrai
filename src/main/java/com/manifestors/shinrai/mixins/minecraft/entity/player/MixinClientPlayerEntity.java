package com.manifestors.shinrai.mixins.minecraft.entity.player;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.modules.movement.NoSlow;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "tickMovement", at = @At(value = "HEAD"))
    private void updateEventHook(CallbackInfo ci) {
        Shinrai.INSTANCE.getEventManager().listenEvent(new TickMovementEvent());
    }

    @Redirect(method = "applyMovementSpeedFactors", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec2f;multiply(F)Lnet/minecraft/util/math/Vec2f;", ordinal = 1))
    private Vec2f noSlowHook(Vec2f instance, float value) {
        return instance.multiply(NoSlow.INSTANCE.getEnabled() ? 1.0F : value);
    }

}