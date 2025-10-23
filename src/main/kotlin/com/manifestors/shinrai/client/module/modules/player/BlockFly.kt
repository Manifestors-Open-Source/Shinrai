package com.manifestors.shinrai.client.module.modules.player

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.item.BlockItem
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.EmptyBlockView

class BlockFly : Module(
    name = "BlockFly",
    category = ModuleCategory.PLAYER,
    alternativeNames = arrayOf("Scaffold")
) {
    private var previousSlot = -1

    override fun onEnable() {
        previousSlot = mc.player?.inventory?.selectedSlot ?: -1
    }

    @ListenEvent
    fun onTickMovement(event: TickMovementEvent) {
        val player = mc.player ?: return

        val bestSlot = findBestSlot() ?: return
        player.inventory.selectedSlot = bestSlot

        placeBlock(player.blockPos.down())
    }

    override fun onDisable() {
        mc.player?.let { player ->
            if (previousSlot != -1) player.inventory.selectedSlot = previousSlot
        }
        previousSlot = -1
    }

    private fun placeBlock(pos: BlockPos) {
        val world = mc.world ?: return
        val player = mc.player ?: return

        if (!world.getBlockState(pos).isReplaceable) return

        for (direction in Direction.entries) {
            val neighborPos = pos.offset(direction)
            if (!world.getBlockState(neighborPos).isAir) {
                val result = BlockHitResult(
                    Vec3d.ofCenter(neighborPos),
                    direction.opposite,
                    neighborPos,
                    false
                )
                if (mc.interactionManager?.interactBlock(player, Hand.MAIN_HAND, result)?.isAccepted!!) {
                    player.swingHand(Hand.MAIN_HAND)
                }
                break
            }
        }
    }

    private fun findBestSlot(): Int? {
        val player = mc.player ?: return null

        val currentSlot = player.inventory.selectedSlot
        val currentStack = player.inventory.getStack(currentSlot)

        if (currentStack.item is BlockItem) {
            val state = (currentStack.item as BlockItem).block.defaultState
            if (state.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN)) return currentSlot
        }

        val blockSlots = (0..8)
            .map { player.inventory.getStack(it) }
            .filter { it.item is BlockItem && (it.item as BlockItem).block.defaultState.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN) }

        if (blockSlots.isEmpty()) return null

        val biggestStack = blockSlots.maxByOrNull { it.count } ?: return null
        return player.inventory.getSlotWithStack(biggestStack)
    }
}
