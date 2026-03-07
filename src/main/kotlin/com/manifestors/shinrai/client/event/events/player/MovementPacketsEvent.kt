package com.manifestors.shinrai.client.event.events.player

import com.manifestors.shinrai.client.event.ConstantEvent
import net.minecraft.util.math.Vec3d

class MovementPacketsEvent(
    val position: Vec3d,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean,
    val state: EventState
) : ConstantEvent()

enum class EventState {
    PRE,
    POST
}