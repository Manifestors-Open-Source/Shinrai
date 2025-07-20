package com.manifestors.shinrai.mixins.minecraft.client.network;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.TickEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "tickMovement", at = @At(value = "HEAD"))
    private void updateEventHook(CallbackInfo ci) {
        Shinrai.eventManager.listenEvent(new TickEvent());
    }

}
