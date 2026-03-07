package com.manifestors.shinrai.client.utils.rotation

class Rotation(
    val yaw: Float,
    val pitch: Float
) {

    companion object {
        fun emptyRotation(): Rotation = Rotation(0f, 0f)
    }

}