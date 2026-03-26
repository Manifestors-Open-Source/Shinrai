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

import com.manifestors.shinrai.client.event.EventManager;
import com.manifestors.shinrai.client.event.events.network.PacketReceivingEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/listener/PacketListener;accepts(Lnet/minecraft/network/packet/Packet;)Z", shift = At.Shift.AFTER), cancellable = true)
    private void injectPacketReadingHook(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        var packetReadingEvent = new PacketReceivingEvent(packet);
        EventManager.INSTANCE.listenEvent(packetReadingEvent);

        if (packetReadingEvent.getCancelled())
            ci.cancel();
    }

}
