package com.manifestors.shinrai.client.module.modules.player

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.screen.slot.Slot
import net.minecraft.screen.slot.SlotActionType

class ChestStealer : Module(
    name = "ChestStealer",
    description = "Automatically steals items from chests.",
    category = ModuleCategory.PLAYER
) {

    @ListenEvent
    fun tick(event: TickMovementEvent?) {
        val screen = mc.currentScreen as? GenericContainerScreen ?: return

        for (slot in screen.screenHandler.slots) {
            if (isChestSlot(slot) && slot.hasStack()) {
                mc.interactionManager?.clickSlot(
                    screen.screenHandler.syncId,
                    slot.id,
                    0,
                    SlotActionType.QUICK_MOVE,
                    mc.player
                )
            }
        }
    }

    private fun isChestSlot(slot: Slot) = slot.id < 54
}
