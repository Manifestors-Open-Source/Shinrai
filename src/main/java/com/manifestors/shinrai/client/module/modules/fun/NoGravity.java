package com.manifestors.shinrai.client.module.modules.fun;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "No Gravity",
        description = "Lets Change World Upside down",
        category = ModuleCategory.MOVEMENT,
        keyCode = GLFW.GLFW_KEY_G
)
public class NoGravity extends Module {

    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        if (mc.player != null)
            mc.player.setNoGravity(false);
    }
}
