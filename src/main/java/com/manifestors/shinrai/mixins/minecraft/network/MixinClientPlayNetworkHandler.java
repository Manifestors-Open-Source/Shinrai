package com.manifestors.shinrai.mixins.minecraft.network;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.player.ChatMessageSendEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void injectCommandListener(String content, CallbackInfo ci) {
        var messageEvent = new ChatMessageSendEvent(content);
        Shinrai.INSTANCE.getEventManager().listenEvent(messageEvent);
        Shinrai.INSTANCE.getCommandManager().processCommands(messageEvent);

        if (messageEvent.getCancelled())
            ci.cancel();
    }

}
