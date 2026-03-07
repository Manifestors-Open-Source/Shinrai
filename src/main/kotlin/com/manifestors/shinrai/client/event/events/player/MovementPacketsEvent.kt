package com.manifestors.shinrai.client.event.events.player

import com.manifestors.shinrai.client.event.ConstantEvent
import net.minecraft.util.math.Vec3d

class MovementPacketsEvent(
    val position: Vec3d,
    val yaw: Float,
    val pitch: Float,
    var onGround: Boolean
) : ConstantEvent()

enum class EventState {
    PRE,
    POST
}