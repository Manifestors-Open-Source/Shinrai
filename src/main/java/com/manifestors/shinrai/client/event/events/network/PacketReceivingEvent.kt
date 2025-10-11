package com.manifestors.shinrai.client.event.events.network

import com.manifestors.shinrai.client.event.CancellableEvent
import lombok.AllArgsConstructor
import lombok.Getter
import net.minecraft.network.packet.Packet

class PacketReceivingEvent(
    val packet: Packet<*>
) : CancellableEvent()
