package com.manifestors.shinrai.client.module.modules.player;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.Slot;
@ModuleData(
        name = "ChestStealer",
        description = "Block submerge in Water",
        category = ModuleCategory.PLAYER
)
public class ChestStealer extends Module {

    @ListenEvent
    public void tick(TickMovementEvent event) {
        if (!(mc.currentScreen instanceof GenericContainerScreen)) {
            return;
        }

        GenericContainerScreen chestScreen = (GenericContainerScreen) mc.currentScreen;

        for (Slot slot : chestScreen.getScreenHandler().slots) {
            if (isChestSlot(slot) && slot.hasStack()) {
                mc.interactionManager.clickSlot(
                        chestScreen.getScreenHandler().syncId,
                        slot.id,
                        0,
                        net.minecraft.screen.slot.SlotActionType.QUICK_MOVE,
                        mc.player
                );
            }
        }
    }


    private boolean isChestSlot(Slot slot) {

        return slot.id < 54;
    }
}
