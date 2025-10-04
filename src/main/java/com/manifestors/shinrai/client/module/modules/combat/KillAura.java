package com.manifestors.shinrai.client.module.modules.combat;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import com.manifestors.shinrai.client.utils.math.TimingUtil;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Hand;

@ModuleData(
        name = "KillAura",
        description = "Automatically attacks entities around you.",
        category = ModuleCategory.COMBAT
)
public class KillAura extends Module {

    private final TimingUtil timer = new TimingUtil();

    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        if (mc.world == null || mc.player == null) return;

        for (var entity : mc.world.getEntities()) {
            if (entity.isAlive() && entity.isAttackable() && entity instanceof VillagerEntity) {
                if (mc.player.distanceTo(entity) <= 3.5F && timer.hasElapsed(625L)) {
                    mc.player.swingHand(mc.player.getActiveHand() == Hand.MAIN_HAND ? Hand.MAIN_HAND : Hand.OFF_HAND);
                    mc.interactionManager.attackEntity(mc.player, entity);
                    timer.reset();
                }
            }
        }
    }

}
