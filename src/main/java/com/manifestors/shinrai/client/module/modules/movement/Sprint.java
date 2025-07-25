package com.manifestors.shinrai.client.module.modules.movement;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "Sprint",
        description = "Automatically sprints for you.",
        category = ModuleCategory.MOVEMENT,
        keyCode = GLFW.GLFW_KEY_Z,
        alternatives = "AutoSprint"
)
public class Sprint extends Module {

    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        if (mc.player != null)
            mc.player.setSprinting(true);
    }

}
