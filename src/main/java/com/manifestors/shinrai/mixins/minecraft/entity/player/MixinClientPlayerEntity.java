/*
 *
 *  * Copyright © 2026 Manifestors Open Source
 *  * License: GPL-3.0
 *  *
 *  * All code in this project is the property of the Manifestors Open Source team
 *  * and its contributors. If you use this code in any project, please provide proper attribution
 *  * and release your project under the GPL-3.0 license as well.
 *  *
 *  * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.mixins.minecraft.entity.player;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.player.EventState;
import com.manifestors.shinrai.client.event.events.player.MovementPacketsEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.features.module.modules.movement.NoSlow;
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
        motionEvent = new MovementPacketsEvent(player.getPos(), player.getYaw(), player.getPitch(), player.isOnGround(), EventState.PRE);
        Shinrai.INSTANCE.getEventManager().listenEvent(motionEvent);
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getX()D"))
    private double hookMotionEventPosX(ClientPlayerEntity instance) {
        return motionEvent.getPosition().x;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
    private double hookMotionEventPosY(ClientPlayerEntity instance) {
        return motionEvent.getPosition().y;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getZ()D"))
    private double hookMotionEventPosZ(ClientPlayerEntity instance) {
        return motionEvent.getPosition().z;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
    private boolean hookMotionEventIsOnGround(ClientPlayerEntity instance) {
        return motionEvent.getOnGround();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    private float hookMotionEventRotationYaw(ClientPlayerEntity instance) {
        return motionEvent.getYaw();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    private float hookMotionEventRotationPitch(ClientPlayerEntity instance) {
        return motionEvent.getPitch();
    }

    @Inject(method = "sendMovementPackets", at = @At("RETURN"))
    private void sendMovementPacketsPostHook(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        motionEvent = new MovementPacketsEvent(player.getPos(), player.getYaw(), player.getPitch(), player.isOnGround(), EventState.POST);
        Shinrai.INSTANCE.getEventManager().listenEvent(motionEvent);
    }

}