package com.manifestors.shinrai.client.event.events.player

import com.manifestors.shinrai.client.event.ConstantEvent

class MovementPacketsEvent(
    val state: EventState,
    var x: Double,
    var y: Double,
    var z: Double,
    var onGround: Boolean
) : ConstantEvent()

enum class EventState {
    PRE,
    POST
}