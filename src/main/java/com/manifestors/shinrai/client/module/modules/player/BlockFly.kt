package com.manifestors.shinrai.client.module.modules.player;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.EmptyBlockView;

import java.util.ArrayList;
import java.util.Comparator;

@ModuleData(
        name = "BlockFly",
        category = ModuleCategory.PLAYER,
        alternatives = "Scaffold"
)
public class BlockFly extends Module {

    private int previousSlot = -1;

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        previousSlot = mc.player.getInventory().getSelectedSlot();
    }

    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        if (mc.world == null || mc.player == null) return;

        var newSlot = findBestSlot();
        if (newSlot == -1) return;

        mc.player.getInventory().setSelectedSlot(newSlot);

        placeBlock(mc.player.getBlockPos().down());
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;

        if (previousSlot != -1)
            mc.player.getInventory().setSelectedSlot(previousSlot);
        previousSlot = -1;
    }

    private void placeBlock(BlockPos pos) {
        if (!mc.world.getBlockState(pos).isReplaceable()) return;

        for (var direction : Direction.values()) {
            var neighborPos = pos.offset(direction);

            if (!mc.world.getBlockState(neighborPos).isAir()) {
                var result = new BlockHitResult(
                        Vec3d.ofCenter(neighborPos),
                        direction.getOpposite(),
                        neighborPos,
                        false
                );

                if (mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, result).isAccepted())
                    mc.player.swingHand(Hand.MAIN_HAND);
                break;
            }
        }
    }

    private int findBestSlot() {
        var slots = new ArrayList<ItemStack>();
        var currentSlot = mc.player.getInventory().getSelectedSlot();
        var currentStack = mc.player.getInventory().getStack(currentSlot);

        if (!currentStack.isEmpty() && currentStack.getItem() instanceof BlockItem item &&
                item.getBlock().getDefaultState().isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN))
            return currentSlot;

        for (int i = 0; i < 9; i++) {
            var stack = mc.player.getInventory().getStack(i);

            if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem))
                continue;

            var state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            if (!state.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN))
                continue;

            slots.add(stack);
        }

        if (slots.isEmpty())
            return -1;

        var biggest = slots.stream()
                .max(Comparator.comparingInt(ItemStack::getCount))
                .orElse(null);

        return mc.player.getInventory().getSlotWithStack(biggest);
    }

}
