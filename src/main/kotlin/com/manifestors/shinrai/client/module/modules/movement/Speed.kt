package com.manifestors.shinrai.client.module.modules.movement

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory

class Speed : Module(
    name = "Speed",
    category = ModuleCategory.MOVEMENT
) {
    private var airTicks = 0
    private var speed = 0.0

    @ListenEvent
    fun onTickMovement(event: TickMovementEvent) {
        if (!mc.player!!.isOnGround) {
            speed *= 0.9999999999999999
            airTicks++
            if (airTicks == 3) mc.player!!.setVelocity(
                mc.player!!.velocity.x,
                -0.0784000015258789,
                mc.player!!.velocity.z
            )
        } else {
            airTicks = 0
            speed = 0.3444567

            mc.player!!.jump()
        }

        //MovementUtils.applyStrafe((float) speed)
    }
}
