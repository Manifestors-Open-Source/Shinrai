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
