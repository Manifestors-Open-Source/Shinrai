package com.manifestors.shinrai.client.module.modules.combat

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import com.manifestors.shinrai.client.utils.math.TimingUtil
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand

class KillAura : Module(
    name = "KillAura",
    description = "Automatically attacks entities around you.",
    category = ModuleCategory.COMBAT
) {
    private val timer = TimingUtil()

    @ListenEvent
    fun onTickMovement(event: TickMovementEvent) {
        val world = mc.world ?: return
        val player = mc.player ?: return

        world.entities
            .filterIsInstance<LivingEntity>()
            .filter { isAttackable(it) && player.distanceTo(it) <= 3.5f }
            .forEach { target ->
                if (timer.hasElapsed(625L)) {
                    val hand = if (player.activeHand == Hand.MAIN_HAND) Hand.MAIN_HAND else Hand.OFF_HAND
                    player.swingHand(hand)
                    mc.interactionManager?.attackEntity(player, target)
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
