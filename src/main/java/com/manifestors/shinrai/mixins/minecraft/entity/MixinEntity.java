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
import com.manifestors.shinrai.client.event.events.player.MovementFixEvent;
import com.manifestors.shinrai.client.features.module.modules.visuals.ESP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    private void hookESP(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity && !(entity instanceof ClientPlayerEntity) && ESP.INSTANCE.getEnabled()) {
            cir.setReturnValue(true);
        }
    }

}
