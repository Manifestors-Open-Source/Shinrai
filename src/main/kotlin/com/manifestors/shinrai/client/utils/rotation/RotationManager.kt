/*
 *
 *  * Copyright © 2026 Manifestors Open Source
 *  * License: GPL-3.0
 *  *
 *  * All code in this project is the property of the Manifestors Open Source team
 *  * and its contributors. If you use this code in any project, please provide proper attribution
 *  * and release your project under the GPL-3.0 license as well.
 *  *
 *  * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.utils.rotation

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.rotation.Rotation.Companion.emptyRotation
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * RotationManager handles rotation calculations, smoothing, and GCD (Greatest Common Divisor) application
 * for yaw and pitch in Minecraft.
 */
object RotationManager : MinecraftInstance {

    /** The current rotation of the player, updated each tick. Nullable if not initialized yet. */
    var currentRotation: Rotation? = null

    /**
     * Calculates the basic yaw and pitch rotation required to look at a given position in the world.
     *
     * @param otherPosition The target position to look at (Vec3d).
     * @return Rotation containing yaw and pitch to face the target.
     */
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

    /**
     * Applies smoothing between the current rotation and the target rotation.
     * Helps create natural, gradual camera movement instead of instant snapping.
     *
     * @param current The current rotation of the player.
     * @param target The target rotation we want to approach.
     * @param yawSpeed How fast to interpolate yaw (horizontal rotation).
     * @param pitchSpeed How fast to interpolate pitch (vertical rotation).
     * @return A new Rotation object representing the smoothed rotation.
     */
    fun applySmoothing(current: Rotation, target: Rotation, yawSpeed: Double, pitchSpeed: Double): Rotation {
        val deltaYaw = MathHelper.wrapDegrees(target.yaw - current.yaw)
        val deltaPitch = MathHelper.wrapDegrees(target.pitch - current.pitch)

        val newYaw = current.yaw + deltaYaw * yawSpeed
        val newPitch = current.pitch + deltaPitch * pitchSpeed

        return Rotation(newYaw.toFloat(), newPitch.toFloat())
    }

    /**
     * Applies GCD (Greatest Common Divisor) adjustment to a rotation.
     * Ensures rotation values align with the discrete increments used by Minecraft's mouse movement,
     * which improves compatibility with anticheats.
     *
     * @param rotation The rotation to apply GCD on.
     * @return A new Rotation object with yaw and pitch snapped to the closest GCD increment.
     */
    fun applyGCD(rotation: Rotation): Rotation {
        val gcd = (mc.options.mouseSensitivity.value + 0.6 + 0.2) * 3

        val gcdYaw = rotation.yaw - (rotation.yaw % gcd)
        val gcdPitch = rotation.pitch - (rotation.pitch % gcd)

        return Rotation(gcdYaw.toFloat(), gcdPitch.toFloat())
    }

}