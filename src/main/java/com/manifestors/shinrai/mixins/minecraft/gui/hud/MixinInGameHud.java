package com.manifestors.shinrai.mixins.minecraft.gui.hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.rendering.Rendering2DEvent;
import com.manifestors.shinrai.client.module.modules.combat.config.SwordBlockingConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z", shift = At.Shift.AFTER))
    private void renderEventHook(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Shinrai.INSTANCE.getEventManager().listenEvent(new Rendering2DEvent(context, MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight()));
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"), method = "renderHotbar")
    public ItemStack swordBlocking$hideOffHandSlot(ItemStack original) {
        if (SwordBlockingConfig.enabled && SwordBlockingConfig.hideOffhandSlot && original.getItem() instanceof ShieldItem) {
            return ItemStack.EMPTY;
        } else {
            return original;
        }
    }

}

