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

package com.manifestors.shinrai.mixins.minecraft.gui.hud;

import com.manifestors.shinrai.client.event.EventManager;
import com.manifestors.shinrai.client.event.events.rendering.Rendering2DEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z", shift = At.Shift.AFTER, opcode = Opcodes.GETFIELD, ordinal = 1))
    private void renderEventHook(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        EventManager.INSTANCE.listenEvent(new Rendering2DEvent(context, this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight()));
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void rewritePotionEffectStatus(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ci.cancel();
    }

}