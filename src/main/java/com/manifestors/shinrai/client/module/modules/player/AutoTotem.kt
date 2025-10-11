package com.manifestors.shinrai.client.module.modules.player

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.SlotActionType

class AutoTotem : Module(
    name = "AutoTotem",
    description = "Will equip Totem when health is lower than 2.5 hearts or in risky situations.",
    category = ModuleCategory.PLAYER
) {
    private var lastTotemSlot = -1
    private var lastItemStack: ItemStack = ItemStack.EMPTY
    private val offhandHandlerIndex = 45
    private var cdTicks = 0

    @ListenEvent
    fun onTick(event: TickMovementEvent) {
        val mc = MinecraftClient.getInstance()
        val player = mc.player ?: return
        val im = mc.interactionManager ?: return

        if (cdTicks > 0) {
            lastTotemSlot = -1
            lastItemStack = ItemStack.EMPTY
            cdTicks--
        }

        if (!shouldEquipTotem(player)) return

        val fromIndex = findTotemSlotIndex(player) ?: return

        if (cdTicks == 0) {
            lastItemStack = getCurrentHandler(mc)?.getSlot(fromIndex)?.stack?.copy() ?: ItemStack.EMPTY
            lastTotemSlot = fromIndex

            moveSlotToOffhand(player, fromIndex)
            cdTicks = 6
        }
    }

    private fun shouldEquipTotem(player: ClientPlayerEntity): Boolean {
        val lowHP = player.health <= 3.0f
        val riskyDim = player.world.registryKey.value.path.contains("the_nether") ||
                player.world.registryKey.value.path.contains("the_end")
        val falling = player.fallDistance >= 5.0f
        val badEffects = player.hasStatusEffect(StatusEffects.WITHER) || player.hasStatusEffect(StatusEffects.POISON)

        return lowHP || riskyDim || falling || badEffects
    }

    private fun findTotemSlotIndex(player: ClientPlayerEntity): Int? {
        val handler = getCurrentHandler(MinecraftClient.getInstance()) ?: return null

        for (i in handler.slots.indices) {
            if (i == offhandHandlerIndex) continue
            val stack = handler.getSlot(i).stack
            if (stack.item == Items.TOTEM_OF_UNDYING) return i
        }
        return null
    }

    private fun moveSlotToOffhand(player: ClientPlayerEntity, fromIndex: Int) {
        val mc = MinecraftClient.getInstance()
        val im = mc.interactionManager ?: return
        val handler = getCurrentHandler(mc) ?: return
        val syncId = handler.syncId

        im.clickSlot(syncId, fromIndex, 0, SlotActionType.PICKUP, player)
        im.clickSlot(syncId, offhandHandlerIndex, 0, SlotActionType.PICKUP, player)

        if (!player.currentScreenHandler.cursorStack.isEmpty) {
            im.clickSlot(syncId, fromIndex, 0, SlotActionType.PICKUP, player)
        }
    }

    private fun getCurrentHandler(mc: MinecraftClient): ScreenHandler? {
        val player = mc.player ?: return null
        return player.currentScreenHandler ?: player.playerScreenHandler
    }
}
