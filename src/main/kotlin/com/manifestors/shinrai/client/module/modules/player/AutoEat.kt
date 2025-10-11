package com.manifestors.shinrai.client.module.modules.player

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.screen.slot.SlotActionType

class AutoEat : Module(
    name = "AutoEat",
    category = ModuleCategory.PLAYER
) {
    private var isEating = false
    private var foodSlot = -1
    private var previousSlot = -1

    @ListenEvent
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

    private fun findBestFoodSlot(player: net.minecraft.client.network.ClientPlayerEntity): Int? {
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

    private fun startEating(player: net.minecraft.client.network.ClientPlayerEntity, slot: Int) {
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

    private fun stopEating(player: net.minecraft.client.network.ClientPlayerEntity) {
        mc.options.useKey.isPressed = false
        isEating = false
        foodSlot = -1
        player.inventory.selectedSlot = previousSlot
        previousSlot = -1
    }
}
