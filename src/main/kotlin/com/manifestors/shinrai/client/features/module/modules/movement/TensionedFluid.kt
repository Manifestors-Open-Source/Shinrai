package com.manifestors.shinrai.client.features.module.modules.movement

import com.manifestors.shinrai.client.event.annotations.InvokeEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory
import net.minecraft.block.Blocks

class TensionedFluid : Module(
    name = "TensionedFluid",
    description = "Block submerge in Water",
    category = ModuleCategory.MOVEMENT
) {

    @InvokeEvent
    fun onTickMovement(event: TickMovementEvent) {
        val blockBelow= mc.player!!.blockPos.down()
        val block = mc.world!!.getBlockState(blockBelow).block
        if (block === Blocks.WATER && !mc.player!!.isJumping) {
            mc.player!!.setVelocity(mc.player!!.velocity.x, 0.00001, mc.player!!.velocity.z)
            mc.player!!.isOnGround = true
        } else if (mc.player!!.isTouchingWater || mc.player!!.isSubmergedInWater()) {
            mc.player!!.setVelocity(mc.player!!.velocity.x, 0.1, mc.player!!.velocity.z)
        }
    }

}
