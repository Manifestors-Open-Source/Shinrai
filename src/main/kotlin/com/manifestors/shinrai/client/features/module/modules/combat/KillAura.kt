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

package com.manifestors.shinrai.client.features.module.modules.combat

import com.manifestors.shinrai.client.event.annotations.InvokeEvent
import com.manifestors.shinrai.client.event.events.player.EventState
import com.manifestors.shinrai.client.event.events.player.JumpFixEvent
import com.manifestors.shinrai.client.event.events.player.MovementFixEvent
import com.manifestors.shinrai.client.event.events.player.MovementInputEvent
import com.manifestors.shinrai.client.event.events.player.MovementPacketsEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory
import com.manifestors.shinrai.client.setting.settings.ChoiceSetting
import com.manifestors.shinrai.client.setting.settings.DoubleSetting
import com.manifestors.shinrai.client.utils.math.TimingUtil
import com.manifestors.shinrai.client.utils.movement.MovementUtils
import com.manifestors.shinrai.client.utils.rotation.RotationManager
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.math.Vec3d

class KillAura : Module(
    name = "KillAura",
    description = "Automatically attacks entities around you.",
    category = ModuleCategory.COMBAT
) {
    private val timer = TimingUtil()
    var currentTarget: LivingEntity? = null

    private val attackRange = DoubleSetting("Attack Range", 3.0, 3.0, 6.0, 0.01)
    private val rotationRange = DoubleSetting("Rotation Range", 5.0, 1.0, 8.0, 0.01)
    private val maxYawSpeed = DoubleSetting("Max Yaw Speed", 180.0, 0.0, 180.0, 0.01)
    private val minYawSpeed = DoubleSetting("Max Yaw Speed", 180.0, 0.0, 180.0, 0.01)
    private val maxPitchSpeed = DoubleSetting("Max Yaw Speed", 180.0, 0.0, 180.0, 0.01)
    private val minPitchSpeed = DoubleSetting("Max Yaw Speed", 180.0, 0.0, 180.0, 0.01)
    private val moveFix = ChoiceSetting("Movement Fix", "Silent", "Off", "Strict", "Use Camera")

    @InvokeEvent
    fun onTickMovement(event: TickMovementEvent) {

    }

    @InvokeEvent
    fun onMovePackets(event: MovementPacketsEvent) {
        if (event.state == EventState.PRE) {
            val player = mc.player ?: return
            val world = mc.world ?: return

            val target = world.entities
                .filterIsInstance<LivingEntity>()
                .filter { it != player && isAttackable(it) && player.distanceTo(it) <= 3.0f }
                .minByOrNull { player.distanceTo(it) }

            if (target != null) {
                val targetRotation = RotationManager.calculateBasicRotation(target.pos)

                val current = RotationManager.currentRotation ?: targetRotation

                val smoothed = RotationManager.applySmoothing(current, targetRotation, 0.3, 0.15)
                val finalRotation = RotationManager.applyGCD(smoothed)

                if (moveFix.currentChoice == "Use Camera") {
                    val player = mc.player ?: return
                    player.yaw = finalRotation.yaw
                    player.pitch = finalRotation.pitch
                } else {
                    event.yaw = finalRotation.yaw
                    event.pitch = finalRotation.pitch
                }

                RotationManager.currentRotation = finalRotation

                attack(target)
                currentTarget = target
            } else {
                currentTarget = null
            }
        }
    }

    @InvokeEvent
    fun onMoveFix(event: MovementFixEvent) {
        if (currentTarget != null && RotationManager.currentRotation != null && moveFix.currentChoice != "Off") {
            val current = RotationManager.currentRotation!!
            event.yaw = current.yaw
        }
    }

    @InvokeEvent
    fun onJumpFix(event: JumpFixEvent) {
        if (currentTarget != null && RotationManager.currentRotation!= null && moveFix.currentChoice != "Off") {
            val current = RotationManager.currentRotation!!
            event.yaw = current.yaw
        }
    }

    @InvokeEvent
    fun onMoveInput(event: MovementInputEvent) {
        if (currentTarget != null && RotationManager.currentRotation != null && moveFix.currentChoice == "Silent") {
            val current = RotationManager.currentRotation ?: return

            val fixedMovement = MovementUtils.applyMovementFix(event.toVec(), current.yaw)

            event.forward = fixedMovement.y
            event.strafe = fixedMovement.x
        }
    }

    override fun onDisable() {
        RotationManager.currentRotation = null
        currentTarget = null
    }

    private fun attack(target: LivingEntity) {
        val player = mc.player ?: return

        val origin = player.eyePos
        val current = RotationManager.currentRotation ?: return
        val rotationVector = Vec3d.fromPolar(current.pitch, current.yaw)
        val end = origin.add(rotationVector.multiply(3.0))

        val rayCast = target.boundingBox.raycast(origin, end)

        if (rayCast.isPresent) {
            if (timer.hasElapsed(15L)) {
                val hand = Hand.MAIN_HAND
                mc.interactionManager?.attackEntity(player, target)
                player.swingHand(hand)
                timer.reset()
            }
        }
    }

    private fun isAttackable(entity: LivingEntity): Boolean {
        return when (entity) {
            is ClientPlayerEntity -> false
            is PlayerEntity, is AnimalEntity, is MobEntity -> true
            else -> entity.isAlive && entity.isAttackable
        }
    }

}
