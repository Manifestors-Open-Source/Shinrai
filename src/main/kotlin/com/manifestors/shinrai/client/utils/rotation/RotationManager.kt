package com.manifestors.shinrai.client.utils.rotation

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.rotation.Rotation.Companion.emptyRotation
import net.minecraft.util.math.Vec3d
import kotlin.math.atan2
import kotlin.math.sqrt

object RotationManager : MinecraftInstance {

    var currentRotation: Rotation? = null

    fun calculateBasicRotation(otherPosition: Vec3d): Rotation {
        val player = mc.player ?: return emptyRotation()
        val playerPosition = player.pos

        val xDifference = otherPosition.x - playerPosition.x
        val yDifference = otherPosition.y - playerPosition.y
        val zDifference = otherPosition.z - playerPosition.z

        val distance = sqrt(xDifference * xDifference + zDifference * zDifference)

        val yaw = Math.toDegrees(atan2(zDifference, xDifference)) - 90f
        val pitch = Math.toDegrees(-atan2(yDifference, distance))

        return Rotation(yaw.toFloat(), Math.clamp(pitch.toFloat(), -90f, 90f))
    }

}