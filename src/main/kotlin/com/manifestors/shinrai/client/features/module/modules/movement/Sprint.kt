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
