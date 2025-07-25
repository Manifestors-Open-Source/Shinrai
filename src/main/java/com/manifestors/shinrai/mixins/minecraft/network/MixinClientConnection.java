package com.manifestors.shinrai.mixins.minecraft.network;

import com.manifestors.shinrai.client.Shinrai;
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
        Shinrai.INSTANCE.getEventManager().listenEvent(packetReadingEvent);

        if (packetReadingEvent.isCancelled())
            ci.cancel();
    }

}
