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
