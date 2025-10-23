package com.manifestors.shinrai.client.module.modules.player

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import com.manifestors.shinrai.client.setting.settings.BooleanSetting
import com.manifestors.shinrai.client.setting.settings.DoubleSetting
import com.manifestors.shinrai.client.utils.math.TimingUtil
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.screen.slot.SlotActionType

class ChestStealer : Module(
    name = "ChestStealer",
    description = "Automatically steals items from chests.",
    category = ModuleCategory.PLAYER
) {

    private val delay = DoubleSetting("Delay", 0.5, 0.0, 5.0, 0.001)
    private val autoClose = BooleanSetting("Auto Close", true)
    private val autoCloseDelay = DoubleSetting("Auto Close Delay", 0.15, 0.0, 5.0, 0.001)
    private val timer = TimingUtil()

    @ListenEvent
    fun onTick(event: TickMovementEvent) {
        val screen = mc.currentScreen as? GenericContainerScreen ?: return
        val player = mc.player ?: return

        for (slot in screen.screenHandler.slots) {
            if (isChestSlot(slot) && slot.hasStack()) {
                if (timer.hasElapsed((delay.current * 1000).toLong())) {
                    mc.interactionManager?.clickSlot(
                        screen.screenHandler.syncId,
                        slot.id,
                        0,
                        SlotActionType.QUICK_MOVE,
                        player
                    )
                    timer.reset()
                }
            }
        }

        if (isChestEmpty(screen.screenHandler) && autoClose.current && timer.hasElapsed((autoCloseDelay.current * 1000).toLong())) {
            screen.close()
            timer.reset()
        }

    }

    private fun isChestEmpty(screenHandler: GenericContainerScreenHandler): Boolean {
        val inventory = screenHandler.slots
            .filter { it.inventory !is PlayerInventory }
            .map { it.inventory }
            .firstOrNull()

        inventory?.let {
            val hasItems = (0..it.size()).any { i -> !it.getStack(i).isEmpty }
            return !hasItems
        }

        return false
    }

    private fun isChestSlot(slot: Slot) = slot.id < 54
}
