package com.manifestors.shinrai.client.module.modules.combat

import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.entity.LivingEntity
import net.minecraft.registry.tag.ItemTags
import net.minecraft.util.hit.EntityHitResult

object AutoWeapon : Module(
    name = "AutoWeapon",
    description = "Automatically selects the best weapon when attacking an entity.",
    category = ModuleCategory.COMBAT
) {
    @ListenEvent
    fun onTick(event: TickMovementEvent) {
        val player = mc.player ?: return
        val hit = mc.crosshairTarget as? EntityHitResult ?: return
        val target = hit.entity as? LivingEntity ?: return

        if (!mc.options.attackKey.isPressed) return

        val inv = player.inventory
        var bestSlot = -1
        var highestDamage = 0f

        for (i in 0..8) {
            val stack = inv.getStack(i)
            if (stack.isEmpty) continue

            val damage = when {
                stack.isIn(ItemTags.SWORDS) -> stack.maxDamage.toFloat()
                stack.isIn(ItemTags.AXES) -> stack.maxDamage * 0.9f
                else -> 0f
            }

            if (damage > highestDamage) {
                highestDamage = damage
                bestSlot = i
            }
        }

        if (bestSlot != -1 && inv.selectedSlot != bestSlot) {
            inv.selectedSlot = bestSlot
        }
    }
}
