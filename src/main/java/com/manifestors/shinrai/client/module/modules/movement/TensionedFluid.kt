package com.manifestors.shinrai.client.module.modules.movement;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

@ModuleData(
        name = "TensionedFluid",
        description = "Block submerge in Water",
        category = ModuleCategory.MOVEMENT
)
public class TensionedFluid extends Module {
    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        BlockPos blockBelow = mc.player.getBlockPos().down();
        Block block = mc.world.getBlockState(blockBelow).getBlock();
        if (block == Blocks.WATER && !mc.player.isJumping()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.00001, mc.player.getVelocity().z);
            mc.player.setOnGround(true);
        } else if (mc.player.isTouchingWater() || mc.player.isSubmergedInWater()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.1, mc.player.getVelocity().z );
        }
       /*
       gonna add tomorrow

       if (block == Blocks.LAVA && !mc.player.isJumping()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.008, mc.player.getVelocity().z);
            mc.player.setOnGround(true);
            mc.player.setHealth(10);
            mc.player.setInvisible(true);

        }

        */
    }

}
