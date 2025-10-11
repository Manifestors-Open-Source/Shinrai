package com.manifestors.shinrai.client.event.events.network

import com.manifestors.shinrai.client.event.CancellableEvent
import net.minecraft.network.packet.Packet

class PacketReceivingEvent(
    val packet: Packet<*>
) : CancellableEvent()
