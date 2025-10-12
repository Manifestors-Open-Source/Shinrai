package com.manifestors.shinrai.mixins.minecraft.entity.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.player.EventState;
import com.manifestors.shinrai.client.event.events.player.MovementPacketsEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.modules.movement.NoSlow;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Unique
    private MovementPacketsEvent motionEvent;

    @Inject(method = "tickMovement", at = @At(value = "HEAD"))
    private void updateEventHook(CallbackInfo ci) {
        Shinrai.INSTANCE.getEventManager().listenEvent(new TickMovementEvent());
    }

    @Redirect(method = "applyMovementSpeedFactors", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec2f;multiply(F)Lnet/minecraft/util/math/Vec2f;", ordinal = 1))
    private Vec2f noSlowHook(Vec2f instance, float value) {
        return instance.multiply(NoSlow.INSTANCE.getEnabled() ? 1.0F : value);
    }

    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void sendMovementPacketsPreHook(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        motionEvent = new MovementPacketsEvent(EventState.PRE, player.getX(), player.getY(), player.getZ(), player.isOnGround());
    }

    @ModifyExpressionValue(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getX()D"))
    private double hookMotionEventPosX(double original) {
        return motionEvent.getX();
    }

    @ModifyExpressionValue(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
    private double hookMotionEventPosY(double original) {
        return motionEvent.getY();
    }

    @ModifyExpressionValue(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getZ()D"))
    private double hookMotionEventPosZ(double original) {
        return motionEvent.getZ();
    }

    @ModifyExpressionValue(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
    private boolean hookMotionEventIsOnGround(boolean original) {
        return motionEvent.getOnGround();
    }

    @Inject(method = "sendMovementPackets", at = @At("RETURN"))
    private void sendMovementPacketsPostHook(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        motionEvent = new MovementPacketsEvent(EventState.POST, player.getX(), player.getY(), player.getZ(), player.isOnGround());
    }

}