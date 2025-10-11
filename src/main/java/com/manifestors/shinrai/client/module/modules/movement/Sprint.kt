package com.manifestors.shinrai.client.module.modules.movement

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory

class Sprint : Module(
    name = "Sprint",
    description = "Automatically sprints for you.",
    category = ModuleCategory.MOVEMENT,
    alternativeNames = arrayOf("AutoSprint")
) {

    @ListenEvent
    fun onTickMovement(event: TickMovementEvent) {
        mc.player!!.isSprinting = true
    }

}
