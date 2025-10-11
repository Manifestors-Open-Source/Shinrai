package com.manifestors.shinrai.client.module.modules.player;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@ModuleData(
        name = "AutoEat",
        category = ModuleCategory.PLAYER
)
public class AutoEat extends Module {

    private boolean isEating = false;
    private int foodSlot = -1;
    private int previousSlot = -1;
    @ListenEvent
    public void onTick(TickMovementEvent event) {
        if (mc.player == null || mc.interactionManager == null) return;
        if (isEating) {
            if (!mc.player.isUsingItem() || mc.player.getHungerManager().getFoodLevel() >= 20) {
                stopEating();
            }
            return;
        }
        if (mc.player.getHungerManager().getFoodLevel() < 18) {
            int bestSlot = findBestFoodSlot();
            if (bestSlot != -1) {
                startEating(bestSlot);
            }
        }
    }

    private int findBestFoodSlot() {
        PlayerInventory inventory = mc.player.getInventory();
        double maxSaturation = -1.0;
        int bestSlot = -1;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            FoodComponent foodComponent = stack.get(DataComponentTypes.FOOD);

            if (foodComponent != null) {
                double saturationValue = 2.0 * foodComponent.nutrition() * foodComponent.saturation();
                if (saturationValue > maxSaturation) {
                    maxSaturation = saturationValue;
                    bestSlot = i;
                }
            }
        }
        return bestSlot;
    }
    private void startEating(int slot) {
        this.previousSlot = mc.player.getInventory().getSelectedSlot();
        if (slot >= 9) {
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, slot, mc.player.getInventory().getSelectedSlot(), net.minecraft.screen.slot.SlotActionType.SWAP, mc.player);
            this.foodSlot = mc.player.getInventory().getSelectedSlot();
        } else {
            this.foodSlot = slot;
            mc.player.getInventory().setSelectedSlot(this.foodSlot);
        }
        isEating = true;
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        mc.options.useKey.setPressed(true);
    }
    private void stopEating() {
        mc.options.useKey.setPressed(false);
        isEating = false;
        foodSlot = -1;
        mc.player.getInventory().setSelectedSlot(previousSlot);
        previousSlot = -1;
    }
}