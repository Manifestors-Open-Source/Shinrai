package com.manifestors.shinrai.client.event.events.network;

import com.manifestors.shinrai.client.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.packet.Packet;

@Getter
@AllArgsConstructor
public class PacketReceivingEvent extends CancellableEvent {

    private final Packet<?> packet;

}
