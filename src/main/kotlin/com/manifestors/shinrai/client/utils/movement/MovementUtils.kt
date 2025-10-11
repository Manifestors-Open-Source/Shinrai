package com.manifestors.shinrai.client.utils.movement

import com.manifestors.shinrai.client.utils.MinecraftInstance
import net.minecraft.util.math.MathHelper
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

object MovementUtils : MinecraftInstance {
    val isMoving: Boolean
        get() {
            if (mc.player == null) return false
            return mc.player!!.input.movementInput.x != 0f || mc.player!!.input.movementInput.y != 0f
        }

    val speed: Float
        get() {
            if (mc.player == null) return 0.0f

            val x: Double =
                mc.player!!.velocity.x
            val z: Double =
                mc.player!!.velocity.z

            return sqrt((x * x + z * z)).toFloat()
        }

    @JvmOverloads
    fun applyStrafe(speed: Float = MovementUtils.speed) {
        if (mc.player != null) {
            var forward = mc.player!!.input.movementInput.y
            var strafe = mc.player!!.input.movementInput.x

            if (forward.toDouble() == 0.0 && strafe.toDouble() == 0.0) {
                mc.player!!.setVelocity(0.0, mc.player!!.velocity.y, 0.0)
                return
            }

            // Normalize input
            if (forward.toDouble() != 0.0) forward = if (forward > 0.0) 1.0f else -1.0f
            if (strafe.toDouble() != 0.0) strafe = if (strafe > 0.0) 1.0f else -1.0f

            // Diagonal movement normalization
            if (forward.toDouble() != 0.0 && strafe.toDouble() != 0.0) {
                forward *= sin(45.0).toFloat()
                strafe *= sin(45.0).toFloat()
            }

            val direction = Math.toRadians(mc.player!!.getBodyYaw().toDouble())

            val x = (forward * -sin(direction) + strafe * cos(direction)) * speed
            val z = (forward * cos(direction) + strafe * sin(direction)) * speed

            mc.player!!.setVelocity(x, mc.player!!.velocity.y, z)
        }
    }

    val isLookingDiagonally: Boolean
        get() {
            val direction: Float =
                mc.player!!.bodyYaw

            val yaw: Float =
                (abs(MathHelper.wrapDegrees(direction)) / 45f).roundToInt() * 45f

            val isYawDiagonal = yaw % 90f != 0f
            val isMovingDiagonal = mc.player!!.movement.y != 0.0 && mc.player!!.movement.x == 0.0

            return isYawDiagonal || isMovingDiagonal
        }
}
