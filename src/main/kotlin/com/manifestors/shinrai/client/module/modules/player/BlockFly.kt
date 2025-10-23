package com.manifestors.shinrai.client.module.modules.player

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import com.manifestors.shinrai.client.setting.settings.BooleanSetting
import com.manifestors.shinrai.client.setting.settings.ChoiceSetting
import net.minecraft.item.BlockItem
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
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

    private val sprint = BooleanSetting("Sprinting", false)
    private val swingMode = ChoiceSetting("Swing", "No Hide","Hide for Client", "Hide for Server", "Hide for Both")

    private var previousSlot = -1

    override fun onEnable() {
        previousSlot = mc.player?.inventory?.selectedSlot ?: -1
    }

    @ListenEvent
    fun onTickMovement(event: TickMovementEvent) {
        val player = mc.player ?: return
        val bestSlot = findBestSlot() ?: return

        player.inventory.selectedSlot = bestSlot

        player.isSprinting = sprint.current

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
                    swing()
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

    private fun swing() {
        when (swingMode.currentChoice) {
            "No Hide" -> mc.player?.swingHand(Hand.MAIN_HAND)
            "Hide for Client" -> mc.networkHandler?.sendPacket(HandSwingC2SPacket(Hand.MAIN_HAND))
            "Hide for Server" -> mc.player?.swingHand(Hand.MAIN_HAND, false)
        }
    }

}
