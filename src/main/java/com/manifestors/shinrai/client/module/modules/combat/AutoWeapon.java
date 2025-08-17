package com.manifestors.shinrai.client.module.modules.combat;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStackSet;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

@ModuleData(
        name = "AutoWeapon",
        category = ModuleCategory.COMBAT
)
public class AutoWeapon extends Module {


    @ListenEvent
    public void onTick(TickMovementEvent event) {
        if (mc.player == null || mc.world == null) return;
        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof EntityHitResult entityHit)) return;
        if (!(entityHit.getEntity() instanceof LivingEntity)) return;
        if (!mc.options.attackKey.isPressed()) return;
        int bestSlot = -1;
        float highestDamage = 0f;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;
            float damage = 0f;
            if (stack.isIn(ItemTags.SWORDS)) {
                damage = stack.getMaxDamage();
            } else if (stack.isIn(ItemTags.AXES)) {
                damage = stack.getMaxDamage() * 0.9f;
            }
            if (damage > highestDamage) {
                highestDamage = damage;
                bestSlot = i;
            }
        }
        if (bestSlot != -1 && mc.player.getInventory().getSelectedSlot() != bestSlot) {
            mc.player.getInventory().setSelectedSlot(bestSlot);
        }
    }
}

