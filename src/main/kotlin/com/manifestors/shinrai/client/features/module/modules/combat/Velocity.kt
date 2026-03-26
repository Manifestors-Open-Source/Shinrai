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

package com.manifestors.shinrai.client.features.module.modules.combat

import com.manifestors.shinrai.client.event.annotations.InvokeEvent
import com.manifestors.shinrai.client.event.events.network.PacketReceivingEvent
import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket

class Velocity : Module(
    name = "Velocity",
    description = "Reduces velocity when you are taking damage. (a.k.a AntiKnockBack)",
    category = ModuleCategory.COMBAT,
    alternativeNames = arrayOf("AntiKnockBack", "AntiVelocity")
) {

    @InvokeEvent
    fun onPacketReading(event: PacketReceivingEvent) {
        val p = event.packet
        event.cancelled = p is EntityVelocityUpdateS2CPacket
    }

}
