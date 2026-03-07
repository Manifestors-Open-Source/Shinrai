package com.manifestors.shinrai.mixins.minecraft.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.player.MovementFixEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class MixinEntity {

    @ModifyExpressionValue(method = "updateVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw()F"))
    private float hookMoveFixEvent(float original) {
        MovementFixEvent movementFixEvent = new MovementFixEvent();
        Shinrai.eventManager.listenEvent(movementFixEvent);
        var entity = (Entity) (Object) this;
        return movementFixEvent.getYaw() != 0f &&
                entity == MinecraftClient.getInstance().player ? movementFixEvent.getYaw() : original;
    }

}
