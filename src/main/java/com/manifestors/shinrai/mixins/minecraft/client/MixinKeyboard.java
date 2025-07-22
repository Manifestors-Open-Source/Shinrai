package com.manifestors.shinrai.mixins.minecraft.client;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.game.KeyPressEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(method = "onKey", at = @At("HEAD"))
    private void keyPressEventHook(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (window == client.getWindow().getHandle()) {
            if (client.currentScreen == null && client.player != null && InputUtil.isKeyPressed(window, key) && action == 1) {
                var keyPressEvent = new KeyPressEvent(key);
                keyPressEvent.toggleModulesByKey();

                if (!keyPressEvent.isCancelled())
                    Shinrai.INSTANCE.getEventManager().listenEvent(keyPressEvent);
            }
        }
    }

}
