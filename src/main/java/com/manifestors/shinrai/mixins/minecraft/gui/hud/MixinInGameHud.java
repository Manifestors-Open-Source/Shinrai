package com.manifestors.shinrai.mixins.minecraft.gui.hud;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.rendering.Rendering2DEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Inject(method = "renderMainHud", at = @At(value = "HEAD"))
    private void renderHudEventHook(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Shinrai.INSTANCE.getEventManager().listenEvent(new Rendering2DEvent(context, MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight()));
    }

}
