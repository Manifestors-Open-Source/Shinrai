package com.manifestors.shinrai.client.module.modules.combat

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.network.PacketReceivingEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket

class Velocity : Module(
    name = "Velocity",
    description = "Reduces velocity when you are taking damage. (a.k.a AntiKnockBack)",
    category = ModuleCategory.COMBAT,
    alternativeNames = arrayOf("AntiKnockBack", "AntiVelocity")
) {

    @ListenEvent
    fun onPacketReading(event: PacketReceivingEvent) {
        val p = event.packet
        event.cancelled = p is EntityVelocityUpdateS2CPacket
    }

}
