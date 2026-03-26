/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.features.module.modules.player

import com.manifestors.shinrai.client.event.annotations.InvokeEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.component.DataComponentTypes
import net.minecraft.util.Hand
import net.minecraft.screen.slot.SlotActionType

class AutoEat : Module(
    name = "AutoEat",
    category = ModuleCategory.PLAYER
) {
    private var isEating = false
    private var foodSlot = -1
    private var previousSlot = -1

    @InvokeEvent
    fun onTick(event: TickMovementEvent) {
        val player = mc.player ?: return
        val im = mc.interactionManager ?: return

        if (isEating) {
            if (!player.isUsingItem || player.hungerManager.foodLevel >= 20) {
                stopEating(player)
            }
            return
        }

        if (player.hungerManager.foodLevel < 18) {
            findBestFoodSlot(player)?.let { startEating(player, it) }
        }
    }

    private fun findBestFoodSlot(player: ClientPlayerEntity): Int? {
        val inv = player.inventory
        var bestSlot: Int? = null
        var maxSaturation = -1.0

        for (i in 0 until inv.size()) {
            val stack = inv.getStack(i)
            val foodComponent = stack.get(DataComponentTypes.FOOD) ?: continue
            val saturationValue = 2.0 * foodComponent.nutrition() * foodComponent.saturation()
            if (saturationValue > maxSaturation) {
                maxSaturation = saturationValue
                bestSlot = i
            }
        }
        return bestSlot
    }

    private fun startEating(player: ClientPlayerEntity, slot: Int) {
        val im = mc.interactionManager ?: return

        previousSlot = player.inventory.selectedSlot
        foodSlot = if (slot >= 9) {
            im.clickSlot(
                player.playerScreenHandler.syncId,
                slot,
                player.inventory.selectedSlot,
                SlotActionType.SWAP,
                player
            )
            player.inventory.selectedSlot
        } else {
            player.inventory.selectedSlot = slot
            slot
        }

        isEating = true
        im.interactItem(player, Hand.MAIN_HAND)
        mc.options.useKey.isPressed = true
    }

    private fun stopEating(player: ClientPlayerEntity) {
        mc.options.useKey.isPressed = false
        isEating = false
        foodSlot = -1
        player.inventory.selectedSlot = previousSlot
        previousSlot = -1
    }
}
