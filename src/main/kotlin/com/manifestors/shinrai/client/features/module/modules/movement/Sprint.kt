package com.manifestors.shinrai.client.features.module.modules.movement

import com.manifestors.shinrai.client.event.annotations.InvokeEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory

class Sprint : Module(
    name = "Sprint",
    description = "Automatically sprints for you.",
    category = ModuleCategory.MOVEMENT,
    alternativeNames = arrayOf("AutoSprint")
) {

    @InvokeEvent
    fun onTickMovement(event: TickMovementEvent) {
        mc.player!!.isSprinting = true
    }

}
