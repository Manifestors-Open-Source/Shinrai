package com.manifestors.shinrai.mixins.minecraft.gui.hud;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.rendering.Rendering2DEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
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

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z", shift = At.Shift.AFTER))
    private void renderEventHook(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Shinrai.INSTANCE.getEventManager().listenEvent(new Rendering2DEvent(context, this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight()));
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void rewritePotionEffectStatus(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ci.cancel();
    }

}