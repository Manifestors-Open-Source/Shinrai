package com.manifestors.shinrai.client.module.modules.player;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

@ModuleData(
        name = "AutoTotem",
        description = "Will get Totem when your health is lesser than 2.5 Hearths",
        category = ModuleCategory.PLAYER
)
public class AutoTotem extends Module {

    private static final int OFFHAND_HANDLER_INDEX = 45;
    private static int cdTicks = 0;

    private int lastTotemSlot = -1;
    private ItemStack lastItemStack = ItemStack.EMPTY;

    @ListenEvent
    public void onTick(TickMovementEvent e) {
        if (!this.isEnabled()) return;

        if (cdTicks > 0) cdTicks--;

        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity p = mc.player;
        if (p == null || mc.interactionManager == null) return;


        if (mc.player.getHealth() > 5f) {
            if (lastTotemSlot != -1 && !lastItemStack.isEmpty()) {
                moveSlotToSlot(mc, OFFHAND_HANDLER_INDEX, lastTotemSlot);
                lastTotemSlot = -1;
                lastItemStack = ItemStack.EMPTY;
            }
            return;
        }

        if (!shouldEquipTotem(p)) return;

        int from = findTotemSlotIndexInHandler(mc);
        if (from == -1) return;

        if (cdTicks == 0) {
            ScreenHandler h = getCurrentHandler(mc);
            lastItemStack = h.getSlot(from).getStack().copy();
            lastTotemSlot = from;

            moveSlotToOffhand(mc, from);
            cdTicks = 6;
        }
    }

    private boolean shouldEquipTotem(ClientPlayerEntity p) {
        float eHP = p.getHealth() + p.getAbsorptionAmount();
        boolean lowHP = eHP <= 5.0f;
        boolean riskyDim = p.getWorld().getRegistryKey().getValue().getPath().contains("the_nether")
                || p.getWorld().getRegistryKey().getValue().getPath().contains("the_end");
        boolean falling = p.fallDistance > 12.0f;
        boolean witherPoison = p.hasStatusEffect(StatusEffects.WITHER)
                || p.hasStatusEffect(StatusEffects.POISON);
        return lowHP || riskyDim || falling || witherPoison;
    }

    private int findTotemSlotIndexInHandler(MinecraftClient client) {
        ScreenHandler h = getCurrentHandler(client);
        if (h == null) return -1;

        int size = h.slots.size();
        for (int i = 0; i < size; i++) {
            if (i == OFFHAND_HANDLER_INDEX) continue;
            ItemStack st = h.getSlot(i).getStack();
            if (!st.isEmpty() && st.getItem() == Items.TOTEM_OF_UNDYING) {
                return i;
            }
        }
        return -1;
    }

    private void moveSlotToOffhand(MinecraftClient client, int fromIndex) {
        ClientPlayerEntity p = client.player;
        if (p == null || client.interactionManager == null) return;
        ScreenHandler h = getCurrentHandler(client);
        if (h == null) return;

        int syncId = h.syncId;

        client.interactionManager.clickSlot(syncId, fromIndex, 0, SlotActionType.PICKUP, p);
        client.interactionManager.clickSlot(syncId, OFFHAND_HANDLER_INDEX, 0, SlotActionType.PICKUP, p);

        if (!p.currentScreenHandler.getCursorStack().isEmpty()) {
            client.interactionManager.clickSlot(syncId, fromIndex, 0, SlotActionType.PICKUP, p);
        }
    }

    private void moveSlotToSlot(MinecraftClient client, int fromIndex, int toIndex) {
        ClientPlayerEntity p = client.player;
        if (p == null || client.interactionManager == null) return;
        ScreenHandler h = getCurrentHandler(client);
        if (h == null) return;

        int syncId = h.syncId;

        client.interactionManager.clickSlot(syncId, fromIndex, 0, SlotActionType.PICKUP, p);
        client.interactionManager.clickSlot(syncId, toIndex, 0, SlotActionType.PICKUP, p);

        if (!p.currentScreenHandler.getCursorStack().isEmpty()) {
            client.interactionManager.clickSlot(syncId, fromIndex, 0, SlotActionType.PICKUP, p);
        }
    }

    private ScreenHandler getCurrentHandler(MinecraftClient client) {
        if (client.player == null) return null;
        return client.player.currentScreenHandler != null
                ? client.player.currentScreenHandler
                : client.player.playerScreenHandler;
    }
}
