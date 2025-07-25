package com.manifestors.shinrai.client.module.modules.extras;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

@ModuleData(
        name = "No Portal Cool Down",
        description = "You are not gonna get effected from portal's cooldown.",
        category = ModuleCategory.EXTRAS,
        keyCode = GLFW.GLFW_KEY_C
)
public class NoPortalCoolDown extends Module {
    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        if (mc.player != null)
            mc.player.setNoGravity(false);
    }
}
