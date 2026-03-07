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
import com.manifestors.shinrai.client.utils.math.TimingUtil
import com.manifestors.shinrai.client.utils.rotation.Rotation
import com.manifestors.shinrai.client.utils.rotation.RotationManager
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import kotlin.math.cos
import kotlin.math.sin

class KillAura : Module(
    name = "KillAura",
    description = "Automatically attacks entities around you.",
    category = ModuleCategory.COMBAT
) {
    private val timer = TimingUtil()
    private var currentTarget: LivingEntity? = null

    private val moveFix = ChoiceSetting("Movement Fix", "Silent", "Strict", "Off")

    @InvokeEvent
    fun onTickMovement(event: TickMovementEvent) {
        val world = mc.world ?: return
        val player = mc.player ?: return

        val target = world.entities
            .filterIsInstance<LivingEntity>()
            .filter { it != player && isAttackable(it) && player.distanceTo(it) <= 3.0f }
            .minByOrNull { player.distanceTo(it) }

        if (target != null) {
            currentTarget = target
            if (timer.hasElapsed(600L)) {
                val hand = Hand.MAIN_HAND
                mc.interactionManager?.attackEntity(player, target)
                player.swingHand(hand)
                timer.reset()
            }
        } else {
            currentTarget = null
        }
    }

    @InvokeEvent
    fun onMovePackets(event: MovementPacketsEvent) {
        if (event.state == EventState.PRE && currentTarget != null) {
            val rotations = RotationManager.calculateBasicRotation(currentTarget!!.pos)

            event.yaw = rotations.yaw
            event.pitch = rotations.pitch
            val player = mc.player ?: return

            player.bodyYaw = event.yaw
            player.headYaw = event.yaw
            RotationManager.currentRotation = Rotation(event.yaw, event.pitch)
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
            val player = mc.player ?: return

            val angleDiff = Math.toRadians((player.yaw - current.yaw).toDouble())

            val cos = cos(angleDiff)
            val sin = sin(angleDiff)

            val newForward = (event.forward * cos) + (event.strafe * sin)
            val newStrafe = (event.forward * sin) - (event.strafe * cos)

            event.forward = newForward.toFloat()
            event.strafe = newStrafe.toFloat()
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
