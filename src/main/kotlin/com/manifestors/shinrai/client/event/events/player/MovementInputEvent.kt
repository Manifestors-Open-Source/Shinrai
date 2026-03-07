package com.manifestors.shinrai.client.event.events.player

import com.manifestors.shinrai.client.event.ConstantEvent
import net.minecraft.util.math.Vec2f

class MovementInputEvent(
    var forward: Float,
    var strafe: Float
) : ConstantEvent() {

    fun toVec(): Vec2f = Vec2f(strafe, forward)

}