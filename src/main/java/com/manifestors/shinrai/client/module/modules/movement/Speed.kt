package com.manifestors.shinrai.client.module.modules.movement;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;

@ModuleData(
        name = "Speed",
        category = ModuleCategory.MOVEMENT
)
public class Speed extends Module {

    private int airTicks;
    private double speed;

    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        if (mc.player != null) {
            if (!mc.player.isOnGround()) {
                speed *= 0.9999999999999999;
                airTicks++;
                if (airTicks == 3)
                    mc.player.setVelocity(mc.player.getVelocity().x, -0.0784000015258789, mc.player.getVelocity().z);
            } else {
                airTicks = 0;
                speed = 0.3444567;

                mc.player.jump();
            }

            //MovementUtils.applyStrafe((float) speed);
        }
    }

}
